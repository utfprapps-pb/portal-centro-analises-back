package com.portal.centro.API.service;

import com.portal.centro.API.dto.SolicitationRequestDto;
import com.portal.centro.API.enums.*;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.StudentProfessor;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.SolicitationRepository;
import com.portal.centro.API.repository.StudentProfessorRepository;
import io.hypersistence.utils.hibernate.type.array.LocalDateTimeArrayType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SolicitationService extends GenericService<Solicitation, Long> {

    private final SolicitationHistoricService solicitationHistoricService;
    private final UserService userService;
    private final SolicitationRepository solicitationRepository;

    private final StudentProfessorRepository studentProfessorRepository;

    public SolicitationService(SolicitationRepository solicitationRepository, SolicitationHistoricService solicitationHistoricService,
                               UserService userService, StudentProfessorRepository studentProfessorRepository) {
        super(solicitationRepository);
        this.solicitationRepository = solicitationRepository;
        this.solicitationHistoricService = solicitationHistoricService;
        this.userService = userService;
        this.studentProfessorRepository = studentProfessorRepository;
    }

    /**
     * Esse método deve ser utilizado ao salvar um novo registro de solicitação ou ao alterar uma solicitação
     * que está na situação REFUSED. Para atualizar a situação da solicitação, utilizar o método updateStatus()
     */
    @Override
    public Solicitation save(Solicitation solicitation) throws Exception {
        User loggedUser = userService.findSelfUser();

        if (isEditing(solicitation)) {
            validateSolicitationWhenEditing(solicitation);
        } else {
            solicitation.setCreatedAt(LocalDateTime.now());
            solicitation.setPaid(false);
        }

        // Verifica se a Natureza do projeto é outra, se sim, verifica se o campo de outra natureza foi preenchido.
        if (solicitation.getProjectNature().equals(SolicitationProjectNature.OTHER)
                && (solicitation.getOtherProjectNature() == null || solicitation.getOtherProjectNature().isEmpty())) {
            throw new ValidationException(
                    "O campo 'Outra natureza de projeto' deve ser preenchido quando a natureza do projeto for 'Outro'.");
        }
//        setProjectToNullIfEmpty(solicitation);
        solicitation.setCreatedBy(loggedUser);

        // Cria o histório da solicitação
        SolicitationHistoric solicitationHistoric = new SolicitationHistoric();
        solicitationHistoric.setSolicitation(solicitation);

        // Seta a situação inicial do projeto conforme o perfil de usuário
        if (solicitation.getId() == null) {
            // Caso o usuário seja um aluno, inicialmente a solicitação deverá ser aprovada pelo professor
            if (loggedUser.getRole().equals(Type.ROLE_STUDENT)) {
                solicitation.setStatus(SolicitationStatus.PENDING_ADVISOR);
                solicitationHistoric.setStatus(SolicitationStatus.PENDING_ADVISOR);
                // Seleciona o orientador do aluno no momento em que a solicitação foi realizada
                List<StudentProfessor> professores = studentProfessorRepository.findAllByStudentAndApproved(loggedUser.getId(), StudentTeacherApproved.ACEITO);
                if (professores.isEmpty()) {
                    throw new GenericException("Você não possui um professor vinculado!");
                } else if (professores.size() == 1) {
                    solicitation.setProfessor(professores.get(0).getProfessor());
                } else {
                    throw new GenericException("É necessário selecionar o professor!");
                }
            } else {
                solicitation.setStatus(SolicitationStatus.PENDING_LAB);
                solicitationHistoric.setStatus(SolicitationStatus.PENDING_LAB);
            }
        }
        super.save(solicitation);
        solicitationHistoricService.save(solicitationHistoric);

        return solicitation;
    }

    /**
     * Esse método deve ser utilizado ao atualizar a situação de uma solicitação
     */
    public Solicitation updateStatus(SolicitationRequestDto solicitationRequestDto) throws Exception {
        // Recupera a solicitação do banco de dados
        Solicitation solicitation = this.findOneById(solicitationRequestDto.getId());

        // Cria um novo registro no histórico
        SolicitationHistoric solicitationHistoric = new SolicitationHistoric();
        solicitationHistoric.setSolicitation(solicitation);

        // Verifica se foi aprovado, se sim irá buscar a nova situação conforme as regras de negócio
        if (solicitationRequestDto.isApproved()) {
            solicitation.setStatus(getNewStatus(solicitation));
        } else {
            solicitation.setStatus(SolicitationStatus.REFUSED);
            solicitationHistoric.setObservation(solicitationRequestDto.getObservation());
        }
        solicitationHistoric.setStatus(solicitation.getStatus());

        // Quando aprovado a entrega da amostra o laboratório poderá colocar a data de agendamento da análise
        if (SolicitationStatus.APPROVED.equals(solicitation.getStatus())
                && solicitationRequestDto.getScheduleDate() != null) {
            solicitation.setScheduleDate(solicitationRequestDto.getScheduleDate());
        }

        solicitationHistoricService.save(solicitationHistoric);
        return super.save(solicitation);
    }

    /**
     * Retorna a nova situação caso a situação atual da solicitação tenha sido aprovada.
     */
    private SolicitationStatus getNewStatus(Solicitation solicitation) {
        return switch (solicitation.getStatus()) {
            case PENDING_ADVISOR -> SolicitationStatus.PENDING_LAB;
            case PENDING_LAB -> SolicitationStatus.PENDING_SAMPLE;
            case PENDING_SAMPLE -> SolicitationStatus.APPROVED;
            case APPROVED -> {
                if ((solicitation.getCreatedBy().getRole().equals(Type.ROLE_EXTERNAL)
                        || solicitation.getCreatedBy().getRole().equals(Type.ROLE_PARTNER))
                        && !solicitation.isPaid()) {
                    yield SolicitationStatus.PENDING_PAYMENT;
                } else {
                    yield SolicitationStatus.FINISHED;
                }
            }
            default -> throw new RuntimeException("Falha ao atualizar a situação da solicitação.");
        };
    }

    private boolean isEditing(Solicitation solicitation) {
        return (Objects.nonNull(solicitation.getId()) && solicitation.getId() > 0);
    }

    private void validateSolicitationWhenEditing(Solicitation solicitation) {
        validateSolicitationCreatorPresent(solicitation);
        validateOnlyStatusRefusedWhenEdit(solicitation);
        validateOnlyUserSolicitationCreatorCanEdit(solicitation);
    }

    private void validateSolicitationCreatorPresent(Solicitation solicitation) {
        User responsibleUser = solicitation.getCreatedBy();
        if (Objects.isNull(responsibleUser))
            throw new ValidationException("É obrigatório informar o responsável pela solicitação.");
    }

    private void validateOnlyStatusRefusedWhenEdit(Solicitation solicitation) {
        if (!Objects.equals(solicitation.getStatus(), SolicitationStatus.REFUSED))
            throw new ValidationException("Não é possível editar uma solicitação com status diferente de 'Recusado'.");
    }

    private void validateOnlyUserSolicitationCreatorCanEdit(Solicitation solicitation) {
        User loggedUser = userService.findSelfUser();
        if (!Objects.equals(solicitation.getCreatedBy().getId(), loggedUser.getId()))
            throw new ValidationException("Somente o responsável pela solicitação pode realizar essa ação.");
    }

    private void setProjectToNullIfEmpty(Solicitation solicitation) {
        if (Objects.nonNull(solicitation.getProject()) &&
                Objects.equals(solicitation.getProject().getId(), 0L))
            solicitation.setProject(null);
    }


    public List<Solicitation> getPending() {
        User user = userService.findSelfUser();
        return switch (user.getRole()) {
            case ROLE_ADMIN -> solicitationRepository.findAllByStatus(SolicitationStatus.PENDING_LAB);
            case ROLE_PROFESSOR ->
                    solicitationRepository.findAllByProject_UserAndStatus(user, SolicitationStatus.PENDING_ADVISOR);
            default -> throw new ValidationException("Você não possui permissão para acessar este recurso.");
        };
    }

    public Solicitation approveProfessor(Long id) { // Professor
        Solicitation solicitation = solicitationRepository.findById(id).orElse(null);
        if (solicitation != null) {
            solicitation.setStatus(SolicitationStatus.PENDING_LAB);
            return solicitationRepository.save(solicitation);
        } else {
            throw new RuntimeException("Registro não encontrado");
        }
    }

    public Solicitation approveLab(Long id) { // Lab
        Solicitation solicitation = solicitationRepository.findById(id).orElse(null);
        if (solicitation != null) {
            solicitation.setStatus(SolicitationStatus.PENDING_SAMPLE);
            return solicitationRepository.save(solicitation);
        } else {
            throw new RuntimeException("Registro não encontrado");
        }
    }

    public Page<Solicitation> getPendingPage(PageRequest pageRequest) {
        User user = userService.findSelfUser();

        return switch (user.getRole()) {
            case ROLE_PROFESSOR ->
                    solicitationRepository.findAllByProject_UserAndStatus(user, SolicitationStatus.PENDING_ADVISOR, pageRequest);
            case ROLE_ADMIN -> solicitationRepository.findAllByStatus(SolicitationStatus.PENDING_LAB, pageRequest);
            default -> throw new ValidationException("Você não possui permissão para acessar este recurso.");
        };
    }

}

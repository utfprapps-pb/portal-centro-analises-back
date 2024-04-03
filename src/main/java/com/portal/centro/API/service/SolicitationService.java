package com.portal.centro.API.service;

import com.portal.centro.API.dto.SolicitationRequestDto;
import com.portal.centro.API.enums.*;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.SolicitationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SolicitationService extends GenericService<Solicitation, Long> {

    private final SolicitationHistoricService solicitationHistoricService;
    private final UserService userService;
    private final SolicitationRepository solicitationRepository;

    public SolicitationService(SolicitationRepository solicitationRepository, SolicitationHistoricService solicitationHistoricService,
                               UserService userService) {
        super(solicitationRepository);
        this.solicitationRepository = solicitationRepository;
        this.solicitationHistoricService = solicitationHistoricService;
        this.userService = userService;
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
        }

        // Verifica se a Natureza do projeto é outra, se sim, verifica se o campo de outra natureza foi preenchido.
        if (solicitation.getProjectNature().equals(SolicitationProjectNature.OTHER)
                && (solicitation.getOtherProjectNature() == null || solicitation.getOtherProjectNature().isEmpty())) {
            throw new ValidationException(
                    "O campo 'Outra natureza de projeto' deve ser preenchido quando a natureza do projeto for 'Outro'.");
        }
        setProjectToNullIfEmpty(solicitation);
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
        Solicitation solicitation = this.findOneById(solicitationRequestDto.getId());
        solicitation.setStatus(solicitationRequestDto.getStatus());

        SolicitationHistoric solicitationHistoric = new SolicitationHistoric();
        solicitationHistoric.setSolicitation(solicitation);
        solicitationHistoric.setStatus(solicitationRequestDto.getStatus());

        if (solicitation.getStatus() == SolicitationStatus.REFUSED) {
            solicitationHistoric.setObservation(solicitationRequestDto.getObservation());
        }

        if (SolicitationStatus.APPROVED.equals(solicitationRequestDto.getStatus())
                && solicitationRequestDto.getScheduleDate() != null) {
            solicitation.setScheduleDate(solicitationRequestDto.getScheduleDate());
        }

        solicitationHistoricService.save(solicitationHistoric);

        return super.save(solicitation);
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

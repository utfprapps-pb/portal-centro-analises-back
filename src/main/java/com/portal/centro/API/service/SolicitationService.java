package com.portal.centro.API.service;

import com.portal.centro.API.dto.SolicitationResponseDto;
import com.portal.centro.API.enums.*;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.SolicitationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SolicitationService extends GenericService<Solicitation, Long> {

    private final SolicitationHistoricService solicitationHistoricService;
    private final UserService userService;
    private final SolicitationRepository solicitationRepository;
    private final RequestValueService requestValueService;
    private final TechnicalReportService technicalReportService;

    public SolicitationService(SolicitationRepository solicitationRepository, SolicitationHistoricService solicitationHistoricService,
                               UserService userService,
                               TransactionService transactionService,
                               RequestValueService requestValueService,
                               TechnicalReportService technicalReportService) {
        super(solicitationRepository);
        this.solicitationRepository = solicitationRepository;
        this.solicitationHistoricService = solicitationHistoricService;
        this.userService = userService;
        this.requestValueService = requestValueService;
        this.technicalReportService = technicalReportService;
    }

    @Override
    public Solicitation save(Solicitation requestBody) throws Exception {
        if (isEditing(requestBody))
            validateSolicitationWhenEditing(requestBody);

        if (requestBody.getProjectNature().equals(SolicitationProjectNature.OTHER)
                && (requestBody.getOtherProjectNature() == null || requestBody.getOtherProjectNature().isEmpty())) {
            throw new ValidationException(
                    "O campo 'Outra natureza de projeto' deve ser preenchido quando a natureza do projeto for 'Outro'.");
        }
        User loggedUser = userService.findSelfUser();
        requestBody.setCreatedBy(loggedUser);
        setSolicitationStatusWhenUserExternalOrPartner(requestBody, loggedUser);
        setProjectToNullIfEmpty(requestBody);
        Solicitation output = super.save(requestBody);
        SolicitationHistoric solicitationHistoric = new SolicitationHistoric();
        if (loggedUser.getRole().equals(Type.ROLE_PROFESSOR)) {
            solicitationHistoric.setStatus(SolicitationStatus.PENDING_LAB);
            output.setStatus(SolicitationStatus.PENDING_LAB);
        } else {
            solicitationHistoric.setStatus(requestBody.getStatus());
        }

        solicitationHistoric.setSolicitation(output);
        solicitationHistoricService.saveAudit(solicitationHistoric);

        return output;
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
        if (!Objects.equals(solicitation.getStatus(), SolicitationStatus.REFUSED) )
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

    private void setSolicitationStatusWhenUserExternalOrPartner(Solicitation solicitation, User user) {
        if (Objects.isNull(user))
            return;

        List<Type> externalTypes = Arrays.asList(Type.ROLE_EXTERNAL, Type.ROLE_PARTNER);
        if (!externalTypes.contains(user.getRole()))
            return;

        solicitation.setStatus(SolicitationStatus.PENDING_LAB);
    }

    public Solicitation updateStatus(SolicitationResponseDto responseDto) throws Exception {
        Solicitation solicitation = this.findOneById(responseDto.getId());
        solicitation.setStatus(responseDto.getStatus());

        if (solicitation.getStatus() == SolicitationStatus.REFUSED) {
            // TODO save in históric.
            // solicitation.setRejectionReason(responseDto.getReason());
        }

//        if(SolicitationStatus.PENDING_PAYMENT.equals(responseDto.getStatus()) && TypeUser.UTFPR.equals(solicitation.getTypeUser())) {
//            // TODO VERIFICAR PAGAMENTO
////            TechnicalReport report = technicalReportService.findBySolicitationId(solicitation.getId());
////            userService.updateBalance(solicitation.getProject().getTeacher().getId(), TransactionType.WITHDRAW,
////                    requestValueService.calculate(report)
////            );
//        }
        if(responseDto.getData() != null && SolicitationStatus.APPROVED.equals(responseDto.getStatus())){
            solicitation.setScheduleDate(responseDto.getData());
        }
        SolicitationHistoric solicitationHistoric = new SolicitationHistoric();
        solicitationHistoric.setStatus(responseDto.getStatus());
        solicitationHistoric.setSolicitation(solicitation);

        solicitationHistoricService.saveAudit(solicitationHistoric);

        return super.save(solicitation);
    }

    public List<Solicitation> getPending() {
        User user = userService.findSelfUser();
        switch (user.getRole()) {
            case ROLE_ADMIN:
                return solicitationRepository.findAllByStatus(SolicitationStatus.PENDING_LAB);
            case ROLE_PROFESSOR:
                return solicitationRepository.findAllByProject_UserAndStatus(user, SolicitationStatus.PENDING_ADVISOR);
            default:
                throw new ValidationException("Você não possui permissão para acessar este recurso.");
        }
    }

    public ResponseEntity approveProfessor(Long id) { // Professor
        Optional<Solicitation> solicitation = solicitationRepository.findById(id);
        solicitation.get().setStatus(SolicitationStatus.PENDING_LAB);

        return ResponseEntity.ok(solicitationRepository.save(solicitation.get()));
    }

    public ResponseEntity approveLab(Long id) { // Lab
        Optional<Solicitation> solicitation = solicitationRepository.findById(id);
        solicitation.get().setStatus(SolicitationStatus.PENDING_SAMPLE);

        return ResponseEntity.ok(solicitationRepository.save(solicitation.get()));
    }

    public Page<Solicitation> getPendingPage(PageRequest pageRequest) {
        User user = userService.findSelfUser();

        switch (user.getRole()) {
            case ROLE_PROFESSOR:
                return solicitationRepository.findAllByProject_UserAndStatus(user, SolicitationStatus.PENDING_ADVISOR, pageRequest);
            case ROLE_ADMIN:
                return solicitationRepository.findAllByStatus(SolicitationStatus.PENDING_LAB, pageRequest);
            default:
                throw new ValidationException("Você não possui permissão para acessar este recurso.");
        }
    }

}

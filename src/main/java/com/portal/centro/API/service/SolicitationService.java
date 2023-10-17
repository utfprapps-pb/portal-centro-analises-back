package com.portal.centro.API.service;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.portal.centro.API.enums.SolicitationProjectNature;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Audit;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.SolicitationRepository;

@Service
public class SolicitationService extends GenericService<Solicitation, Long> {

    private final AuditService auditService;
    private final UserService userService;
    private final SolicitationRepository solicitationRepository;

    public SolicitationService(SolicitationRepository solicitationRepository, AuditService auditService,
                               UserService userService) {
        super(solicitationRepository);
        this.solicitationRepository = solicitationRepository;
        this.auditService = auditService;
        this.userService = userService;
    }

    @Override
    public Solicitation save(Solicitation requestBody) throws Exception {
        if (requestBody.getProjectNature().equals(SolicitationProjectNature.OTHER)
                && (requestBody.getOtherProjectNature() == null || requestBody.getOtherProjectNature().isEmpty())) {
            throw new ValidationException(
                    "O campo 'Outra natureza de projeto' deve ser preenchido quando a natureza do projeto for 'Outro'.");
        }

        requestBody.setCreatedBy(userService.findSelfUser());

        Solicitation output = super.save(requestBody);
        Audit audit = new Audit();
        audit.setNewStatus(requestBody.getStatus());
        audit.setSolicitation(output);

        auditService.saveAudit(audit);

        return output;
    }

    public Solicitation updateStatus(Long id, SolicitationStatus status) throws Exception {
        Solicitation solicitation = this.findOneById(id);
        solicitation.setStatus(status);

        Audit audit = new Audit();
        audit.setNewStatus(status);
        audit.setSolicitation(solicitation);

        auditService.saveAudit(audit);

        return super.save(solicitation);
    }

    public List<Solicitation> getPending() {
        User user = userService.findSelfUser();
        switch (user.getRole()) {
            case ADMIN:
                return solicitationRepository.findAllByStatus(SolicitationStatus.PENDING_LAB);
            case PROFESSOR:
                return solicitationRepository.findAllByProject_TeacherAndStatus(user, SolicitationStatus.PENDING_ADVISOR);
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

        if (Objects.equals(user.getRole(), Type.STUDENT) || Objects.equals(user.getRole(), Type.EXTERNAL))
            throw new ValidationException("Você não possui permissão para acessar este recurso.");

        if (Objects.equals(user.getRole(), Type.PROFESSOR))
            return solicitationRepository.findAllByProject_TeacherAndStatus(user, SolicitationStatus.PENDING_ADVISOR, pageRequest);

        if (Objects.equals(user.getRole(), Type.ADMIN))
            return solicitationRepository.findAllByStatus(SolicitationStatus.PENDING_LAB, pageRequest);

        return new PageImpl<>(Collections.emptyList());
    }

}

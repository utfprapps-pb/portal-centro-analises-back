package com.portal.centro.API.service;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Audit;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.AuditRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuditService extends GenericService<Audit, Long> {

    private final AuditRepository auditRepository;
    private final UserService userService;

    public AuditService(AuditRepository auditRepository, UserService userService) {
        super(auditRepository);
        this.auditRepository = auditRepository;
        this.userService = userService;
    }

    @Override
    public List<Audit> getAll() {
        User user = userService.findSelfUser();
        switch (user.getRole()) {
            case ADMIN:
                return super.getAll();
            case PROFESSOR:
                return auditRepository.findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(user, user);
            default:
                return auditRepository.findAllBySolicitation_CreatedBy(user);
        }
    }

    public void saveAudit(Audit audit) {
        audit.setChangeDate(LocalDateTime.now());

        audit.setUpdatedBy(userService.findSelfUser());
        auditRepository.save(audit);
    }

    public List<Audit> findHistoryById(Long id, SolicitationStatus status) {
        User user = userService.findSelfUser();

        switch (user.getRole()) {
            case STUDENT:
            case EXTERNAL:
            case PARTNER:
                return auditRepository.findAllBySolicitation_CreatedByAndSolicitationIdAndNewStatusIsNotOrderByChangeDateDesc(user, id, status);
            case PROFESSOR:
                return auditRepository.findAllBySolicitation_CreatedByOrSolicitation_Project_TeacherAndSolicitationIdAndNewStatusIsNotOrderByChangeDateDesc(user, user, id, status);
            case ADMIN:
                return auditRepository.findAllBySolicitationIdAndNewStatusIsNotOrderByChangeDateDesc(id, status);
            default:
                return new ArrayList<>();
        }
    }

    public Page<Audit> page(PageRequest pageRequest) {
        User user = userService.findSelfUser();

        switch (user.getRole()) {
            case STUDENT:
            case EXTERNAL:
            case PARTNER:
                return auditRepository.findAllDistinctByOrderByUserCreatedAtDescCreatedByUser(user.getId(), pageRequest);
            case PROFESSOR:
                return auditRepository.findAllDistinctByOrderByUserCreatedAtDescCreatedByUserOrTeacherInProject(user.getId(), pageRequest);
            case ADMIN:
                return auditRepository.findAllDistinctByOrderByUserCreatedAtDesc(pageRequest);
            default:
                throw new ValidationException("Você não possui permissão para acessar este recurso.");
        }
    }

}

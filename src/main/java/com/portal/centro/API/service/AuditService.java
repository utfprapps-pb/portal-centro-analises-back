package com.portal.centro.API.service;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Audit;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.AuditRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

        if (Objects.equals(user.getRole(), Type.STUDENT) || Objects.equals(user.getRole(), Type.EXTERNAL))
            return auditRepository.findAllBySolicitation_CreatedByAndSolicitationIdAndNewStatusIsNotOrderByNewStatus(user, id, status);

        if (Objects.equals(user.getRole(), Type.PROFESSOR))
            return auditRepository.findAllBySolicitation_CreatedByOrSolicitation_Project_TeacherAndSolicitationIdAndNewStatusIsNotOrderByNewStatus(user, user, id, status);

        return auditRepository.findAllBySolicitationIdAndNewStatusIsNotOrderByNewStatus(id, status);
    }

    public Page<Audit> page(PageRequest pageRequest) {
        User user = userService.findSelfUser();

        if (Objects.equals(user.getRole(), Type.STUDENT) || Objects.equals(user.getRole(), Type.EXTERNAL))
            return auditRepository.findAllBySolicitation_CreatedBy(user, pageRequest);

        if (Objects.equals(user.getRole(), Type.PROFESSOR))
            return auditRepository.findAllBySolicitation_CreatedByOrSolicitation_Project_Teacher(user, user, pageRequest);

        return auditRepository.findAll(pageRequest);
    }

}

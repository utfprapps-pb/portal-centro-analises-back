package com.portal.centro.API.service;

import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Audit;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.AuditRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

}

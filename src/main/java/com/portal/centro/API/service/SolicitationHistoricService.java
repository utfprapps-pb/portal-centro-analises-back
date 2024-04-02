package com.portal.centro.API.service;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.SolicitationHistoricRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitationHistoricService extends GenericService<SolicitationHistoric, Long> {

    private final SolicitationHistoricRepository solicitationHistoricRepository;
    private final UserService userService;

    public SolicitationHistoricService(SolicitationHistoricRepository solicitationHistoricRepository, UserService userService) {
        super(solicitationHistoricRepository);
        this.solicitationHistoricRepository = solicitationHistoricRepository;
        this.userService = userService;
    }

    @Override
    public List<SolicitationHistoric> getAll() {
        User user = userService.findSelfUser();
        switch (user.getRole()) {
            case ROLE_ADMIN:
                return super.getAll();
            case ROLE_PROFESSOR:
                return solicitationHistoricRepository.findAllBySolicitation_CreatedByOrSolicitation_Project_User(user, user);
            default:
                return solicitationHistoricRepository.findAllBySolicitation_CreatedBy(user);
        }
    }

    public void saveAudit(SolicitationHistoric solicitationHistoric) {
        solicitationHistoric.setCreatedAt(LocalDateTime.now());

        solicitationHistoric.setCreatedBy(userService.findSelfUser());
        solicitationHistoricRepository.save(solicitationHistoric);
    }

    public List<SolicitationHistoric> findHistoryById(Long id, SolicitationStatus status) {
        User user = userService.findSelfUser();

        switch (user.getRole()) {
            case ROLE_STUDENT:
            case ROLE_EXTERNAL:
            case ROLE_PARTNER:
                return solicitationHistoricRepository.findAllBySolicitation_CreatedByAndSolicitationIdAndStatusIsNotOrderByCreatedAtDesc(user, id, status);
            case ROLE_PROFESSOR:
                return solicitationHistoricRepository.findAllBySolicitation_Project_UserAndSolicitationIdAndStatusIsNotOrderByCreatedAtDesc(user, id, status);
            case ROLE_ADMIN:
                return solicitationHistoricRepository.findAllBySolicitationIdAndStatusIsNotOrderByCreatedAtDesc(id, status);
            default:
                return new ArrayList<>();
        }
    }

    public Page<SolicitationHistoric> page(PageRequest pageRequest) {
        User user = userService.findSelfUser();

        switch (user.getRole()) {
            case ROLE_STUDENT:
            case ROLE_EXTERNAL:
            case ROLE_PARTNER:
                return solicitationHistoricRepository.findAllDistinctByOrderByUserCreatedAtDescCreatedByUser(user.getId(), pageRequest);
            case ROLE_PROFESSOR:
                return solicitationHistoricRepository.findAllDistinctByOrderByUserCreatedAtDescCreatedByUserOrTeacherInProject(user.getId(), pageRequest);
            case ROLE_ADMIN:
                return solicitationHistoricRepository.findAllDistinctByOrderByUserCreatedAtDesc(pageRequest);
            default:
                throw new ValidationException("Você não possui permissão para acessar este recurso.");
        }
    }

}

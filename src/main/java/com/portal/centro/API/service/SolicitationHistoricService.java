package com.portal.centro.API.service;

import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.SolicitationHistoric;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.SolicitationHistoricRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
            default:
                return solicitationHistoricRepository.findAllBySolicitation_CreatedByOrSolicitation_Responsavel(user, user);
        }
    }

    public List<SolicitationHistoric> findHistoryBySolicitationId(Long id) {
        return solicitationHistoricRepository.findAllBySolicitationId(id);
    }

    public void verificaStatusValido(SolicitationHistoric historico) throws Exception {
        User selfUser = userService.findSelfUser();
        if (!Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN)) {
            List<SolicitationStatus> statusPermitidos = List.of(
                    SolicitationStatus.AWAITING_RESPONSIBLE_CONFIRMATION,
                    SolicitationStatus.AWAITING_LAB_CONFIRMATION,
                    SolicitationStatus.AWAITING_CORRECTION,
                    SolicitationStatus.REJECTED);
            if (!statusPermitidos.contains(historico.getStatus())) {
                throw new GenericException("Você não pode fazer isso!");
            }
        }
    }
}

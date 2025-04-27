package com.portal.centro.API.service;

import com.portal.centro.API.configuration.ApplicationContextProvider;
import com.portal.centro.API.enums.FinanceState;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.*;
import com.portal.centro.API.repository.FinanceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FinanceService extends GenericService<Finance, Long> {

    private final UserService userService;
    private final SolicitationHistoricService solicitationHistoricService;
    private final SolicitationService solicitationService;
    private final UserBalanceService userBalanceService;
    @PersistenceContext
    private EntityManager entityManager;

    public FinanceService(FinanceRepository financeRepository,
                          UserService userService,
                          SolicitationHistoricService solicitationHistoricService,
                          SolicitationService solicitationService,
                          UserBalanceService userBalanceService,
                          EntityManager entityManager) {
        super(financeRepository);
        this.userService = userService;
        this.solicitationHistoricService = solicitationHistoricService;
        this.solicitationService = solicitationService;
        this.userBalanceService = userBalanceService;
        this.entityManager = entityManager;
    }

    private boolean throwIfUserNotIsAdmin() throws Exception {
        User selfUser = userService.findSelfUser();
        if (!Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN)) {
            throw new GenericException("Você não pode fazer isso!");
        }
        return true;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public Finance save(Finance requestBody) throws Exception {
        this.throwIfUserNotIsAdmin();
        Finance finance = super.save(requestBody);
        if (finance.getSolicitation() != null) {
            if (SolicitationStatus.AWAITING_PAYMENT.equals(finance.getSolicitation().getStatus())) {
                throw new GenericException("Essa solicitação já está aguardando pagamento!");
            }
            SolicitationHistoric historic = new SolicitationHistoric();
            historic.setSolicitation(finance.getSolicitation());
            historic.setStatus(SolicitationStatus.AWAITING_PAYMENT);
            solicitationHistoricService.save(historic);
            this.atualizarSolicitacao(finance, historic.getStatus());
        }
        return finance;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public Finance update(Finance requestBody) throws Exception {
        this.throwIfUserNotIsAdmin();
        FinanceState oldFinanceState = this.findOneById(requestBody.getId()).getState();
        Finance newFinance = super.update(requestBody);
        if (newFinance.getSolicitation() != null) {
            if (!oldFinanceState.equals(newFinance.getState())) {
                UserBalance userBalance = userBalanceService.findByUser(newFinance.getPagador());

                SolicitationHistoric historic = new SolicitationHistoric();
                historic.setSolicitation(newFinance.getSolicitation());
                if (FinanceState.PENDING.equals(oldFinanceState) && FinanceState.RECEIVED.equals(newFinance.getState())) {
                    historic.setStatus(SolicitationStatus.COMPLETED);
                    historic.setObservation("Solicitação de pagamento aprovada");
                    userBalance.setBalance(userBalance.getBalance().subtract(newFinance.getValue()));
                } else if (FinanceState.RECEIVED.equals(oldFinanceState) && FinanceState.PENDING.equals(newFinance.getState())) {
                    historic.setStatus(SolicitationStatus.AWAITING_PAYMENT);
                    historic.setObservation("Solicitação de pagamento cancelada");
                    userBalance.setBalance(userBalance.getBalance().add(newFinance.getValue()));
                }
                newFinance.getSolicitation().setStatus(historic.getStatus());
                userBalanceService.save(userBalance);
                solicitationHistoricService.save(historic);
            }
            this.atualizarSolicitacao(newFinance, newFinance.getSolicitation().getStatus());
        }
        return newFinance;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public ObjectReturn deleteById(Long aLong) throws Exception {
        this.throwIfUserNotIsAdmin();
        final Finance finance = this.findOneById(aLong);
        ObjectReturn retorno = super.deleteById(aLong);
        if (finance.getSolicitation() != null) {
            boolean atualizarBalance = false;
            UserBalance userBalance = userBalanceService.findByUser(finance.getPagador());
            SolicitationHistoric historic = new SolicitationHistoric();
            historic.setSolicitation(finance.getSolicitation());
            historic.setStatus(SolicitationStatus.ANALYZING);
            historic.setObservation("Solicitação de pagamento cancelada");
            if (SolicitationStatus.COMPLETED.equals(finance.getSolicitation().getStatus())) {
                userBalance.setBalance(userBalance.getBalance().add(finance.getValue()));
                atualizarBalance = true;
            }
            if (atualizarBalance) {
                userBalanceService.save(userBalance);
            }
            solicitationHistoricService.save(historic);
            this.atualizarSolicitacao(finance, historic.getStatus());
        }
        return retorno;
    }

    protected void atualizarSolicitacao(Finance finance, SolicitationStatus newStatus) {
        WebsocketService websocketService = ApplicationContextProvider.getBean(WebsocketService.class);
        Solicitation solicitation = solicitationService.findOneById(finance.getSolicitation().getId());
        if (ObjectUtils.isNotEmpty(newStatus)) {
            solicitation.setStatus(newStatus);
        }

        UserBalance userBalance = userBalanceService.findByUser(finance.getPagador());

        websocketService.atualizarSolicitacao(solicitation);
        websocketService.atualizarUserbalance(userBalance);
    }
}

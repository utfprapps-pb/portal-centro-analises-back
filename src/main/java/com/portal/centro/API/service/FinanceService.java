package com.portal.centro.API.service;

import com.portal.centro.API.configuration.ApplicationContextProvider;
import com.portal.centro.API.enums.FinanceState;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.*;
import com.portal.centro.API.repository.FinanceRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class FinanceService extends GenericService<Finance, Long> {

    private final static BigDecimal MIN_ONE = new BigDecimal(-1);
    private final FinanceRepository financeRepository;
    private final UserService userService;
    private final SolicitationHistoricService solicitationHistoricService;
    private final SolicitationService solicitationService;
    private final UserBalanceService userBalanceService;
    private final FinanceTransactionService financeTransactionService;

    public FinanceService(FinanceRepository financeRepository,
                          UserService userService,
                          SolicitationHistoricService solicitationHistoricService,
                          SolicitationService solicitationService,
                          UserBalanceService userBalanceService,
                          FinanceTransactionService financeTransactionService) {
        super(financeRepository);
        this.financeRepository = financeRepository;
        this.userService = userService;
        this.solicitationHistoricService = solicitationHistoricService;
        this.solicitationService = solicitationService;
        this.userBalanceService = userBalanceService;
        this.financeTransactionService = financeTransactionService;
    }

    private void throwIfUserNotIsAdmin() throws Exception {
        User selfUser = userService.findSelfUser();
        if (!Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN)) {
            throw new GenericException("Você não pode fazer isso!");
        }
    }

    @Override
    public List<Finance> getAll() {
        User user = userService.findSelfUser();
        if (Objects.requireNonNull(user.getRole()) == Type.ROLE_ADMIN) {
            return super.getAll();
        }
        return financeRepository.findAllByResponsavelOrPagador(user, user);
    }

    @Override
    public Finance findOneById(Long id) throws Exception {
        Finance oneById = super.findOneById(id);

        User selfUser = userService.findSelfUser();
        if (Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN)) {
            return oneById;
        }

        if (oneById != null) {
            if (oneById.getResponsavel() != null && oneById.getResponsavel().getId().equals(selfUser.getId()) ||
                    (oneById.getPagador() != null && oneById.getPagador().getId().equals(selfUser.getId()))) {
                return oneById;
            }
        }
        throw new GenericException("Você não pode fazer isso!");
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
        } else {
            if (finance.getAlterarSaldo()) {
                FinanceTransaction transaction = getFinanceTransaction(finance);
                if (transaction.getValue() != null) {
                    financeTransactionService.save(transaction);
                }
            }
        }
        return finance;
    }

    private FinanceTransaction getFinanceTransaction(Finance finance) {
        FinanceTransaction transaction = new FinanceTransaction();
        transaction.setFinance(finance);
        transaction.setUsuario(finance.getResponsavel());
        transaction.setValue(finance.getValue());

        if (FinanceState.RECEIVED.equals(finance.getState())) {
            if (finance.getSolicitation() != null) {
                transaction.setUsuario(finance.getPagador());
            }
            transaction.setValue(transaction.getValue().multiply(MIN_ONE));
        }
        return transaction;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public Finance update(Finance requestBody) throws Exception {
        this.throwIfUserNotIsAdmin();
        Finance oldFinance = this.findOneById(requestBody.getId());
        FinanceState oldFinanceState = oldFinance.getState();
        Finance newFinance = super.update(requestBody);

        if (newFinance.getSolicitation() != null) {
            if (!oldFinanceState.equals(newFinance.getState())) {
                SolicitationHistoric historic = new SolicitationHistoric();
                historic.setSolicitation(newFinance.getSolicitation());
                if (FinanceState.PENDING.equals(oldFinanceState) && FinanceState.RECEIVED.equals(newFinance.getState())) {
                    historic.setStatus(SolicitationStatus.COMPLETED);
                    historic.setObservation("Solicitação de pagamento aprovada");
                } else if (FinanceState.RECEIVED.equals(oldFinanceState) && FinanceState.PENDING.equals(newFinance.getState())) {
                    historic.setStatus(SolicitationStatus.AWAITING_PAYMENT);
                    historic.setObservation("Solicitação de pagamento cancelada");
                }
                newFinance.getSolicitation().setStatus(historic.getStatus());
                solicitationHistoricService.save(historic);
            }
            FinanceTransaction transaction = getFinanceTransaction(newFinance);
            if (transaction.getValue() != null) {
                financeTransactionService.save(transaction);
            }
        }

        financeTransactionService.deleteByFinance(oldFinance);

        FinanceTransaction transaction = getFinanceTransaction(newFinance);
        if (transaction.getValue() != null) {
            financeTransactionService.save(transaction);
        }
        return newFinance;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public ObjectReturn deleteById(Long aLong) throws Exception {
        this.throwIfUserNotIsAdmin();
        final Finance finance = this.findOneById(aLong);
        financeTransactionService.deleteByFinance(finance);

        ObjectReturn retorno = super.deleteById(aLong);
        if (finance.getSolicitation() != null) {
            SolicitationHistoric historic = new SolicitationHistoric();
            historic.setSolicitation(finance.getSolicitation());
            historic.setStatus(SolicitationStatus.ANALYZING);
            historic.setObservation("Solicitação de pagamento cancelada");
            solicitationHistoricService.save(historic);
        }
        return retorno;
    }

    protected void atualizarSolicitacao(Finance finance, SolicitationStatus newStatus) throws Exception {
        WebsocketService websocketService = ApplicationContextProvider.getBean(WebsocketService.class);
        Solicitation solicitation = solicitationService.findOneById(finance.getSolicitation().getId());
        if (ObjectUtils.isNotEmpty(newStatus)) {
            solicitation.setStatus(newStatus);
        }
        websocketService.atualizarSolicitacao(solicitation);
        this.atualizarBalance(finance.getPagador());
    }

    protected void atualizarBalance(User user) {
        WebsocketService websocketService = ApplicationContextProvider.getBean(WebsocketService.class);
        UserBalance userBalance = userBalanceService.findByUser(user);
        websocketService.atualizarUserbalance(userBalance);
    }
}

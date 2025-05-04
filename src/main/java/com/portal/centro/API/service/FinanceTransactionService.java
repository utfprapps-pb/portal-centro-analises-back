package com.portal.centro.API.service;

import com.portal.centro.API.configuration.ApplicationContextProvider;
import com.portal.centro.API.enums.FinanceState;
import com.portal.centro.API.enums.SolicitationStatus;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.*;
import com.portal.centro.API.repository.FinanceRepository;
import com.portal.centro.API.repository.FinanceTransactionRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FinanceTransactionService extends GenericService<FinanceTransaction, Long> {

    private final FinanceTransactionRepository financeTransactionRepository;
    private final UserBalanceService userBalanceService;

    public FinanceTransactionService(FinanceTransactionRepository financeTransactionRepository,
                                     UserBalanceService userBalanceService) {
        super(financeTransactionRepository);
        this.financeTransactionRepository = financeTransactionRepository;
        this.userBalanceService = userBalanceService;
    }

    @Override
    @Transactional
    public FinanceTransaction save(FinanceTransaction requestBody) throws Exception {
        if (requestBody.getFinance().getAlterarSaldo()) {
            FinanceTransaction save = super.save(requestBody);
            if (save.getUsuario() != null) {
                this.atualizarBalance(save.getUsuario());
            }
            return save;
        }
        return null;
    }

    @Override
    @Transactional
    public FinanceTransaction update(FinanceTransaction requestBody) throws Exception {
        throw new GenericException("Você não pode fazer isso!");
    }

    @Override
    @Transactional
    public ObjectReturn deleteById(Long aLong) throws Exception {
        FinanceTransaction financeTransaction = this.findOneById(aLong);
        ObjectReturn objectReturn = super.deleteById(aLong);

        if (financeTransaction.getUsuario() != null) {
            this.atualizarBalance(financeTransaction.getUsuario());
        }
        return objectReturn;
    }

    @Transactional
    protected void atualizarBalance(User user) throws Exception {
        UserBalance userBalance = userBalanceService.findByUser(user);
        BigDecimal balance = financeTransactionRepository.getUserBalance(user);
        userBalance.setBalance(balance);
        userBalanceService.save(userBalance);

        WebsocketService websocketService = ApplicationContextProvider.getBean(WebsocketService.class);
        websocketService.atualizarUserbalance(userBalance);
    }

    @Transactional
    public void deleteByFinance(Finance requestBody) throws Exception {
        List<FinanceTransaction> allByFinance = this.financeTransactionRepository.findAllByFinance(requestBody);
        List<User> usuarios = new ArrayList<>();
        for (FinanceTransaction financeTransaction : allByFinance) {
            if (financeTransaction.getUsuario() != null && !usuarios.contains(financeTransaction.getUsuario())) {
                usuarios.add(financeTransaction.getUsuario());
            }
        }

        this.financeTransactionRepository.deleteByFinance(requestBody);

        for (User usuario : usuarios) {
            this.atualizarBalance(usuario);
        }
    }
}

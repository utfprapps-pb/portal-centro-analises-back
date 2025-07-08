package br.edu.utfpr.pb.app.labcaapi.service;

import br.edu.utfpr.pb.app.labcaapi.configuration.ApplicationContextProvider;
import br.edu.utfpr.pb.app.labcaapi.exceptions.GenericException;
import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericService;
import br.edu.utfpr.pb.app.labcaapi.model.*;
import br.edu.utfpr.pb.app.labcaapi.repository.FinanceTransactionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
        if (balance.compareTo(userBalance.getNegativeLimit().multiply(new BigDecimal(-1))) < 0) {
            throw new GenericException(
                    "Novo saldo ultrapassa o limite do usuário!\n" +
                    "Novo saldo: " + userBalance.getBalance() + "\n" +
                    "O limite negativo é: " + userBalance.getNegativeLimit());
        }
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

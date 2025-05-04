package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Finance;
import com.portal.centro.API.model.FinanceTransaction;
import com.portal.centro.API.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FinanceTransactionRepository extends GenericRepository<FinanceTransaction, Long> {

    List<FinanceTransaction> findAllByFinance(Finance finance);

    void deleteByFinance(Finance finance);

    @Query("select coalesce(sum(f.value), 0) from tb_finance_transaction f where f.usuario = ?1")
    BigDecimal getUserBalance(User user);

}

package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.Finance;
import br.edu.utfpr.pb.app.labcaapi.model.FinanceTransaction;
import br.edu.utfpr.pb.app.labcaapi.model.User;
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

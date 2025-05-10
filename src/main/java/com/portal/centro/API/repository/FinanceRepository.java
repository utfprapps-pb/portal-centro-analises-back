package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Equipment;
import com.portal.centro.API.model.Finance;
import com.portal.centro.API.model.FinanceDetails;
import com.portal.centro.API.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanceRepository extends GenericRepository<Finance, Long> {

    List<Finance> findAllByResponsavelOrPagador(User soliictante, User pagador);

}

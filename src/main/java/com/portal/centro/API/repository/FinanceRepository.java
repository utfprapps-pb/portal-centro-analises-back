package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Finance;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends GenericRepository<Finance, Long> {

}

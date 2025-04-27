package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.UserBalance;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceRepository extends GenericRepository<UserBalance, Long> {

    UserBalance findByUserId(Long userId);



}

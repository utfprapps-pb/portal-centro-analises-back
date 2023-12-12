package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository  extends GenericRepository<Transaction,Long> {

    List<Transaction> findAllByUser_IdOrderByCreatedAtDesc(Long userId);

    Page<Transaction> findAllByUser_IdOrderByCreatedAtDesc(Long userId, PageRequest pageRequest);

    @Query(nativeQuery = true, value = "select * from transaction order by user_id, created_at desc")
    List<Transaction> findAllByOrderByUserCreatedAtDesc();

}

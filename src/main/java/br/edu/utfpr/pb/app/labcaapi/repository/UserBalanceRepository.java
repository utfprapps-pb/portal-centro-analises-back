package br.edu.utfpr.pb.app.labcaapi.repository;

import br.edu.utfpr.pb.app.labcaapi.generic.crud.GenericRepository;
import br.edu.utfpr.pb.app.labcaapi.model.UserBalance;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBalanceRepository extends GenericRepository<UserBalance, Long> {

    UserBalance findByUserId(Long userId);



}

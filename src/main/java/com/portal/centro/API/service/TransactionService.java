package com.portal.centro.API.service;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Transaction;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionService  extends GenericService<Transaction, Long> {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public TransactionService(GenericRepository<Transaction, Long> genericRepository, TransactionRepository transactionRepository, UserService userService) {
        super(genericRepository);
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    public List<Transaction> getAllByUserId(Long id){
       return transactionRepository.findAllByUser_IdOrderByCreatedAtDesc(id);
    }

    @Override
    public List<Transaction> getAll() {
        User selfUser = userService.findSelfUser();
        if (Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN))
            return transactionRepository.findAllByOrderByUserCreatedAtDesc();

        return transactionRepository.findAllByUser_IdOrderByCreatedAtDesc(selfUser.getId());
    }

    @Override
    public Page<Transaction> page(PageRequest pageRequest) {
        User user = userService.findSelfUser();

        switch (user.getRole()) {
            case ROLE_ADMIN:
                return super.page(pageRequest);
            case ROLE_PROFESSOR:
                return transactionRepository.findAllByUser_IdOrderByCreatedAtDesc(user.getId(), pageRequest);
            default:
                throw new ValidationException("Você não possui permissão para acessar este recurso.");
        }
    }
}

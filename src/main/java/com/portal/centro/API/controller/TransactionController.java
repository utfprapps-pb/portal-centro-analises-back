package com.portal.centro.API.controller;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.ValidationException;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Transaction;
import com.portal.centro.API.model.User;
import com.portal.centro.API.model.UserBalance;
import com.portal.centro.API.service.TransactionService;
import com.portal.centro.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("transaction")
public class TransactionController extends GenericController<Transaction, Long> {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(GenericService<Transaction, Long> genericService, TransactionService transactionService, UserService userService) {
        super(genericService);
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity save(@RequestBody @Valid Transaction requestBody) throws Exception {

        User selfUser = userService.findSelfUser();
        if (!Objects.equals(selfUser.getRole(), Type.ROLE_ADMIN)) {
            throw new ValidationException("Somente o administrador pode realizar esta ação.");
        }
        if (!ObjectUtils.isEmpty(requestBody.getId())) {
            throw new ValidationException("Não é possível atualizar uma transação, somente inseri-las.");
        }
        requestBody.setCreatedAt(LocalDateTime.now());
        requestBody.setCreatedBy(selfUser);
        UserBalance userBalance = userService.updateBalance(
                requestBody.getUser().getId(),
                requestBody.getType(),
                requestBody.getValue()
        );
        requestBody.setOldBalance(userBalance.getOld());
        requestBody.setCurrentBalance(userBalance.getCurrent());
        return ResponseEntity.ok(transactionService.save(requestBody));
    }

    @Override
    public ResponseEntity deleteById(Long aLong) {
        throw new ValidationException("Não é possível excluir uma transação, somente inseri-las.");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getByUserId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(transactionService.getAllByUserId(id));
    }

}

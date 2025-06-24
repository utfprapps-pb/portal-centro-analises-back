package com.portal.centro.API.service;

import com.portal.centro.API.enums.Type;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.ObjectReturn;
import com.portal.centro.API.model.User;
import com.portal.centro.API.model.UserBalance;
import com.portal.centro.API.repository.UserBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserBalanceService extends GenericService<UserBalance, Long> {

    private final UserBalanceRepository userBalanceRepository;
    private final UserService userService;
    private final WebsocketService websocketService;

    @Autowired
    public UserBalanceService(UserBalanceRepository userBalanceRepository,
                              UserService userService,
                              WebsocketService websocketService) {
        super(userBalanceRepository);
        this.userBalanceRepository = userBalanceRepository;
        this.userService = userService;
        this.websocketService = websocketService;
    }

    public BigDecimal getUserBalance() {
        Long selfId = userService.findSelfUser().getId();
        User user = new User();
        user.setId(selfId);
        UserBalance userBalance = this.findByUser(user);
        websocketService.atualizarUserbalance(userBalance);
        return userBalance.getBalance();
    }

    @Override
    public ObjectReturn deleteById(Long aLong) throws Exception {
        throw new GenericException("Você não pode fazer isso!");
    }

    public UserBalance findByUser(User user) {
        UserBalance userBalance = userBalanceRepository.findByUserId(user.getId());
        if (userBalance == null) {
            userBalance = new UserBalance();
            userBalance.setNegativeLimit(new BigDecimal(0L));
            if (Type.ROLE_PROFESSOR.equals(user.getRole())) {
                userBalance.setNegativeLimit(new BigDecimal(1000));
            }
            userBalance.setUser(user);
            userBalance.setBalance(BigDecimal.ZERO);
            return userBalanceRepository.save(userBalance);
        }
        if (userBalance.getNegativeLimit() == null) {
            userBalance.setNegativeLimit(new BigDecimal(0L));
        }
        return userBalance;
    }
}

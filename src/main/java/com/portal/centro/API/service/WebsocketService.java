package com.portal.centro.API.service;

import com.portal.centro.API.model.User;
import com.portal.centro.API.model.UserBalance;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WebsocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    public WebsocketService(SimpMessagingTemplate messagingTemplate, UserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
    }

    public void atualizarSolicitacao(Object solicitacao) {
        messagingTemplate.convertAndSend("/topic/solicitacoes", solicitacao);
    }

    public void atualizarUserbalance(UserBalance balance) {
        if (balance.getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            messagingTemplate.convertAndSend("/topic/user/balance/" + balance.getUser().getId(), balance.getBalance());
        }
        messagingTemplate.convertAndSend("/topic/balance", balance);
    }

}

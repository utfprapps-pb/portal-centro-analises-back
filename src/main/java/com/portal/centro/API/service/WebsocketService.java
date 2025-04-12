package com.portal.centro.API.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

    private SimpMessagingTemplate messagingTemplate;

    public WebsocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void atualizarSolicitacao(Object solicitacao) {
        messagingTemplate.convertAndSend("/topic/solicitacoes", solicitacao);
    }

}

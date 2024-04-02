package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum SolicitationStatus {
    PENDING_ADVISOR("Aguardando Confirmação do Orientador"),
    PENDING_CORRECTION("Aguardando Correção"),
    PENDING_LAB("Aguardando Confirmação do Laboratório"),
    PENDING_SAMPLE("Aguardando amostra"),
    APPROVED("Aguardando Análise"),
    PENDING_PAYMENT("Aguardando Pagamento"),
    REFUSED("Recusado"),
    FINISHED("Concluído");

    private final String content;

    SolicitationStatus(String content) {
        this.content = content;
    }

}

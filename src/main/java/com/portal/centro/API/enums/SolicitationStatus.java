package com.portal.centro.API.enums;

public enum SolicitationStatus {
    PENDING_ADVISOR("Aguardando Confirmação do Orientador"),
    PENDING_CORRECTION("Aguardando Correção"),
    PENDING_LAB("Aguardando Confirmação do Laboratório"),
    PENDING_SAMPLE("Aguardando amostra"),
    APPROVED("Aguardando Análise"),
    PENDING_PAYMENT("Aguardando Pagamento"),
    REFUSED("Recusado"),
    FINISHED("Concluído");

    private String content;

    SolicitationStatus(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}

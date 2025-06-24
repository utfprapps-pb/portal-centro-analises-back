package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum FinanceState {

    PENDING("Pendente"),
    PAID("Pago"),
    RECEIVED("Recebido");

    private final String content;

    FinanceState(String content) {
        this.content = content;
    }

}

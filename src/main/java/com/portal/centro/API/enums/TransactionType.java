package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum TransactionType {

    DEPOSIT("+"),
    WITHDRAW("-");

    private final String content;

    TransactionType (String content) {
        this.content = content;
    }

}

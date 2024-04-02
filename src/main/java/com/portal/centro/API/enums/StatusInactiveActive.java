package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum StatusInactiveActive {
    INACTIVE("inativo"),

    ACTIVE("ativo");

    private final String content;

    StatusInactiveActive(String content) {
        this.content = content;
    }

}

package com.portal.centro.API.enums;

public enum StatusInactiveActive {
    INACTIVE("inativo"),

    ACTIVE("ativo");

    private String content;

    StatusInactiveActive(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

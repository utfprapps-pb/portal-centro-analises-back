package com.portal.centro.API.enums;

public enum UserType {

    PF("Pessoa Física"),
    PJ("Pessoa Jurídica");

    private final String content;

    UserType(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

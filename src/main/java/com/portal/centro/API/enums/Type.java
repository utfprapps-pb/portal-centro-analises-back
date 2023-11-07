package com.portal.centro.API.enums;

public enum Type {
    ROLE_PROFESSOR("ROLE_PROFESSOR"),
    ROLE_STUDENT("ROLE_PROFESSOR"),
    ROLE_EXTERNAL("ROLE_PROFESSOR"),
    ROLE_ADMIN("ROLE_PROFESSOR"),
    ROLE_PARTNER("ROLE_PROFESSOR");

    private String content;

    Type (String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

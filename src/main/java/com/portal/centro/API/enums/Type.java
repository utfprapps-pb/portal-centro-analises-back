package com.portal.centro.API.enums;

public enum Type {
    ROLE_PROFESSOR("ROLE_PROFESSOR"),
    ROLE_STUDENT("ROLE_STUDENT"),
    ROLE_EXTERNAL("ROLE_EXTERNAL"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_PARTNER("ROLE_PARTNER");

    private String content;

    Type (String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

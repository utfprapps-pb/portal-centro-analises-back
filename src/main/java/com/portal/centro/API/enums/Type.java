package com.portal.centro.API.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Type implements GrantedAuthority {
    PROFESSOR("professor"),
    STUDENT("student"),
    EXTERNAL("external"),
    ADMIN("admin"),
    PARTNER("partner");

    private String content;

    Type (String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public static Type getByOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IllegalArgumentException("Invalid ordinal: " + ordinal);
        }
        return values()[ordinal];
    }

    @Override
    public String getAuthority() {
        return this.content;
    }
}

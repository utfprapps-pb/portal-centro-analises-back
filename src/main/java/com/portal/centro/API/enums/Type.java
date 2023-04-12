package com.portal.centro.API.enums;

public enum Type {
    PROFESSOR("professor"),
    STUDENT("student"),
    PARTNER("partner"),
    EXTERNAL("external");

    private String content;

    Type (String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
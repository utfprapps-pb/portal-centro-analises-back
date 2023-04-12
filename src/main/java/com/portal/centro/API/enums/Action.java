package com.portal.centro.API.enums;

public enum Action {
    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete");

    private String content;

    Action (String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
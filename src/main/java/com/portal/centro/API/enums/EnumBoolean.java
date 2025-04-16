package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum EnumBoolean {
    TRUE("Sim"),
    FALSE("Não");

    private final String content;

    EnumBoolean(String content) {
        this.content = content;
    }

}

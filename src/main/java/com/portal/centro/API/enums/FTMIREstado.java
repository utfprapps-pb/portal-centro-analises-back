package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum FTMIREstado {
    S("Sólido"),
    L("Liquído");

    private final String content;

    FTMIREstado(String content) {
        this.content = content;
    }

}

package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum CLAEModoEluicao {
    ISO("Isocártico"),
    GRA("Gradiente");

    private final String content;

    CLAEModoEluicao(String content) {
        this.content = content;
    }

}

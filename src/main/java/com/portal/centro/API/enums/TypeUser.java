package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum TypeUser {
    UTFPR("UTFPR"),
    PARTNER("Parceiro"),
    EXTERNAL("Externo");

    private final String content;

    TypeUser (String content) {
        this.content = content;
    }

}

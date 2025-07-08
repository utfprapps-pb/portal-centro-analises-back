package br.edu.utfpr.pb.app.labcaapi.enums;

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

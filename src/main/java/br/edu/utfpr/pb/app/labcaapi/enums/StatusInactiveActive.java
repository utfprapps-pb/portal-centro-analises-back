package br.edu.utfpr.pb.app.labcaapi.enums;

import lombok.Getter;

@Getter
public enum StatusInactiveActive {

    INACTIVE("Inativo"),
    ACTIVE("Ativo");

    private final String content;

    StatusInactiveActive(String content) {
        this.content = content;
    }

}

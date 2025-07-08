package br.edu.utfpr.pb.app.labcaapi.enums;

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

package br.edu.utfpr.pb.app.labcaapi.enums;

import lombok.Getter;

@Getter
public enum AAForno {
    FC("Forno de chama"),
    FG("Forno de grafite"),
    GH("Gerador de hidretos");

    private final String content;

    AAForno(String content) {
        this.content = content;
    }

}

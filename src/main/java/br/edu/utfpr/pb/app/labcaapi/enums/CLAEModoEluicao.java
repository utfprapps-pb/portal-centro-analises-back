package br.edu.utfpr.pb.app.labcaapi.enums;

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

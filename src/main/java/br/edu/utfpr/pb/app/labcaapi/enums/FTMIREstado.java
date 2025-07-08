package br.edu.utfpr.pb.app.labcaapi.enums;

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

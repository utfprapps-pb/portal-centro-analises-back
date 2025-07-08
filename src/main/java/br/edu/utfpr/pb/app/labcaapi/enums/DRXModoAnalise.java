package br.edu.utfpr.pb.app.labcaapi.enums;

import lombok.Getter;

@Getter
public enum DRXModoAnalise {
    CN("Contínuo"),
    ST("Step");

    private final String content;

    DRXModoAnalise(String content) {
        this.content = content;
    }

}

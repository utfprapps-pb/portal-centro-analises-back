package br.edu.utfpr.pb.app.labcaapi.enums;

import lombok.Getter;

@Getter
public enum CORTipoLeitura {
    CIE("CIE : L*a*b*"),
    HL("Hunter Lab: L a b");

    private final String content;

    CORTipoLeitura(String content) {
        this.content = content;
    }

}

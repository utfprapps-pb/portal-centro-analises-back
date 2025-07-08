package br.edu.utfpr.pb.app.labcaapi.enums;

import lombok.Getter;

@Getter
public enum FTMIRRegistroEspectro {
    A("Absorbância (A)"),
    T("Transmitância (T%)");

    private final String content;

    FTMIRRegistroEspectro(String content) {
        this.content = content;
    }

}

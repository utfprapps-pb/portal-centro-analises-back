package br.edu.utfpr.pb.app.labcaapi.enums;

import lombok.Getter;

@Getter
public enum DRXStep {
    _002("0,02"),
    _005("0,05");

    private final String content;

    DRXStep(String content) {
        this.content = content;
    }

}

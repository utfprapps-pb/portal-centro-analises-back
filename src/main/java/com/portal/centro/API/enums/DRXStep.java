package com.portal.centro.API.enums;

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

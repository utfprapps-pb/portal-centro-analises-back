package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum FTMIRServico {
    PK("Pastilha de KBr (somente para amostras sólidas e sem água)"),
    RA("Reflectância total atenuada (Attenuated total reflectance)"),
    RD("Refletância difusa (Difuse reflectance sampling)");

    private final String content;

    FTMIRServico(String content) {
        this.content = content;
    }

}

package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum SolicitationFileType {

    REPORT("Relatório final"),
    TICKET("Boleto"),
    INVOICE("Nota Fiscal"),
    ATTACHMENT("Anexo");

    private final String content;

    SolicitationFileType (String content) {
        this.content = content;
    }

}

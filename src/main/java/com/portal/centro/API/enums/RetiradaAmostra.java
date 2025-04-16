package com.portal.centro.API.enums;

import lombok.Getter;

@Getter
public enum RetiradaAmostra {
    TRUE("Sim, quero minhas amostras de volta e me comprometo a retirá-las dentro de 30 dias após o envio dos resultados da análise."),
    FALSE("Não, podem ser descartadas");

    private final String content;

    RetiradaAmostra(String content) {
        this.content = content;
    }

}

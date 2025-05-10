package com.portal.centro.API.enums;

import com.portal.centro.API.reports.ReportContants;
import lombok.Getter;

@Getter
public enum Reports {

    _1(1L, ReportContants.FINANCEIROS, "Financeiro"),
    _2(2L, ReportContants.EQUIPAMENTOS, "Equipamentos");

    private final Long id;
    private final String menu;
    private final String title;

    Reports(Long id, String menu, String title) {
        this.id = id;
        this.menu = menu;
        this.title = title;
    }

}


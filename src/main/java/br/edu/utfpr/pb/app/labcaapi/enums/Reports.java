package br.edu.utfpr.pb.app.labcaapi.enums;

import br.edu.utfpr.pb.app.labcaapi.reports.ReportContants;
import lombok.Getter;

@Getter
public enum Reports {

    _1(1L, ReportContants.FINANCEIROS, "Financeiro"),
    _2(2L, ReportContants.EQUIPAMENTOS, "Equipamentos"),
    _3(3L, ReportContants.SOLICITACOES, "Solicitações");

    private final Long id;
    private final String menu;
    private final String title;

    Reports(Long id, String menu, String title) {
        this.id = id;
        this.menu = menu;
        this.title = title;
    }

}


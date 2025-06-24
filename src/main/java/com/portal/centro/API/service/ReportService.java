package com.portal.centro.API.service;

import com.portal.centro.API.enums.Reports;
import com.portal.centro.API.exceptions.GenericException;
import com.portal.centro.API.reports.EquipmentExcelFactory;
import com.portal.centro.API.reports.FinanceExcelFactory;
import com.portal.centro.API.reports.ReportContants;
import com.portal.centro.API.reports.SolicitationExcelFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ReportService {

    private final FinanceExcelFactory financeExcelFactory;
    private final EquipmentExcelFactory equipmentExcelFactory;
    private final SolicitationExcelFactory solicitationExcelFactory;

    public ReportService(FinanceExcelFactory financeExcelFactory,
                         EquipmentExcelFactory equipmentExcelFactory,
                         SolicitationExcelFactory solicitationExcelFactory
    ) {
        this.financeExcelFactory = financeExcelFactory;
        this.equipmentExcelFactory = equipmentExcelFactory;
        this.solicitationExcelFactory = solicitationExcelFactory;
    }

    public byte[] generateReport(Long id) throws Exception {
        Optional<Reports> reportOptional = Arrays.stream(Reports.values())
                .filter(it -> id.equals(it.getId()))
                .findFirst();

        if (reportOptional.isPresent()) {
            Reports report = reportOptional.get();
            if (ReportContants.FINANCEIROS.equals(report.getMenu())) {
                return financeExcelFactory.gerarRelatorioCompleto();
            } else if (ReportContants.EQUIPAMENTOS.equals(report.getMenu())) {
                return equipmentExcelFactory.gerarRelatorioCompleto();
            } else if (ReportContants.SOLICITACOES.equals(report.getMenu())) {
                return solicitationExcelFactory.gerarRelatorioCompleto();
            }
        }
        throw new GenericException("Relatório não encontrado");
    }

}

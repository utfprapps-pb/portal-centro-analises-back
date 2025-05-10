package com.portal.centro.API.reports;

import com.portal.centro.API.enums.FinanceState;
import com.portal.centro.API.model.Finance;
import com.portal.centro.API.model.FinanceDetails;
import com.portal.centro.API.reports.excelFactory.ExcelFactory;
import com.portal.centro.API.reports.excelFactory.ExcelFactoryCell;
import com.portal.centro.API.reports.excelFactory.ExcelFactoryRow;
import com.portal.centro.API.reports.excelFactory.ExcelFactorySheet;
import com.portal.centro.API.service.FinanceService;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceExcelFactory {

    private final ExcelFactory excelFactory;
    private final FinanceService financeService;

    private static final ExcelFactoryRow HEADER = new ExcelFactoryRow()
            .addCell(new ExcelFactoryCell("Código", true))
            .addCell(new ExcelFactoryCell("Descrição", true))
            .addCell(new ExcelFactoryCell("Observação", true))
            .addCell(new ExcelFactoryCell("Data de Criação", true))
            .addCell(new ExcelFactoryCell("Data de Vencimento", true))
            .addCell(new ExcelFactoryCell("Responsável", true))
            .addCell(new ExcelFactoryCell("Usuário Creditado", true))
            .addCell(new ExcelFactoryCell("Usuário Debitado", true))
            .addCell(new ExcelFactoryCell("Valor", true));

    public FinanceExcelFactory(ExcelFactory excelFactory,
                               FinanceService financeService) {
        this.excelFactory = excelFactory;
        this.financeService = financeService;
    }

    public byte[] gerarRelatorioCompleto() throws Exception {
        List<Finance> dados = financeService.getAll();
        dados = dados
                .stream()
                .filter(it -> !FinanceState.PENDING.equals(it.getState()))
                .collect(Collectors.toList());
        dados.sort((a, b) -> a.getId().compareTo(b.getId()) < 0 ? -1 : 1);

        List<ExcelFactorySheet> sheets = new ArrayList<>();
        sheets.add(this.createSheetCreditos(dados));
        sheets.add(this.createSheetDebitos(dados));
        sheets.add(this.createSheetCompleto(dados));
        sheets.add(this.createSheetLaboratorio(dados));
        sheets.add(this.createSheetFinanceDetail(dados));

        return excelFactory.generateExcel(sheets);
    }

    private ExcelFactoryRow createRowByFinance(Finance finance) {
        ExcelFactoryRow row = new ExcelFactoryRow()
                .addCell(new ExcelFactoryCell().setValue(finance.getId()))
                .addCell(new ExcelFactoryCell().setValue(finance.getDescription()))
                .addCell(new ExcelFactoryCell().setValue(finance.getObservacao()))
                .addCell(new ExcelFactoryCell().setValue(finance.getCreatedAt()).setDataFormat("dd/MM/yyyy"))
                .addCell(new ExcelFactoryCell().setValue(finance.getDueDate()).setDataFormat("dd/MM/yyyy"))
                .addCell(new ExcelFactoryCell().setValue(finance.getCreatedBy().getId() + " - " + finance.getCreatedBy().getName()));

        if (FinanceState.PAID.equals(finance.getState())) {
            if (finance.getResponsavel() != null) {
                String responsavel = finance.getResponsavel().getId() + " - " + finance.getResponsavel().getName();
                row.addCell(new ExcelFactoryCell().setValue(responsavel));
            } else {
                row.addCell(new ExcelFactoryCell().setValue(null));
            }
            row.addCell(new ExcelFactoryCell().setValue(null));
        } else {
            row.addCell(new ExcelFactoryCell().setValue(null));
            if (finance.getPagador() != null) {
                String responsavel = finance.getResponsavel().getId() + " - " + finance.getResponsavel().getName();
                row.addCell(new ExcelFactoryCell().setValue(responsavel));
            } else {
                row.addCell(new ExcelFactoryCell().setValue(null));
            }
        }
        BigDecimal valor = this.getFinanceValue(finance);
        row.addCell(new ExcelFactoryCell().setValue(valor).setAlignment(HorizontalAlignment.RIGHT));
        return row;
    }

    private BigDecimal getFinanceValue(Finance finance) {
        if (FinanceState.PAID.equals(finance.getState())) {
            return finance.getValue().negate();
        } else {
            return finance.getValue();
        }
    }

    private ExcelFactorySheet createSheetCreditos(List<Finance> dados) {
        ExcelFactorySheet sheet = new ExcelFactorySheet("Créditos");
        List<ExcelFactoryRow> rows = new ArrayList<>();
        rows.add(HEADER);

        BigDecimal total = BigDecimal.ZERO;
        for (Finance finance : dados) {
            if (FinanceState.PAID.equals(finance.getState())) {
                continue;
            }
            ExcelFactoryRow row = this.createRowByFinance(finance);
            rows.add(row);
            total = total.add(this.getFinanceValue(finance));
        }

        ExcelFactoryRow row = this.createRowTotal(HEADER, total);
        rows.add(row);
        sheet.setRow(rows);
        return sheet;
    }

    private ExcelFactorySheet createSheetDebitos(List<Finance> dados) {
        ExcelFactorySheet sheet = new ExcelFactorySheet("Débitos");
        List<ExcelFactoryRow> rows = new ArrayList<>();
        rows.add(HEADER);

        BigDecimal total = BigDecimal.ZERO;
        for (Finance finance : dados) {
            if (FinanceState.RECEIVED.equals(finance.getState())) {
                continue;
            }
            ExcelFactoryRow row = this.createRowByFinance(finance);
            rows.add(row);
            total = total.add(this.getFinanceValue(finance));
        }

        ExcelFactoryRow row = this.createRowTotal(HEADER, total);
        rows.add(row);

        sheet.setRow(rows);
        return sheet;
    }

    private ExcelFactorySheet createSheetCompleto(List<Finance> dados) {
        ExcelFactorySheet sheet = new ExcelFactorySheet("Completo");
        List<ExcelFactoryRow> rows = new ArrayList<>();
        rows.add(HEADER);

        BigDecimal total = BigDecimal.ZERO;
        for (Finance finance : dados) {
            ExcelFactoryRow row = this.createRowByFinance(finance);
            rows.add(row);
            total = total.add(this.getFinanceValue(finance));
        }

        ExcelFactoryRow row = this.createRowTotal(HEADER, total);
        rows.add(row);

        sheet.setRow(rows);
        return sheet;
    }

    private ExcelFactorySheet createSheetLaboratorio(List<Finance> dados) {
        ExcelFactorySheet sheet = new ExcelFactorySheet("Laboratório");
        List<ExcelFactoryRow> rows = new ArrayList<>();
        rows.add(HEADER);

        BigDecimal total = BigDecimal.ZERO;
        for (Finance finance : dados) {
            ExcelFactoryRow row = this.createRowByFinance(finance);
            if (FinanceState.PAID.equals(finance.getState())) {
                if (finance.getResponsavel() != null) {
                    continue;
                }
            }
            rows.add(row);
            total = total.add(this.getFinanceValue(finance));
        }

        ExcelFactoryRow row = this.createRowTotal(HEADER, total);
        rows.add(row);

        sheet.setRow(rows);
        return sheet;
    }

    private ExcelFactoryRow createRowTotal(ExcelFactoryRow header, BigDecimal total) {
        int headerSize = header.getCells().size();
        ExcelFactoryRow row = new ExcelFactoryRow();
        for (int i = 0; i < headerSize - 2; i++) {
            row.addCell(new ExcelFactoryCell());
        }
        row.addCell(new ExcelFactoryCell("Total", true).setAlignment(HorizontalAlignment.RIGHT));
        row.addCell(new ExcelFactoryCell(total, true).setAlignment(HorizontalAlignment.RIGHT));

        return row;
    }

    private ExcelFactorySheet createSheetFinanceDetail(List<Finance> dados) {
        ExcelFactorySheet sheet = new ExcelFactorySheet("Detalhes do Financeiro");
        List<ExcelFactoryRow> rows = new ArrayList<>();
        ExcelFactoryRow header = new ExcelFactoryRow()
                .addCell(new ExcelFactoryCell("Código", true))
                .addCell(new ExcelFactoryCell("Data de Criação", true))
                .addCell(new ExcelFactoryCell("Responsável", true))
                .addCell(new ExcelFactoryCell("Usuário Pagador", true))
                .addCell(new ExcelFactoryCell("Equipamento", true))
                .addCell(new ExcelFactoryCell("Valor", true));

        rows.add(header);

        BigDecimal total = BigDecimal.ZERO;
        for (Finance finance : dados) {
            for (FinanceDetails detail : finance.getDetails()) {
                ExcelFactoryRow row = this.createRowByFinanceDetail(detail);
                rows.add(row);
                total = total.add(detail.getValue());
            }
        }

        ExcelFactoryRow totalRow = this.createRowTotal(header, total);
        rows.add(totalRow);

        sheet.setRow(rows);
        return sheet;
    }

    private ExcelFactoryRow createRowByFinanceDetail(FinanceDetails detail) {
        return new ExcelFactoryRow()
                .addCell(new ExcelFactoryCell().setValue(detail.getFinance().getId()))
                .addCell(new ExcelFactoryCell().setValue(detail.getCreatedAt()).setDataFormat("dd/MM/yyyy"))
                .addCell(new ExcelFactoryCell().setValue(detail.getFinance().getResponsavel().getId() + " - " + detail.getFinance().getResponsavel().getName()))
                .addCell(new ExcelFactoryCell().setValue(detail.getFinance().getPagador().getId() + " - " + detail.getFinance().getPagador().getName()))
                .addCell(new ExcelFactoryCell().setValue(detail.getEquipment().getId() + " - " + detail.getEquipment().getName()))
                .addCell(new ExcelFactoryCell().setValue(detail.getValue()).setAlignment(HorizontalAlignment.RIGHT));
    }

}

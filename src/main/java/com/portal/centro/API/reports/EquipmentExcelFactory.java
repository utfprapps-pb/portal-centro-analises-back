package com.portal.centro.API.reports;

import com.portal.centro.API.model.Equipment;
import com.portal.centro.API.model.SolicitationAmostraAnalise;
import com.portal.centro.API.reports.excelFactory.ExcelFactory;
import com.portal.centro.API.reports.excelFactory.ExcelFactoryCell;
import com.portal.centro.API.reports.excelFactory.ExcelFactoryRow;
import com.portal.centro.API.reports.excelFactory.ExcelFactorySheet;
import com.portal.centro.API.service.EquipmentService;
import com.portal.centro.API.service.SolicitationService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentExcelFactory {

    private final ExcelFactory excelFactory;
    private final EquipmentService equipmentService;
    private final SolicitationService solicitationService;


    public EquipmentExcelFactory(ExcelFactory excelFactory,
                                 EquipmentService equipmentService,
                                 SolicitationService solicitationService
    ) {
        this.excelFactory = excelFactory;
        this.equipmentService = equipmentService;
        this.solicitationService = solicitationService;
    }

    public byte[] gerarRelatorioCompleto() throws Exception {
        List<Equipment> dados = equipmentService.getAll();
        dados.sort((a, b) -> a.getId().compareTo(b.getId()) < 0 ? -1 : 1);

        List<ExcelFactorySheet> sheets = new ArrayList<>();
        sheets.add(this.createSheetEquipamentos(dados));
        sheets.add(this.createSheetEquipamentosUsos(dados));

        return excelFactory.generateExcel(sheets);
    }

    private ExcelFactorySheet createSheetEquipamentos(List<Equipment> dados) {
        List<ExcelFactoryRow> rows = new ArrayList<>();

        ExcelFactoryRow header = new ExcelFactoryRow()
                .addCell(new ExcelFactoryCell("Código", true))
                .addCell(new ExcelFactoryCell("Nome", true))
                .addCell(new ExcelFactoryCell("Nome curto", true))
                .addCell(new ExcelFactoryCell("Modelo", true))
                .addCell(new ExcelFactoryCell("Status", true))
                .addCell(new ExcelFactoryCell("Valor hora UTFPR", true))
                .addCell(new ExcelFactoryCell("Valor hora parceiro", true))
                .addCell(new ExcelFactoryCell("Valor hora externo", true))
                .addCell(new ExcelFactoryCell("Valor amostra UTFPR", true))
                .addCell(new ExcelFactoryCell("Valor amostra parceiro", true))
                .addCell(new ExcelFactoryCell("Valor amostra externo", true));

        rows.add(header);

        for (Equipment equipment : dados) {
            ExcelFactoryRow row = new ExcelFactoryRow()
                    .addCell(new ExcelFactoryCell().setValue(equipment.getId()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getName()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getShortName()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getModel()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getStatus().getContent()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getValueHourUtfpr()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getValueHourPartner()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getValueHourExternal()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getValueSampleUtfpr()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getValueSamplePartner()))
                    .addCell(new ExcelFactoryCell().setValue(equipment.getValueSampleExternal()));
            rows.add(row);
        }

        return new ExcelFactorySheet("Equipamentos", rows);
    }

    private ExcelFactorySheet createSheetEquipamentosUsos(List<Equipment> dados) throws Exception {
        List<ExcelFactoryRow> rows = new ArrayList<>();

        ExcelFactoryRow header = new ExcelFactoryRow()
                .addCell(new ExcelFactoryCell("Código", true))
                .addCell(new ExcelFactoryCell("Nome", true))
                .addCell(new ExcelFactoryCell("Data Inicio", true))
                .addCell(new ExcelFactoryCell("Data Termino", true))
                .addCell(new ExcelFactoryCell("Tempo de Operação", true));

        rows.add(header);

        for (Equipment equipment : dados) {
            List<SolicitationAmostraAnalise> analises = solicitationService.findAllAnaliseByEquipment(equipment);

            for (SolicitationAmostraAnalise analise : analises) {
                long segundos = ChronoUnit.SECONDS.between(analise.getDataini(), analise.getDatafin());


                ExcelFactoryRow row = new ExcelFactoryRow()
                        .addCell(new ExcelFactoryCell().setValue(equipment.getId()))
                        .addCell(new ExcelFactoryCell().setValue(equipment.getName()))
                        .addCell(new ExcelFactoryCell().setValue(analise.getDataini()))
                        .addCell(new ExcelFactoryCell().setValue(analise.getDatafin()))
                        .addCell(new ExcelFactoryCell().setValue(segundos / 86400.0).setDataFormat("[h]:mm:ss;@"));
                rows.add(row);
            }
        }

        return new ExcelFactorySheet("Operações", rows);
    }

}

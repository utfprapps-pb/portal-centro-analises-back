package com.portal.centro.API.reports.excelFactory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExcelFactoryRow {

    private XSSFRow row;
    private Integer index;

    private List<ExcelFactoryCell> cells;

    public List<ExcelFactoryCell> getCells() {
        if (cells == null) {
            cells = new ArrayList<>();
        }
        return cells;
    }

    public ExcelFactoryRow addCell(ExcelFactoryCell cell) {
        if (this.cells == null || this.cells.isEmpty()) {
            this.cells = new ArrayList<>();
        }
        this.cells.add(cell);
        return this;
    }
}

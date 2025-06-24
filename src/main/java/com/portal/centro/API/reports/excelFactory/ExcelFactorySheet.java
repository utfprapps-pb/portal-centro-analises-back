package com.portal.centro.API.reports.excelFactory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExcelFactorySheet {

    private String title;
    private XSSFSheet sheet;
    private List<ExcelFactoryRow> row;

    public List<ExcelFactoryRow> getRow() {
        if (row == null) {
            row = new ArrayList<>();
        }
        return row;
    }

    public ExcelFactorySheet(String title) {
        this.title = title;
    }

    public ExcelFactorySheet(String title, List<ExcelFactoryRow> row) {
        this.title = title;
        this.row = row;
    }

}

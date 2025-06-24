package com.portal.centro.API.reports.excelFactory;

import com.portal.centro.API.exceptions.GenericException;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelFactory {

    private Map<String, XSSFCellStyle> styles = new HashMap<>();

    public byte[] generateExcel(List<ExcelFactorySheet> sheetsFactoryList) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        int columnLength = 0;
        try (ByteArrayOutputStream fos = new ByteArrayOutputStream()) {
            for (ExcelFactorySheet sheetFactory : sheetsFactoryList) {
                XSSFSheet sheet = this.addSheet(workbook, sheetFactory.getTitle());
                List<ExcelFactoryRow> rows = sheetFactory.getRow();

                for (int i = 0; i < rows.size(); i++) {
                    ExcelFactoryRow row = rows.get(i);
                    row.setRow(sheet.createRow(i));

                    if (row.getIndex() == null) {
                        row.setIndex(i);
                    }

                    addRow(sheet, row);

                    if (row.getCells().size() > columnLength) {
                        columnLength = row.getCells().size();
                    }
                }
            }

            this.adjustWidthAllSheets(workbook);
            this.resetPosition(workbook);

            workbook.write(fos);
            return fos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            workbook.close();
            this.styles = new HashMap<>();
        }
    }

    private void adjustWidthAllSheets(XSSFWorkbook workbook) {
        int sheet_ativa_index = workbook.getActiveSheetIndex();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            workbook.setActiveSheet(i);
            int first_row_index = workbook.getSheetAt(i).getFirstRowNum();
            if (workbook.getSheetAt(i).getRow(first_row_index) == null) {
                continue;
            }
            int last_cell_index = workbook.getSheetAt(i).getRow(first_row_index).getLastCellNum();
            for (int j = 0; j <= last_cell_index; j++) {
                workbook.getSheetAt(i).autoSizeColumn(j);
                workbook.getSheetAt(i).setColumnWidth(j, workbook.getSheetAt(i).getColumnWidth(j) + (4 * 256));
            }
        }
        workbook.setActiveSheet(sheet_ativa_index);
    }

    private void resetPosition(XSSFWorkbook workbook) {
        this.activeSheet(workbook, workbook.getSheetAt(0));
        this.getActiveSheet(workbook).setActiveCell(new CellAddress(0, 0));
    }

    private XSSFSheet addSheet(XSSFWorkbook workbook, String title) {
        if (title == null) {
            return workbook.createSheet();
        } else {
            return workbook.createSheet(title);
        }
    }

    private void activeSheet(XSSFWorkbook workbook, XSSFSheet sheet) {
        workbook.setActiveSheet(workbook.getSheetIndex(sheet));
    }

    private XSSFSheet getActiveSheet(XSSFWorkbook workbook) {
        return workbook.getSheetAt(workbook.getActiveSheetIndex());
    }

    private void addRow(XSSFSheet sheet, ExcelFactoryRow simpleRow) throws Exception {
        if (simpleRow.getIndex() == null) {
            simpleRow.setIndex(sheet.getLastRowNum() + 1);
        }
        simpleRow.setRow(sheet.createRow(simpleRow.getIndex()));
        XSSFRow row = simpleRow.getRow();

        for (int i = 0; i < simpleRow.getCells().size(); i++) {
            ExcelFactoryCell cell = simpleRow.getCells().get(i);
            cell.setIndex(i);
            cell.setCell(row.createCell(i));

            this.setValueToCell(cell);
        }
    }

    private void setValueToCell(ExcelFactoryCell customCell) throws Exception {
        XSSFCell cell = customCell.getCell();
        if (customCell.getValue() != null) {
            if (customCell.getValue() instanceof Long) {
                cell.setCellValue((Long) customCell.getValue());
            } else if (customCell.getValue() instanceof Integer) {
                cell.setCellValue((Integer) customCell.getValue());
            } else if (customCell.getValue() instanceof String) {
                cell.setCellValue((String) customCell.getValue());
            } else if (customCell.getValue() instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal) customCell.getValue()).doubleValue());
            } else if (customCell.getValue() instanceof LocalDateTime) {
                cell.setCellValue((LocalDateTime) customCell.getValue());
            } else if (customCell.getValue() instanceof Date) {
                cell.setCellValue((Date) customCell.getValue());
            } else if (customCell.getValue() instanceof Double) {
                cell.setCellValue((Double) customCell.getValue());
            } else if (customCell.getValue() instanceof Boolean) {
                cell.setCellValue((Boolean) customCell.getValue());
            } else {
                throw new GenericException("Não identificado");
            }
        }

        cell.setCellStyle(this.getStyleRow(customCell));
    }

    private Map<String, XSSFCellStyle> getStyles() {
        return styles;
    }

    private XSSFCellStyle getStyleRow(ExcelFactoryCell cell) {
        return this.getStyleRow(cell, (short) 11, cell.getBold(), cell.getDataFormat(), cell.getAlignment(), cell.getFontColor());
    }

    private XSSFCellStyle getStyleRow(ExcelFactoryCell cell, short fontSize, boolean bold, String dataFormat, HorizontalAlignment alignment, IndexedColors fontColor) {
        String key = "fntsz:" + fontSize;
        key += "bl:" + bold;
        key += "alg:" + alignment;
        key += "dtfmt:" + dataFormat;
        key += "fntclr:" + fontColor.getIndex();

        XSSFCellStyle ret;
        XSSFWorkbook workbook = cell.getCell().getSheet().getWorkbook();
        if (this.getStyles().get(key) != null) {
            return this.getStyles().get(key);
        } else {
            XSSFFont fonte = workbook.createFont();
            fonte.setFontHeightInPoints(fontSize);
            fonte.setFontName("Arial");
            fonte.setColor(fontColor.getIndex());
            ret = cell.getCell().getSheet().getWorkbook().createCellStyle();

            ret.setFont(fonte);
            ret.setAlignment(alignment);
            ret.setVerticalAlignment(VerticalAlignment.CENTER);

            if (bold) {
                fonte.setBold(true);
            }
            if (dataFormat != null) {
                ret.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(dataFormat));
            }

            this.getStyles().put(key, ret);
        }
        return ret;
    }

}

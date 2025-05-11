package com.portal.centro.API.reports.excelFactory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExcelFactoryCell {

    private Object value;

    private XSSFCell cell;
    private int index;

    private Boolean bold;
    private IndexedColors fontColor;

    private HorizontalAlignment alignment = HorizontalAlignment.LEFT;

    private String dataFormat;

    public ExcelFactoryCell(Object value, Boolean bold) {
        this.value = value;
        this.bold = bold;
    }

    public ExcelFactoryCell setAlignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public ExcelFactoryCell setIndex(int index) {
        this.index = index;
        return this;
    }

    public ExcelFactoryCell setValue(Object value) {
        this.value = value;
        return this;
    }

    public Boolean getBold() {
        return bold != null ? bold : false;
    }

    public ExcelFactoryCell setBold(Boolean bold) {
        this.bold = bold;
        return this;
    }

    public IndexedColors getFontColor() {
        return fontColor != null ? fontColor : IndexedColors.BLACK;
    }

    public ExcelFactoryCell setFontColor(IndexedColors fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public ExcelFactoryCell setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
        return this;
    }

    public String getDataFormat() {
        if (this.getValue() != null) {
            if (this.dataFormat != null) {
                return this.dataFormat;
            }
            if (this.getValue() instanceof BigDecimal) {
                return "#,##.00##;[Red]-#,##.00##";
            }
            if (this.getValue() instanceof LocalDateTime) {
                return "dd/MM/yyyy HH:mm:ss";
            }
        }
        return null;
    }

}

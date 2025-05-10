package com.portal.centro.API.reports.excelFactory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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

    // <------ Personalização das Cells ------>
    private Boolean bold;
    private IndexedColors fontColor;
    private IndexedColors foregroundColor;

    private HorizontalAlignment alignment = HorizontalAlignment.LEFT;
    private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;

    private String dataFormat;

    //Bordas
    private BorderStyle borderTop;
    private BorderStyle borderBottom;
    private BorderStyle borderLeft;
    private BorderStyle borderRight;

    private IndexedColors borderTopColor;
    private IndexedColors borderBottomColor;
    private IndexedColors borderLeftColor;
    private IndexedColors borderRightColor;

    public ExcelFactoryCell(Object value, Boolean bold) {
        this.value = value;
        this.bold = bold;
        this.setDataFormat(null);
    }

    public ExcelFactoryCell(Object value, HorizontalAlignment horizontalAlignment, Boolean bold) {
        this.value = value;
        this.alignment = horizontalAlignment;
        this.bold = bold;
        this.setDataFormat(null);
    }

    public ExcelFactoryCell setBorders(BorderStyle border) {
        this.setBorderTop(border);
        this.setBorderRight(border);
        this.setBorderBottom(border);
        this.setBorderLeft(border);
        return this;
    }

    public ExcelFactoryCell setBordersColor(IndexedColors borderColor) {
        this.setBorderTopColor(borderColor);
        this.setBorderRightColor(borderColor);
        this.setBorderBottomColor(borderColor);
        this.setBorderLeftColor(borderColor);
        return this;
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

    public ExcelFactoryCell setForegroundColor(IndexedColors foregroundColor) {
        this.foregroundColor = foregroundColor;
        return this;
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment != null ? verticalAlignment : VerticalAlignment.BOTTOM;
    }

    public ExcelFactoryCell setBorderTop(BorderStyle borderTop) {
        this.borderTop = borderTop;
        return this;
    }

    public ExcelFactoryCell setBorderBottom(BorderStyle borderBottom) {
        this.borderBottom = borderBottom;
        return this;
    }

    public ExcelFactoryCell setBorderLeft(BorderStyle borderLeft) {
        this.borderLeft = borderLeft;
        return this;
    }

    public ExcelFactoryCell setBorderRight(BorderStyle borderRight) {
        this.borderRight = borderRight;
        return this;
    }

    public IndexedColors getBorderTopColor() {
        return borderTopColor != null ? borderTopColor : IndexedColors.BLACK;
    }

    public ExcelFactoryCell setBorderTopColor(IndexedColors borderTopColor) {
        this.borderTopColor = borderTopColor;
        return this;
    }

    public IndexedColors getBorderBottomColor() {
        return borderBottomColor != null ? borderBottomColor : IndexedColors.BLACK;
    }

    public ExcelFactoryCell setBorderBottomColor(IndexedColors borderBottomColor) {
        this.borderBottomColor = borderBottomColor;
        return this;
    }

    public IndexedColors getBorderLeftColor() {
        return borderLeftColor != null ? borderLeftColor : IndexedColors.BLACK;
    }

    public ExcelFactoryCell setBorderLeftColor(IndexedColors borderLeftColor) {
        this.borderLeftColor = borderLeftColor;
        return this;
    }

    public IndexedColors getBorderRightColor() {
        return borderRightColor != null ? borderRightColor : IndexedColors.BLACK;
    }

    public ExcelFactoryCell setBorderRightColor(IndexedColors borderRightColor) {
        this.borderRightColor = borderRightColor;
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

//    public ExcelFactoryCell setDataFormat(String format) {
//        if (this.getValue() != null) {
//            if (format != null) {
//                this.dataFormat = format;
//                return this;
//            }
//            if (this.getValue() instanceof BigDecimal) {
//                this.dataFormat = "#.##,00##;[Red]-#.##,00##";
//                return this;
//            }
//            if (this.getValue() instanceof LocalDateTime) {
//                this.dataFormat = "dd/MM/yyyy HH:mm:ss";
//                return this;
//            }
//        }
//        return this;
//    }
}

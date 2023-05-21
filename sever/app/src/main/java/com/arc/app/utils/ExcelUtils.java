package com.arc.app.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ExcelUtils {
    public static Date convertToDate(Cell cell) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false);
        try {
            if (cell != null) {
                if (cell.getCellType() == CellType.STRING) {
                    return dateFormat.parse(cell.getStringCellValue());
                } else if (DateUtil.isCellDateFormatted(cell)) {
                    return dateFormat.parse(dateFormat.format(cell.getDateCellValue()));
                }
            }
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi convert date tại: " + cell.getAddress());
        }
        return null;
    }

    public static CellStyle getCellStyle(Workbook wb, Boolean isAlignment, Boolean isBold, Boolean isItalic) {
        CellStyle result = wb.createCellStyle();
        // font
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 13);
        // bold
        if (isBold) {
            font.setBold(true);
        }
        // italic
        if (isItalic) {
            font.setItalic(true);
        }
        // alignment
        if (isAlignment) {
            result.setAlignment(HorizontalAlignment.CENTER);
        } else {
            result.setAlignment(HorizontalAlignment.LEFT);
        }
        result.setBorderTop(BorderStyle.THIN);
        result.setBorderBottom(BorderStyle.THIN);
        result.setBorderRight(BorderStyle.THIN);
        result.setBorderLeft(BorderStyle.THIN);
        result.setWrapText(true);
        result.setFont(font);
        return result;
    }

    public static void mergeCellAddress(Sheet sheet, int firstRow, int lastRow, int firstColumn, int lastColumn) {
        CellRangeAddress merge = new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn);
        sheet.addMergedRegion(merge);
        RegionUtil.setBorderTop(BorderStyle.THIN, merge, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, merge, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, merge, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, merge, sheet);
    }

}

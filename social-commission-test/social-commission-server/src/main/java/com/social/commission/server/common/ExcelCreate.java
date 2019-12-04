package com.social.commission.server.common;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class ExcelCreate {
    private HSSFWorkbook workbook = null;
    private HSSFSheet sheet = null;
    private HSSFRow row = null;

    public ExcelCreate() {
        this.workbook = new HSSFWorkbook();
        HSSFFont font2 = this.workbook.createFont();
        font2.setFontName("仿宋_GB2312");
        font2.setFontName("黑体");
        font2.setBoldweight((short) 700);
        font2.setFontHeightInPoints((short) 24);
        this.sheet = this.workbook.createSheet();
    }

    public HSSFWorkbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public HSSFSheet getSheet() {
        return this.sheet;
    }

    public void setSheet(HSSFSheet sheet) {
        this.sheet = sheet;
    }

    public HSSFRow getRow() {
        return this.row;
    }

    public void setRow(HSSFRow row) {
        this.row = row;
    }

    public void createRow(int index) {
        this.row = this.sheet.createRow(index);
    }

    public void downloadExcel(HSSFWorkbook workbook, HttpServletResponse response, String filename) throws IOException {
        OutputStream out = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
        workbook.write(out);
        out.flush();
        out.close();
    }

    public void downloadReportExcel(HSSFWorkbook workbook, HttpServletResponse response, String filename) throws IOException {
        OutputStream out = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        response.setContentType("application/msexcel;charset=UTF-8");
        workbook.write(out);
        out.flush();
        out.close();
    }

    public void setCell(int index, String value) {
        HSSFCell cell = this.row.createCell((short)index);
        HSSFCellStyle cellStyle2 = this.workbook.createCellStyle();
        HSSFDataFormat format = this.workbook.createDataFormat();
        cellStyle2.setDataFormat(format.getFormat("@"));
        cell.setCellStyle(cellStyle2);
        cell.setCellType(1);
        cell.setCellValue(value);
    }

    public void setCells(int index, String value) {
        HSSFCell cell = this.row.createCell((short)index);
        HSSFCellStyle cellStyle2 = this.workbook.createCellStyle();
        HSSFDataFormat format = this.workbook.createDataFormat();
        cellStyle2.setDataFormat(format.getFormat("@"));
        cell.setCellStyle(cellStyle2);
        cell.setCellType(1);
        cellStyle2.setBorderBottom((short) 1);
        cellStyle2.setBorderLeft((short) 1);
        cellStyle2.setBorderTop((short) 1);
        cellStyle2.setBorderRight((short) 1);
        cellStyle2.setAlignment((short) 2);
        cell.setCellValue(value);
    }

    public void setCell2(int index, String value) {
        HSSFCell cell = this.row.createCell((short)index);
        cell.setCellValue(value);
        HSSFCellStyle cellStyle2 = this.workbook.createCellStyle();
        HSSFDataFormat format = this.workbook.createDataFormat();
        cellStyle2.setDataFormat(format.getFormat("¥#,##0"));
        cell.setCellStyle(cellStyle2);
        cell.setCellType(1);
        cellStyle2.setBorderBottom((short) 1);
        cellStyle2.setBorderLeft((short) 1);
        cellStyle2.setBorderTop((short) 1);
        cellStyle2.setBorderRight((short) 1);
        cellStyle2.setAlignment((short) 2);
    }

    public void setCellStyle(int row, int col) {
        HSSFRow onerow = this.sheet.getRow((short)row);
        HSSFCell cell = onerow.getCell((short)col);
        HSSFCellStyle cellStyle = this.workbook.createCellStyle();
        cellStyle.setAlignment((short) 2);
        cell.setCellStyle(cellStyle);
    }

    public void addMergedRegion(Region region) {
        this.sheet.addMergedRegion(region);
    }

    public void setCellTitle(int index, String value) {
        HSSFCell cell = this.row.createCell((short)index);
        HSSFCellStyle cellStyle2 = this.workbook.createCellStyle();
        HSSFDataFormat format = this.workbook.createDataFormat();
        cellStyle2.setDataFormat(format.getFormat("@"));
        cell.setCellStyle(cellStyle2);
        cell.setCellType(1);
        cellStyle2.setBorderBottom((short) 1);
        cellStyle2.setBorderLeft((short) 1);
        cellStyle2.setBorderTop((short) 1);
        cellStyle2.setBorderRight((short) 1);
        cellStyle2.setAlignment((short) 2);
        cell.setCellValue(value);
    }

    public void setCellTitle(int firstRow, int lastRow, int firstRank, int lastRank, String value) {
        for (int i = firstRank; i < lastRank + 1; i++) {
            this.sheet.getRow(firstRow).createCell(i);
            if (i == firstRank) {
                this.sheet.getRow(firstRow).getCell(i).setCellValue(StringUtils.isEmpty(value) ? "" : value);
            }
        }
        CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstRank, lastRank);
        RegionUtil.setBorderBottom(1, cra, this.sheet, this.workbook);
        RegionUtil.setBorderLeft(1, cra, this.sheet, this.workbook);
        RegionUtil.setBorderRight(1, cra, this.sheet, this.workbook);
        RegionUtil.setBorderTop(1, cra, this.sheet, this.workbook);
        this.sheet.addMergedRegion(cra);
    }

    public void setCellTitle1(int index, String value) {
        HSSFCell cell = this.row.createCell((short)index);
        HSSFCellStyle cellStyle2 = this.workbook.createCellStyle();
        HSSFDataFormat format = this.workbook.createDataFormat();
        cellStyle2.setDataFormat(format.getFormat("@"));
        cell.setCellStyle(cellStyle2);
        cell.setCellType(1);
        cell.setCellValue(value);
    }

    public void setCellWidth(int index, int width) {
        this.sheet.setColumnWidth(index, 256 * width);
    }
}

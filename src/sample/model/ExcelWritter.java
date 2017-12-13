package sample.model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.DTO.TroubleTicket;


import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by m80028770 on 8/7/2017.
 */
public class ExcelWritter {

    public void writeClearTT(ArrayList<TroubleTicket> data, File file) {

        System.out.println("ExcelWritter.writeClearTT ... writing " + data.size() + " records");


        Workbook wb = new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet("Clear TT");

        Row header = sheet1.createRow(0);

        // set headers
        int cl = 0;
        while (cl < TroubleTicket.HEADERS.length) {
            header.createCell(cl).setCellValue(TroubleTicket.HEADERS[cl]);
            cl++;
        }

        //test

        for (int i = 0; i < data.size(); i++) {
            Row row = sheet1.createRow(i + 1);

            cl = 0;
            row.createCell(cl++).setCellValue(data.get(i).getOrderid());
            row.createCell(cl++).setCellValue(data.get(i).getTitle());
            row.createCell(cl++).setCellValue(data.get(i).getProcessstatus());
            row.createCell(cl++).setCellValue(data.get(i).getServerSerial());



        }


        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private XSSFCellStyle getHeaderStyle(Workbook wb) {

        Font font_white = wb.createFont();
        font_white.setColor(IndexedColors.WHITE.getIndex());
        font_white.setBold(true);


        // Header Style
        XSSFCellStyle cs1 = (XSSFCellStyle) wb.createCellStyle();
        cs1.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 112, 192)));
        cs1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs1.setFont(font_white);
        cs1.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        cs1.setTopBorderColor(IndexedColors.WHITE.getIndex());
        cs1.setRightBorderColor(IndexedColors.WHITE.getIndex());
        cs1.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        cs1.setBorderBottom(CellStyle.BORDER_THIN);
        cs1.setBorderTop(CellStyle.BORDER_THIN);
        cs1.setBorderRight(CellStyle.BORDER_THIN);
        cs1.setBorderLeft(CellStyle.BORDER_THIN);

        return cs1;
    }

    private XSSFCellStyle getCellStyle(Workbook wb) {


        // Cell Style
        XSSFCellStyle cs2 = (XSSFCellStyle) wb.createCellStyle();
        cs2.setFillForegroundColor(new XSSFColor(new java.awt.Color(221, 235, 247)));
        cs2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //cs2.setFont(font);
        cs2.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        cs2.setTopBorderColor(IndexedColors.WHITE.getIndex());
        cs2.setRightBorderColor(IndexedColors.WHITE.getIndex());
        cs2.setLeftBorderColor(IndexedColors.WHITE.getIndex());
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);

//        CreationHelper createHelper = wb.getCreationHelper();
//        cs2.setDataFormat(createHelper.createDataFormat().getFormat("[h]:mm:ss"));

        return cs2;

    }

    private XSSFCellStyle getCellStyle_timeRange(Workbook wb) {


        XSSFCellStyle cs2 = getCellStyle(wb);

        CreationHelper createHelper = wb.getCreationHelper();
        cs2.setDataFormat(createHelper.createDataFormat().getFormat("[h]:mm:ss"));

        return cs2;

    }

}

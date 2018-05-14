package com.example.nordicmotorhomes.utilities;

import com.example.nordicmotorhomes.models.Customer;
import com.example.nordicmotorhomes.models.Staff;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {
    private final int A = 0;
    private final int B = 1;
    private final int C = 2;
    private final int D = 3;
    private final int E = 4;
    private final int F = 5;
    private final int G = 6;
    private final int H = 7;
    private final int I = 8;

    public void generateFinalBill(Customer customer){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bill");

        createHeader(workbook, sheet, customer);
        createSquare(workbook, sheet);

        writeToFile(workbook, sheet, "");
    }

    private void createHeader(XSSFWorkbook workbook, XSSFSheet sheet, Customer customer){
        Row row = sheet.createRow(1);
        Cell cell = row.createCell(2);

        cell.setCellValue("Nordic Motorhomes");

        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(font);

        cell.setCellStyle(cellStyle);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 5));

        //create the lease contract number
        row = sheet.createRow(3);
        cell = row.createCell(D);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, D, E));
        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Lease contract");

        //create no. #
        row = sheet.createRow(4);
        cell = row.createCell(D);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, D, E));
        cell.setCellStyle(cellStyle);
        cell.setCellValue("no. 1");

        //create owner/customer labels
        row = sheet.createRow(6);
        cell = row.createCell(B);
        font = workbook.createFont();
        font.setBold(true);
        cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Owner");

        cell = row.createCell(F);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Customer");

        //create owner/customer info
        row = sheet.createRow(7);
        cell = row.createCell(B);
        cell.setCellValue("Nordic Motorhomes A/S");

        cell = row.createCell(F);
        cell.setCellValue(customer.getFirstName() + " " + customer.getLastName());

        row = sheet.createRow(8);
        cell = row.createCell(B);
        cell.setCellValue("Sample lane 1");

        cell = row.createCell(F);
        cell.setCellValue(customer.getAddress());

        row = sheet.createRow(9);
        cell = row.createCell(B);
        cell.setCellValue("+4527280136");

        cell = row.createCell(F);
        cell.setCellValue(customer.getCity() + ", " + customer.getPostCode());

        row = sheet.createRow(10);
        cell = row.createCell(B);
        cell.setCellValue("+4527280136");

        cell = row.createCell(F);
        cell.setCellValue(customer.getPhone());
    }

    private void createSquare(XSSFWorkbook workbook, XSSFSheet sheet){
        int top = 13;
        int bottom = top + 7;
        int left = B;
        int right = G;
        for(int i = top; i <= bottom; i++){
            Row row = sheet.createRow(i);
            for(int y = left; y <= right; y++){

                if(y != left && i != top && i != bottom ){
                    y = right;
                }

                Cell cell = row.createCell(y);
                CellStyle style = workbook.createCellStyle();

                if(i == top){
                    style.setBorderTop(BorderStyle.THICK);
                } else if(i == bottom){
                    style.setBorderBottom(BorderStyle.THICK);
                }

                if(y == left){
                    style.setBorderLeft(BorderStyle.THICK);
                } else if(y == right){
                    style.setBorderRight(BorderStyle.THICK);
                }

                cell.setCellStyle(style);
            }
        }

        XSSFFont font = workbook.createFont();
        font.setBold(true);

        //base style
        CellStyle style = workbook.createCellStyle();
        style.setBorderRight(BorderStyle.HAIR);
        style.setBorderLeft(BorderStyle.HAIR);
        style.setBorderTop(BorderStyle.HAIR);
        style.setBorderBottom(BorderStyle.HAIR);
        style.setFont(font);

        //create the top row
        Row row = sheet.createRow(top - 1);
        Cell cell = row.createCell(E);
        cell.setCellStyle(style);
        cell.setCellValue("B. Price");

        cell = row.createCell(F);
        cell.setCellStyle(style);
        cell.setCellValue("Duration");

        cell = row.createCell(G);
        cell.setCellStyle(style);
        cell.setCellValue("Price");

        //create the total of the table
        row = sheet.createRow(bottom+1);
        cell = row.createCell(F);
        cell.setCellStyle(style);
        cell.setCellValue("Total");

        //reset style
        style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setBorderBottom(BorderStyle.THICK);
        style.setBorderLeft(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.THICK);

        cell = row.createCell(G);
        cell.setCellStyle(style);
    }

    private void createTermsAndConditions(XSSFWorkbook workbook, XSSFSheet sheet){

    }

    private void createSignatures(XSSFWorkbook workbook, XSSFSheet sheet, Customer customer, Staff staff){
        Row row = sheet.createRow(35);
        Cell cell = row.createCell(B);

        //create bold style for titles
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);

        cell.setCellStyle(style);
        cell.setCellValue("Nordic Motorhomes A/S");

        cell = row.createCell(F);
        cell.setCellStyle(style);
        cell.setCellValue("Customer");

        //write down names
        row = sheet.createRow(36);
        cell = row.createCell(B);
        cell.setCellValue(staff.getFirstName() + " " + staff.getLastName());

        cell = row.createCell(F);
        cell.setCellValue(customer.getFirstName() + " " + customer.getLastName());

        //write down phone numbers/cpr
        row = sheet.createRow(37);
        cell = row.createCell(B);
        cell.setCellValue(staff.getPhone());

        cell = row.createCell(F);
        cell.setCellValue("CPR: " + customer.getCPR());

        //write down the position
        row = sheet.createRow(38);
        cell = row.createCell(B);
        cell.setCellValue(staff.getPosition());

        //make signature lines
            //create style
        style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THICK);

        row = sheet.createRow(40);
        cell = row.createCell(B);
        cell.setCellStyle(style);
        cell = row.createCell(C);
        cell.setCellStyle(style);

        cell = row.createCell(F);
        cell.setCellStyle(style);
        cell = row.createCell(G);
        cell.setCellStyle(style);
    }

    private void populateSquare(XSSFWorkbook workbook, XSSFSheet sheet){

    }

    private void writeToFile(XSSFWorkbook workbook, XSSFSheet sheet, String fileName){

        StringBuilder str = new StringBuilder();
        str.append("files/").append(fileName).append(".xlsx");
        fileName = str.toString();

        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

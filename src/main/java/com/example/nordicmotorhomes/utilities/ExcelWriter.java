package com.example.nordicmotorhomes.utilities;

import com.example.nordicmotorhomes.models.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ExcelWriter {
    private static final int A = 0;
    private static final int B = 1;
    private static final int C = 2;
    private static final int D = 3;
    private static final int E = 4;
    private static final int F = 5;
    private static final int G = 6;
    private static final int H = 7;
    private static final int I = 8;
    private static final int MaxRows = 17;
    private static final int StartRow = 13;

    public boolean generateFinalBill(Customer customer, Reservation reservation,
                                     List<Payment> payments, List<Extra> extras,
                                     MotorHouse motorHouse, String filename,
                                     List<Delivery> deliveries){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Bill");

        createHeader(workbook, sheet, customer, reservation.getId());
        createTermsAndConditions(workbook, sheet);
        createSignatures(workbook, sheet, customer);
        int length = populateSquare(workbook, sheet, payments, extras, reservation, motorHouse, deliveries);
        createSquare(workbook, sheet, length);
        footer(workbook, sheet);

        return writeToFile(workbook, filename);
    }

    private void createHeader(XSSFWorkbook workbook, XSSFSheet sheet, Customer customer, int reservationID){
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
        cell.setCellValue("no. " + reservationID);

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

    private void createSquare(XSSFWorkbook workbook, XSSFSheet sheet, int length){
        int top = 13;
        int bottom = top + length;
        int left = B;
        int right = H;
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

    private void createSignatures(XSSFWorkbook workbook, XSSFSheet sheet, Customer customer){
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
        //cell.setCellValue(staff.getFirstName() + " " + staff.getLastName());
        cell.setCellValue("staff name");

        cell = row.createCell(F);
        cell.setCellValue(customer.getFirstName() + " " + customer.getLastName());

        //write down phone numbers/cpr
        row = sheet.createRow(37);
        cell = row.createCell(B);
        //cell.setCellValue(staff.getPhone());
        cell.setCellValue("staff phone");

        cell = row.createCell(F);
        cell.setCellValue("CPR: " + customer.getCPR());

        //write down the position
        row = sheet.createRow(38);
        cell = row.createCell(B);
        //cell.setCellValue(staff.getPosition());
        cell.setCellValue("staff position");

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

    private int populateSquare(XSSFWorkbook workbook, XSSFSheet sheet, List<Payment> payments,
                               List<Extra> extras, Reservation reservation, MotorHouse motorHouse,
                               List<Delivery> deliveries){
        Row row;
        Cell cell;

        //count the rows in the square
        int rows = 0;

        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);

        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setAlignment(HorizontalAlignment.CENTER);
        boldStyle.setFont(font);


        row = sheet.createRow(StartRow + rows);
        rows += 2;
        cell = row.createCell(B);
        sheet.addMergedRegion(new CellRangeAddress(StartRow, StartRow, B, D));

        cell.setCellValue(reservation.getMotorhouseName());
        cell.setCellStyle(boldStyle);

        //set base price
        cell = row.createCell(E);
        cell.setCellValue(motorHouse.getPrice());

        //set duration
        cell = row.createCell(F);
        cell.setCellValue(ChronoUnit.DAYS.between(reservation.getDateFrom(), reservation.getDateTo()));

        //set total row price
        cell = row.createCell(G);
        cell.setCellValue(ChronoUnit.DAYS.between(reservation.getDateFrom(), reservation.getDateTo()) * motorHouse.getPrice());

        //insert all the deliveries
        if(!deliveries.isEmpty()){
            row = sheet.createRow(StartRow + rows);
            rows++;

            cell = row.createCell(B);
            sheet.addMergedRegion(new CellRangeAddress(StartRow + rows - 1, StartRow + rows - 1, C, D));
            cell.setCellStyle(boldStyle);
            cell.setCellValue("Delivery");

            double km = 0;

            for(Delivery delivery : deliveries){
                km += delivery.getDistance();
            }

            cell = row.createCell(E);
            cell.setCellValue("0.70");

            cell = row.createCell(F);
            cell.setCellValue(km + " km.");

            cell = row.createCell(G);
            cell.setCellValue(km * 0.7);
        }

        if(extras.size() + rows < MaxRows){
            row = sheet.createRow(StartRow + rows);
            rows++;
            cell = row.createCell(C);
            sheet.addMergedRegion(new CellRangeAddress(StartRow + rows - 1, StartRow + rows - 1, C, D));
            cell.setCellStyle(boldStyle);
            cell.setCellValue("Extras:");

            for(Extra extra : extras){
                row = sheet.createRow(StartRow + rows);
                rows++;

                row.createCell(D).setCellValue(extra.getName());
                row.createCell(G).setCellValue(extra.getPrice());
            }
        }

        double paid = 0;

        for(Payment payment : payments){
            paid += payment.getAmmount();
        }

        rows += 2;

        row = sheet.createRow(StartRow + rows);
        row.createCell(G).setCellValue(reservation.getTotal());

        row = sheet.createRow(32);
        row.createCell(B).setCellValue("Paid: ");
        cell = row.createCell(C);
        cell.setCellStyle(boldStyle);
        cell.setCellValue(paid);
        row.createCell(D).setCellValue("EUR.");

        return rows;
    }

    private void footer(XSSFWorkbook workbook, XSSFSheet sheet){
        Row row = sheet.createRow(46);
        Cell cell = row.createCell(E);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        cell.setCellStyle(style);
        cell.setCellValue("2018");

        row = sheet.createRow(47);
        cell = row.createCell(E);

        cell.setCellStyle(style);
        cell.setCellValue("Copenhagen");
    }

    private boolean writeToFile(XSSFWorkbook workbook, String fileName){

        StringBuilder str = new StringBuilder();
        str.append("src/main/resources/files/").append(fileName).append(".xlsx");
        fileName = str.toString();

        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}

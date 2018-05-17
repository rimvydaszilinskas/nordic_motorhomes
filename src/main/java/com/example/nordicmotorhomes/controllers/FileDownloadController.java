package com.example.nordicmotorhomes.controllers;

import com.example.nordicmotorhomes.models.*;
import com.example.nordicmotorhomes.repositories.*;
import com.example.nordicmotorhomes.utilities.ExcelWriter;
import com.example.nordicmotorhomes.utilities.PriceCalculator;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

@Controller
public class FileDownloadController {

    private static final String FILEPATH = "files/";
    private static final String EXTENSION = ".xlsx";
    private IPerson<Customer> customerRepository = new PersonRepository();
    private IReservation reservationRepository = new ReservationRepository();
    private IExtras extrasRepository = new ExtrasRepository();
    private IPayment paymentRepository = new PaymentRepository();
    private IMotorHouse motorHouseRepository = new MotorHouseRepo();

    @GetMapping("/bills/{type}/{id}")
    public void downloadFile(@PathVariable("type")String type,
            @PathVariable("reservationID")int id,
            HttpServletResponse response) throws Exception{
        String filename = buildFilename(id, type);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        if(!file.exists()){
            //if file not exists create file
            ExcelWriter writer = new ExcelWriter();
            //get the required data
            Reservation reservation = reservationRepository.get(id);
            Customer customer = customerRepository.get(reservation.getCustomerID());
            List<Extra> extras = extrasRepository.getReservation(id);
            List<Payment> payments = paymentRepository.getReservationPayments(id);
            MotorHouse motorHouse = motorHouseRepository.get(reservation.getMotorhouseID());
            motorHouse.setPrice(PriceCalculator.GetPrice(motorHouse.getPrice()));
            //create file
            writer.generateFinalBill(customer, reservation, payments, extras, motorHouse, buildRawFilename(id, type), new LinkedList<>());
        }

        //get file
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());

        response.setContentType(mimeType);

        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

        response.setContentLength((int) file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        FileCopyUtils.copy(inputStream, response.getOutputStream());

        file.delete();
    }

    private String buildFilename(int id, String type){
        StringBuilder str = new StringBuilder(FILEPATH);

        str.append(buildRawFilename(id, type));

        str.append(EXTENSION);
        return str.toString();
    }

    private String buildRawFilename(int id, String type){
        StringBuilder str = new StringBuilder();
        //if type is final make a files/invoice_{id}.xlsx
        if(type.equals("final")){
            str.append("invoice_").append(id);
        } else if(type.equals("receipt")){
            //if type is receipt make a files/receipt_{id}.xlsx
            str.append("receipt_").append(id);
        }
        return str.toString();
    }

}

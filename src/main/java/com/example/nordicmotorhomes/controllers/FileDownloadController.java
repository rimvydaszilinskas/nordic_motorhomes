package com.example.nordicmotorhomes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;

@Controller
public class FileDownloadController {

    private static final String FILENAME = "files/test.pdf";
    private static final String EXTERNAL_FILEPATH = "C:/";

    @RequestMapping(value="/download/{type}", method= RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("type") String type) throws Exception{
        File file = null;
        if(type.equalsIgnoreCase("internal")){
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            file = new File(classLoader.getResource(FILENAME).getFile());
        }

        if(!file.exists()){
            String errorMessage = "Sorry file not available";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }


        String mimeType = URLConnection.guessContentTypeFromName(file.getName());

        if(mimeType == null){
            System.out.println("mimetype is not detectable");
            mimeType = "application/octet-stream";
        }

        System.out.println("mimetype: " + mimeType);

        response.setContentType(mimeType);

        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

        response.setContentLength((int) file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

}

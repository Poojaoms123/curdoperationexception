package com.example.project.Service.ServiceImpl;

import com.itextpdf.html2pdf.HtmlConverter;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;

@Service
public class PdfService {
    private final TemplateEngine templateEngine;
    public PdfService(TemplateEngine templateEngine) { this.templateEngine = templateEngine; }

    public InputStream createPdf() throws IOException {

        Context context=new Context();
        context.setVariable("name","demo");
        String process = templateEngine.process("EmailTemplate", context);

        File file = File.createTempFile("demo",".pdf");
        HtmlConverter.convertToPdf(process,new FileOutputStream(file));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream inputStream = new FileInputStream(file);
        FileInputStream fileInputStream = new FileInputStream(file);
        return inputStream;

    }
}

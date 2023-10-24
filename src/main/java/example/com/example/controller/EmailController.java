package example.com.example.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import example.com.example.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/pdfpassword")
    public void exportStringAsPasswordPdfIText(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=exported-data.pdf");

        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);

        // Set a password for the PDF document should not be kept as null
        String userPassword = "user123"; // Set your desired user password
        String ownerPassword = "owner456"; // Set your desired owner password
        writer.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);

        document.open();

        // Your string data that you want to export as PDF
        String data = "This is the data you want to export as a password-protected PDF.";

        // Add the data to the PDF
        document.add(new Paragraph(data));
        document.add(new Paragraph("Generated on: " + new Date()));

        document.close();

        // Send the PDF as an email attachment
        byte[] pdfData = byteArrayOutputStream.toByteArray();
        emailService.sendPasswordProtectedPdf("ananthram119@gmail.com", userPassword, ownerPassword, pdfData);
    }
}

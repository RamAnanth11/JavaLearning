package example.com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordProtectedPdf(String recipient, String userPassword, String ownerPassword, byte[] pdfData) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set the email subject and body
            helper.setSubject("Secure PDF Attachment");
            helper.setText("Please find the PDF attachment in this email.");

            // Attach the PDF
            String encodedFilename = MimeUtility.encodeText("exported-data.pdf", "UTF-8", "Q");
            InputStreamSource pdfSource = new ByteArrayResource(pdfData);
            helper.addAttachment(encodedFilename, pdfSource);

            // Set the recipient's email address
            helper.setTo(recipient);

            // Send the email
            javaMailSender.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
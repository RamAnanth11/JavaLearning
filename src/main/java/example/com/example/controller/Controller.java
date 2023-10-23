package example.com.example.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import example.com.example.configuration.APIResponse;
import example.com.example.configuration.RecordLogs;
import example.com.example.service.ServiceClass;


@RestController
@RequestMapping("/myapp")
public class Controller {
	
	@Autowired
	ServiceClass service;

	@GetMapping("/export/pdf")
	public void exportStringAsPdf(HttpServletResponse response) throws IOException, DocumentException {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=exported-data.pdf");

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		// Your string data that you want to export as PDF
		String data = "This is the data you want to export as a PDF.";

		// Add the data to the PDF
		document.add(new Paragraph(data));

		document.close();
	}

	@GetMapping("/export/passwordpdf")
	public void exportStringAsPasswordPdf(HttpServletResponse response) throws IOException, DocumentException {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=exported-data.pdf");

		Document document = new Document();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);

//	    // Set a password for the PDF document
//	    String userPassword = "yourUserPassword"; // Set your desired user password
//	    String ownerPassword = "yourOwnerPassword"; // Set your desired owner password
//	    writer.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);

		// Set a password for the PDF document
		String userPassword = "yourUserPassword"; // Set your desired user password
		String ownerPassword = "yourOwnerPassword"; // Set your desired owner password
		writer.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(), PdfWriter.ALLOW_PRINTING,
				PdfWriter.ENCRYPTION_AES_128);

		document.open();

		// Your string data that you want to export as PDF
		String data = "This is the data you want to export as a PDF.";

		// Add the data to the PDF
		document.add(new Paragraph(data));

		document.close();

		// Now, you can write the password-protected PDF to the response
		response.setContentLength(byteArrayOutputStream.size());
		OutputStream os = response.getOutputStream();
		byteArrayOutputStream.writeTo(os);
		os.flush();
		os.close();
	}

	@GetMapping("/export/pdfbox")
	public void exportStringAsPdfbox(HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf"); // Set the content type to PDF
		response.setHeader("Content-Disposition", "attachment; filename=sample.pdf");

		String message = "This is a sample PDF document created using PDFBox.";

		PDDocument doc = new PDDocument();
		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDType1Font font = PDType1Font.HELVETICA_BOLD;

			PDPageContentStream contents = new PDPageContentStream(doc, page);
			contents.setFont(font, 12); // Adjust the font size
			contents.beginText();
			contents.newLineAtOffset(50, 700);
			contents.showText(message);
			contents.endText();
			contents.close();

			doc.save(response.getOutputStream()); // Write the PDF to the response's output stream
		} finally {
			doc.close();
		}
	}

	@GetMapping("/export/pdfboxpassword")
	public ResponseEntity<APIResponse> exportStringAsPdfboxPassword(HttpServletResponse response) throws IOException {
		RecordLogs.writeLogFile("GatewayLandingPageController /miniStatement_new_web api is calling ");
		APIResponse response2 = service.pdfBoxPassword(response);
		RecordLogs.writeLogFile("GatewayLandingPageController /miniStatement_new_web api is completed "+response2);
		return ResponseEntity.ok(response2);
	}

}

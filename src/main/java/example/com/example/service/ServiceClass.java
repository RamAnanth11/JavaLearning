package example.com.example.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import example.com.example.configuration.APIResponse;

@Service
public class ServiceClass {
	
	public APIResponse pdfBoxPassword(HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf"); // Set the content type to PDF
		response.setHeader("Content-Disposition", "attachment; filename=sample.pdf");
		APIResponse apiResponse = new APIResponse();
		String message = "This is a sample PDF document created using PDFBox.";
	
		PDDocument doc = new PDDocument();
		try {

			int keyLength = 256;

			AccessPermission ap = new AccessPermission();

			// disable printing, 
			ap.setCanPrint(false);
			//disable copying
			ap.setCanExtractContent(false);
			//Disable other things if needed...

			// Owner password (to open the file with all permissions) is "12345"
			// User password (to open the file but with restricted permissions, is empty here)
			
			String ownerPassword = "Ananth Ram";
			String userPassword = "Nagarajan";
			
			StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPassword, userPassword , ap);
			spp.setEncryptionKeyLength(keyLength);
			doc.protect(spp);
			PDPage page = new PDPage();
			doc.addPage(page);

//			PDType1Font font = PDType1Font.HELVETICA_BOLD;
			PDType1Font font = PDType1Font.COURIER;

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
		apiResponse.setStatus(false);
		apiResponse.setMessage("Hi This is message");
		apiResponse.setDescription("This is success message");
		apiResponse.setRecordId("");
		return apiResponse;
	}
}

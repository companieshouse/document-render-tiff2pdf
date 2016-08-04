package com.companieshouse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.codec.TiffImage;

@SuppressWarnings("serial")
public class TiffToPDF extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String xReqID = request.getHeader("X-Request-ID");
		if (xReqID == null) {
			byte[] rb = new byte[6];
			new Random().nextBytes(rb);
			xReqID = encode(rb);			
		}
		
		byte[] pdfBytes = tiffToPDF(request);
		
		OutputStream output = response.getOutputStream();
		output.write(pdfBytes);
		output.close();		
	}
	
	private static String encode(byte[] bytesToEncode) {
		Base64.Encoder encoder = Base64.getUrlEncoder();
		byte[] encoded = encoder.encode(bytesToEncode);
		return new String(encoded);		
	}
	
	private static byte[] tiffToPDF(HttpServletRequest request) {
		RandomAccessFileOrArray myTiffFile;
		try {		
			 myTiffFile = new RandomAccessFileOrArray(request.getInputStream());
			 int numberOfPages=TiffImage.getNumberOfPages(myTiffFile);
			 System.out.println("Number of Images in Tiff File: " + numberOfPages);
			 Document TifftoPDF=new Document();
			 ByteArrayOutputStream PDFOutput = new ByteArrayOutputStream();
			 PdfWriter.getInstance(TifftoPDF, PDFOutput);
			
			 TifftoPDF.open();
			 
			 for(int i=1;i<=numberOfPages;i++){
			     Image tempImage=TiffImage.getTiffImage(myTiffFile, i);
   		         tempImage.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
			 	 tempImage.setAbsolutePosition(0, 0);
     	         TifftoPDF.add(tempImage);
			     TifftoPDF.newPage(); 
   		     }
			 
			 if (request.getHeader("PDF-Subject") != null)
				 TifftoPDF.addSubject(request.getHeader("PDF-Subject"));
			 if (request.getHeader("PDF-Author") != null)
				 TifftoPDF.addAuthor(request.getHeader("PDF-Author"));
			 if (request.getHeader("PDF-Creator") != null)
				 TifftoPDF.addCreator(request.getHeader("PDF-Creator"));
			 if (request.getHeader("PDF-Title") != null)
				 TifftoPDF.addTitle(request.getHeader("PDF-Title"));

			 TifftoPDF.close();

			 System.out.println("Tiff to PDF Conversion in Java Completed" );
			    
			 return PDFOutput.toByteArray();
			    
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    return null;
	}
	
	

}

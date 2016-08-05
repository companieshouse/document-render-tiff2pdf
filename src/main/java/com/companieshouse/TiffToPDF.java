package com.companieshouse;

import java.io.ByteArrayOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.codec.TiffImage;

@SuppressWarnings("serial")
public class TiffToPDF extends HttpServlet {
	
	final static Logger log = Logger.getLogger(TiffToPDF.class);
	private static RandomAccessFileOrArray myTiffFile;

	public static RandomAccessFileOrArray getMyTiffFile() {
		return myTiffFile;
	}

	public void setMyTiffFile(RandomAccessFileOrArray myTiffFile) {
		TiffToPDF.myTiffFile = myTiffFile;
	}

	public byte[] tiffToPDF(HttpServletRequest request) {
		RandomAccessFileOrArray myTiffFile = TiffToPDF.myTiffFile;
		try {
			int numberOfPages = TiffImage.getNumberOfPages(myTiffFile);
			log.info("Number of Images in Tiff File: "
					+ numberOfPages);
			Document TifftoPDF = new Document();
			ByteArrayOutputStream PDFOutput = new ByteArrayOutputStream();
			PdfWriter.getInstance(TifftoPDF, PDFOutput);

			TifftoPDF.open();

			for (int i = 1; i <= numberOfPages; i++) {
				Image tempImage = TiffImage.getTiffImage(myTiffFile, i);
				tempImage.scaleToFit(PageSize.A4.getWidth(),
						PageSize.A4.getHeight());
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

			log.info("Tiff to PDF Conversion in Java Completed");

			return PDFOutput.toByteArray();

		} catch (DocumentException e) {
			log.error(e);
			e.printStackTrace();
		}

		return null;
	}

}

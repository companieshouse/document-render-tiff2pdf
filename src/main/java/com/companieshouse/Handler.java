package com.companieshouse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lowagie.text.pdf.RandomAccessFileOrArray;

@SuppressWarnings("serial")
public class Handler extends HttpServlet {
	
	final static Logger log = Logger.getLogger(Handler.class);
	
	private TiffToPDF converter;
	
	public TiffToPDF getConverter() {
		return converter;
	}

	public void setConverter(TiffToPDF converter) {
		this.converter = converter;
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String xReqID = request.getHeader("X-Request-ID");
		if (xReqID == null) {
			byte[] rb = new byte[6];
			new Random().nextBytes(rb);
			xReqID = encode(rb);
		}
		log.info("X Request ID: " + xReqID);
		
		if (converter == null) {
			setConverter(new TiffToPDF());
		}

		converter.setMyTiffFile(new RandomAccessFileOrArray(request.getInputStream()));
		byte[] pdfBytes = converter.tiffToPDF(request);
		int pages = converter.getPages();

		response.addHeader("PDF-Pages", String.valueOf(pages));
		OutputStream output = response.getOutputStream();
		output.write(pdfBytes);
		output.close();
	}

	public static String encode(byte[] bytesToEncode) {
		Base64.Encoder encoder = Base64.getUrlEncoder();
		byte[] encoded = encoder.encode(bytesToEncode);
		return new String(encoded);
	}

}

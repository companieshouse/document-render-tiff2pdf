package com.companieshouse;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

import com.lowagie.text.pdf.RandomAccessFileOrArray;

public class TiffToPDFTest extends Mockito {
	
	@Test 
	public void testSuccessfulConversionOfTiff() throws IOException {
		TiffToPDF testT2P = new TiffToPDF();
		ClassLoader classLoader = getClass().getClassLoader();
		RandomAccessFileOrArray myTiffFile = new RandomAccessFileOrArray(classLoader.getResourceAsStream("test.tif"));
		testT2P.setMyTiffFile(myTiffFile);
		HttpServletRequest mockReq = mock(HttpServletRequest.class);
		when(mockReq.getHeader("PDF-Subject")).thenReturn("Accounts");
		when(mockReq.getHeader("PDF-Author")).thenReturn("Matt Rout");
		when(mockReq.getHeader("PDF-Creator")).thenReturn("Companies House");
		when(mockReq.getHeader("PDF-Title")).thenReturn("Eric ate a banana");
		byte[] pdfBytes = testT2P.tiffToPDF(mockReq);
		assertNotNull(pdfBytes);
	}
}

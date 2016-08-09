package com.companieshouse;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;

public class DocumentConverterTest extends Mockito {
	
	ServletInputStream inputStream;
	ServletOutputStream outputStream;
	HttpServletRequest mockReq;
	HttpServletResponse response;
	TiffToPDF mockT2P;
	
	@Before
	public void setUp() {
		inputStream = mock(ServletInputStream.class);
		outputStream = mock(ServletOutputStream.class);
		mockReq = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		mockT2P = mock(TiffToPDF.class);
	}
	
	@Test
	public void testMethodEncodeReturnsBase64URLEncodedString() {
		byte[] rb = new byte[6];
		new Random().nextBytes(rb);
		String id1 = DocumentConverter.encode(rb);
		assertEquals(id1.length(), 8);
		String id2 = DocumentConverter.encode(rb);
		assertEquals(id1, id2);		
	}
	
	DocumentConverter testHandler = new DocumentConverter();
	
	@Test
	public void testHandler() throws IOException, ServletException {
		byte[] b = "test".getBytes();
		testHandler.setConverter(mockT2P);
		when(mockReq.getInputStream()).thenReturn(inputStream);
		when(response.getOutputStream()).thenReturn(outputStream);
		when(mockT2P.tiffToPDF(mockReq)).thenReturn(b);
		testHandler.doPost(mockReq, response);
		assertNotNull(response.getOutputStream());
	} 

}

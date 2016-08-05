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
import org.junit.Test;

public class HandlerTest extends Mockito {
	
	@Test
	public void testMethodEncodeReturnsBase64URLEncodedString() {
		byte[] rb = new byte[6];
		new Random().nextBytes(rb);
		String id1 = Handler.encode(rb);
		assertEquals(id1.length(), 8);
		String id2 = Handler.encode(rb);
		assertEquals(id1, id2);		
	}
	
	Handler testHandler = new Handler();
	
	@Test
	public void testHandler() throws IOException, ServletException {
		ServletInputStream inputStream = mock(ServletInputStream.class);
		ServletOutputStream outputStream = mock(ServletOutputStream.class);
		HttpServletRequest mockReq = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		TiffToPDF mockT2P = mock(TiffToPDF.class);
		byte[] b = "test".getBytes();
		testHandler.setConverter(mockT2P);
		when(mockReq.getInputStream()).thenReturn(inputStream);
		when(response.getOutputStream()).thenReturn(outputStream);
		when(mockT2P.tiffToPDF(mockReq)).thenReturn(b);
		testHandler.doPost(mockReq, response);
		assertNotNull(response.getOutputStream());
	} 

}

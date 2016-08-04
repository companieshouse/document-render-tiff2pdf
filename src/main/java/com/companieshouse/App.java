package com.companieshouse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class App 
{
    public static void main( String[] args ) throws Exception {
        Server server = new Server(9090);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(TiffToPDF.class, "/convert");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}

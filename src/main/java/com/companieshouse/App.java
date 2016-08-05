package com.companieshouse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class App 
{
	final static Logger log = Logger.getLogger(App.class);
	
    public static void main( String[] args ) throws Exception {
    	log.trace("Starting service on port 9090.");
        Server server = new Server(9090);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(Handler.class, "/convert");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}

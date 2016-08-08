package com.companieshouse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class App 
{
	final static Logger log = Logger.getLogger(App.class);
	
    public static void main( String[] args ) throws Exception {
    	int bind = 9090;
    	String newBind = System.getenv("TIFF2PDF_SERVICE_LISTEN");
    	if (newBind != null) {
    		bind = Integer.parseInt(newBind);
    	}
    	log.info("Starting service on port " + bind);
        Server server = new Server(bind);
        
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(Handler.class, "/convert");
        handler.addServletWithMapping(Healthcheck.class, "/healtcheck");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}

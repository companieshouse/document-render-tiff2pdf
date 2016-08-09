package com.companieshouse;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class App 
{
	final static Logger log = Logger.getLogger(App.class);
	
    public static void main( String[] args ) throws Exception {
    	//Default port is 5000 so service runs on elastic beanstalk
    	int bind = 5000;
    	String newBind = System.getenv("TIFF2PDF_SERVICE_PORT");
    	if (newBind != null) {
    		bind = Integer.parseInt(newBind);
    	}
    	log.info("Starting service on port " + bind);
    	QueuedThreadPool threadPool = new QueuedThreadPool(1000, 100);
        Server server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory());
        connector.setPort(bind);
        
        server.addConnector(connector);
        
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(DocumentConverter.class, "/convert");
        handler.addServletWithMapping(Healthcheck.class, "/healthcheck");
        server.setHandler(handler);
        server.start();
        server.join();
    }
}

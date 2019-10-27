package com.revolut.banking;


import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;



public class BankingStarter {
	
	static Logger log = Logger.getLogger(BankingStarter.class.getName());
	
	public static void main(String... args) {
		 String webPort = System.getenv("PORT");
	        if (webPort == null || webPort.isEmpty()) {
	            webPort = "8080";
	        }	      

	        final Server server = new Server(Integer.valueOf(webPort));
	        final WebAppContext root = new WebAppContext();

	        root.setContextPath("/");
	        root.setParentLoaderPriority(true);
	        root.setWar("target/banking-0.0.1.war");
	        
	        server.setHandler(root);
	        
	        try {
	        
	        server.start();
	        log.info("server started with URI:"+server.getURI());
	        server.join();
	        }catch (Exception e) {
	        	e.printStackTrace();
			}
	}

}

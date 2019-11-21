package com.revolut.banking;

import com.revolut.banking.config.H2DatabaseFactory;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.IOException;
import java.util.Properties;


/**
 * Banking starter is the starter class of the application.
 * It has embedded server, when started deploys the war on the server and starts the server.
 * initializes the database also
 */
public class BankingStarter {
	
	static Logger log = Logger.getLogger(BankingStarter.class.getName());
	private static Properties properties=new Properties();
	
	public static void main(String... args) {
		try {
			properties.load(BankingStarter.class.getResourceAsStream("/application.properties"));
			log.info("Properties file loaded");
		} catch (IOException e) {
			log.error("Exception occurred while loading properties",e);
		}
		log.info("Initilization of application");
		H2DatabaseFactory.populateData();
		log.info("Database initialized");

		startApplication();
	}

	private static void startApplication(){
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}

		final Server server = new Server(Integer.valueOf(webPort));
		final WebAppContext root = new WebAppContext();

		root.setContextPath("/");
		root.setParentLoaderPriority(true);
		root.setWar("target/banking-"+properties.getProperty("version")+".war");

		server.setHandler(root);
		try {
			server.start();
			log.info("server started with URI:"+server.getURI());
			server.join();
		}catch (Exception e) {
			log.error("Exception occurred while starting the server",e);
		}
	}

}

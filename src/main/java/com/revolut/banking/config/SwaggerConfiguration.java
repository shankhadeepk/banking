package com.revolut.banking.config;

import io.swagger.jaxrs.config.BeanConfig;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Swagger configuration for the project
 */
public class SwaggerConfiguration extends HttpServlet{
	
	    static Logger log = Logger.getLogger(SwaggerConfiguration.class.getName());
	   public void init(ServletConfig config) throws ServletException {
		   log.info("Swagger configured succesfully");
	       super.init(config);
	       BeanConfig beanConfig = new BeanConfig();
	       beanConfig.setTitle("Banking Service");
	       beanConfig.setVersion("1.0");
	       beanConfig.setSchemes(new String[]{"http"});
	       beanConfig.setHost("localhost:8080");
	       beanConfig.setBasePath("/api");
	       beanConfig.setResourcePackage("com.revolut.banking");
	       beanConfig.setScan(true);
	       beanConfig.setDescription("");
	   }
	}
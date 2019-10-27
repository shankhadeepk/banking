package com.revolut.banking.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.revolut.banking.config.SwaggerConfiguration;
import com.revolut.banking.exceptions.AccountNotFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Path("/rest")
@Api(value="/rest",description = "Service for banking")
public class AppResource {
	
	static Logger log = Logger.getLogger(AppResource.class.getName());
	
	@Path("/health")
	@GET
	@ApiResponses(value= {
	@ApiResponse(code=200,message="Successful")
	})
	public String check() throws AccountNotFoundException {
		log.info("in the controller");
		if(true)
			throw new AccountNotFoundException();
		else
			return "success";
	}

}

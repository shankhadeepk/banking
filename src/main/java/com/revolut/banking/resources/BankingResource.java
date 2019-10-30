package com.revolut.banking.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.revolut.banking.config.SwaggerConfiguration;
import com.revolut.banking.exceptions.AccountNotFoundException;
import com.revolut.banking.model.BankAccount;
import com.revolut.banking.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Path("/account")
@Api(value="/account",description = "Service for banking")
public class BankingResource {
	
	static Logger log = Logger.getLogger(BankingResource.class.getName());
	
	private final AccountService accountsService=new AccountService();
	
	@Path("/health")
	@GET
	@ApiResponses(value= {
	@ApiResponse(code=200,message="Successful")
	})
	public String check() throws AccountNotFoundException {
		log.info("in the controller");
		return "success";
	}
	
	@Path("/")
	@POST
	@ApiResponses(value= {
	@ApiResponse(code=200,message="Account created successfully")
	})
	public String createAccount(BankAccount bAccount){
		log.info("in the controller");
		//Check if the account with the details already exists.
		accountsService.validateAccount(bAccount);
		return null;
	}

}

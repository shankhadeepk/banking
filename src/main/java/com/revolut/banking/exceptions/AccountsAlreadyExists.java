package com.revolut.banking.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.revolut.banking.model.BankingError;

@Provider
public class AccountsAlreadyExists extends Exception implements ExceptionMapper<AccountsAlreadyExists> {
	
	static Logger log = Logger.getLogger(AccountsAlreadyExists.class.getName());
	
	public AccountsAlreadyExists() {
		super("Account is already present");
	}

	@Override
	public Response toResponse(AccountsAlreadyExists exception) {
		log.error(exception);
		BankingError error=new BankingError(exception.getMessage(),Response.Status.NOT_FOUND.name());
		return Response.status(Response.Status.NOT_FOUND).entity(error).type(MediaType.APPLICATION_JSON).build();
	}

}

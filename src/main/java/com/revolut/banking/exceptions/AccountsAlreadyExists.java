package com.revolut.banking.exceptions;

import com.google.gson.Gson;
import com.revolut.banking.model.BankingError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * If the account already exists while creation, AccountsAlreadyExists exception is thrown and appropriate message is reverted back
 */
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
		Gson gson=new Gson();
		return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(error)).type(MediaType.APPLICATION_JSON).build();
	}

}

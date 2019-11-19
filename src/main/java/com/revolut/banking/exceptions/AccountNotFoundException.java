package com.revolut.banking.exceptions;

import com.revolut.banking.model.BankingError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * If the account is not present, AccountNotFoundException exception is thrown and appropriate message is reverted back
 */
@Provider
public class AccountNotFoundException extends Exception
				implements ExceptionMapper<AccountNotFoundException>{
	
	static Logger log = Logger.getLogger(AccountNotFoundException.class.getName());

	public AccountNotFoundException() {
		super("The account is not present");
	}
	
	public AccountNotFoundException(String message) {
		super(message);
	}
	@Override
	public Response toResponse(AccountNotFoundException exception) {
		log.error(exception);
		BankingError error=new BankingError(exception.getMessage(),Response.Status.NOT_FOUND.name());
		return Response.status(Response.Status.NOT_FOUND).entity(error).type(MediaType.APPLICATION_JSON).build();
	}

}

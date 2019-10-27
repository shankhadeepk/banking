package com.revolut.banking.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccountNotFoundException extends Exception
				implements ExceptionMapper<AccountNotFoundException>{

	public AccountNotFoundException() {
		super("The account is not present");
	}
	
	public AccountNotFoundException(String message) {
		super(message);
	}
	@Override
	public Response toResponse(AccountNotFoundException exception) {		
		return Response.status(404).entity(exception.getMessage()).type("application/plain").build();
	}

}

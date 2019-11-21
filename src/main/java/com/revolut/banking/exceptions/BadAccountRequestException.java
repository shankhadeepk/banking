package com.revolut.banking.exceptions;

import com.google.gson.Gson;
import com.revolut.banking.model.BankingError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Bad account Request, if the request is not proper, the exception is mapped to this exception
 */
@Provider
public class BadAccountRequestException extends Exception implements ExceptionMapper<BadAccountRequestException> {
	
	static Logger log = Logger.getLogger(BadAccountRequestException.class.getName());
	
	public BadAccountRequestException() {
		super("The Request is not proper ");
	}

	@Override
	public Response toResponse(BadAccountRequestException exception) {
		log.error(exception);
		BankingError error=new BankingError(exception.getMessage(),Response.Status.BAD_REQUEST.name());
		Gson gson=new Gson();
		return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).type(MediaType.APPLICATION_JSON).build();
	}

}

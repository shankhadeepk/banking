package com.revolut.banking.exceptions;

import com.revolut.banking.model.BankingError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * Generic exception handler for other exceptions
 *
 */
@Provider
public class GeneralBankingException extends Exception
				implements ExceptionMapper<GeneralBankingException>{
	
	static Logger log = Logger.getLogger(GeneralBankingException.class.getName());

	public GeneralBankingException(){
		super("General Banking Exception");
	}

	public GeneralBankingException(String message) {
		super(message);
	}

	@Override
	public Response toResponse(GeneralBankingException exception) {
		log.error("Exception occurred: "+exception);
		BankingError error=new BankingError(exception.getMessage(),Response.Status.INTERNAL_SERVER_ERROR.name());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).type(MediaType.APPLICATION_JSON).build();
	}

}
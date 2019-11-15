package com.revolut.banking.exceptions;

import com.revolut.banking.model.BankingError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InterruptedExcMapper implements ExceptionMapper<InterruptedException>{
	
	static Logger log = Logger.getLogger(InterruptedExcMapper.class.getName());


	@Override
	public Response toResponse(InterruptedException exception) {
		log.error(exception);
		BankingError error=new BankingError(exception.getMessage(),Response.Status.INTERNAL_SERVER_ERROR.name());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).type(MediaType.APPLICATION_JSON).build();
	}

}

package com.revolut.banking.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InterruptedExcMapper extends Exception 
		implements ExceptionMapper<InterruptedException>{
	
	InterruptedExcMapper(){
		super("exception occurred due to adding data to queue");
	}

	@Override
	public Response toResponse(InterruptedException exception) {
		return Response.status(500).entity(exception.getMessage()).type("application/plain").build();
	}

}

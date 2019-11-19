package com.revolut.banking.exceptions;

import com.revolut.banking.model.BankingError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * All other exceptions are mapped to this exception mapper
 */
@Provider
public class AllExceptionMapper extends Throwable implements ExceptionMapper<Throwable>{

	static Logger log = Logger.getLogger(AllExceptionMapper.class.getName());

	@Override
	public Response toResponse(Throwable exception) {
		log.error("Something worst happened",exception);
		BankingError error=new BankingError(exception.getMessage(),Response.Status.INTERNAL_SERVER_ERROR.name());

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
		                .entity(error).type(MediaType.APPLICATION_JSON).build();

	}

}

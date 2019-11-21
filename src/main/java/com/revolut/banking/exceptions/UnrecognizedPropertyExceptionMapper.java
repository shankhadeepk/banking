package com.revolut.banking.exceptions;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.gson.Gson;
import com.revolut.banking.model.BankingError;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException>
{

    @Override
    public Response toResponse(UnrecognizedPropertyException exception)
    {
        BankingError error=new BankingError("'" + exception.getPropertyName() + "' is an unrecognized field.",Response.Status.BAD_REQUEST.name());
        Gson gson=new Gson();
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(gson.toJson(error))
                .type( MediaType.APPLICATION_JSON)
                .build();
    }

}

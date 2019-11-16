package com.revolut.banking.exceptions;

import com.revolut.banking.model.BankingError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class BalanceNotEnoughException extends Exception
        implements ExceptionMapper<AccountNotFoundException> {

    static Logger log = Logger.getLogger(AccountNotFoundException.class.getName());

    public BalanceNotEnoughException() {
        super("The account balance is not enough for withdrawl");
    }

    public BalanceNotEnoughException(String message) {
        super(message);
    }
    @Override
    public Response toResponse(AccountNotFoundException exception) {
        log.error(exception);
        BankingError error=new BankingError(exception.getMessage(),Response.Status.NOT_FOUND.name());
        return Response.status(Response.Status.NOT_FOUND).entity(error).type(MediaType.APPLICATION_JSON).build();
    }

}

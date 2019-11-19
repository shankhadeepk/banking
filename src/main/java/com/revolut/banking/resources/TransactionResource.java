package com.revolut.banking.resources;

import com.google.gson.Gson;
import com.revolut.banking.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/transaction")
@Api(value = "/transaction", description = "Transaction controller")
public class TransactionResource {

    static Logger log = Logger.getLogger(TransactionResource.class.getName());

    private final TransactionService transactionService;

    public TransactionResource() throws SQLException {
        this.transactionService=new TransactionService();
    }

    @Path("/health")
    @GET
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successful") })
    public String check() {
        log.info("in the controller");
        return "success";
    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses(value= {
            @ApiResponse(code=200,message="All Transaction information returned successfully")
    })
    public Response getallTransactions() throws Exception{
        log.info("Show all Transactions");
        //Every transaction is saved in database
        Gson gson=new Gson();
        return Response.status(Response.Status.OK).entity(gson.toJson(transactionService.getAllTransactions())).build();
    }


}

package com.revolut.banking.resources;

import com.revolut.banking.model.BankAccount;
import com.revolut.banking.model.BankingTransactionBuilder;
import com.revolut.banking.model.BankingTransactionnResponse;
import com.revolut.banking.service.AccountService;
import com.revolut.banking.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/")
@Api(value = "/", description = "Service for banking")
public class BankingResource {

	static Logger log = Logger.getLogger(BankingResource.class.getName());

	private AccountService accountsService;
	private TransactionService transactionService;

	public BankingResource() throws SQLException {
		accountsService = new AccountService();
		transactionService = new TransactionService();
	}

	@Path("/health")
	@GET
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful") })
	public String check() {
		log.info("in the controller");
		return "success";
	}

	@Path("/account")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
	@ApiResponse(code=200,message="Account created successfully")
	})
	public Response createAccount(BankAccount bAccount) throws Exception{
		log.info("Create Account");
		BankingTransactionnResponse bankingTransactionnResponse
							=new BankingTransactionBuilder()
							.setTransactionId()
							.setFromAccHolderName(bAccount.getBankAccHolderName())
							.setTypeOfTransaction("CREATE_ACCOUNT")
							.build();
		transactionService.createTransaction();
		//Check if the account with the details already exists.
		accountsService.validateAccount(bAccount);
		accountsService.createAccount(bAccount);
		
		return Response.status(Response.Status.CREATED).entity(bankingTransactionnResponse).build();
	}
}

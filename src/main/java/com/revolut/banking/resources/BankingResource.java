package com.revolut.banking.resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.h2.mvstore.tx.Transaction;

import com.revolut.banking.config.SwaggerConfiguration;
import com.revolut.banking.exceptions.AccountNotFoundException;
import com.revolut.banking.exceptions.AccountsAlreadyExists;
import com.revolut.banking.exceptions.BadAccountRequestException;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;
import com.revolut.banking.model.BankingTransactionBuilder;
import com.revolut.banking.model.BankingTransactionnResponse;
import com.revolut.banking.service.AccountService;
import com.revolut.banking.service.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

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
	public Response createAccount(BankAccount bAccount) throws SQLException, AccountsAlreadyExists, BadAccountRequestException, GeneralBankingException{
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

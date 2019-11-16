package com.revolut.banking.resources;

import com.revolut.banking.model.BankAccType;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/")
@Api(value = "/", description = "Service for banking")
public class BankingResource {

	static Logger log = Logger.getLogger(BankingResource.class.getName());

	private final AccountService accountsService;
	private final TransactionService transactionService;

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

	@Path("/account/{accId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Account information returned successfully")
	})
	public Response getAccount(@PathParam("accId") long bAccountId) throws Exception{
		log.info("Delete Account");
		BankingTransactionnResponse bankingTransactionnResponse
				=new BankingTransactionBuilder()
				.setTransactionId()
				.setFromAccount(bAccountId)
				.setTypeOfTransaction("GET_ACCOUNT")
				.build();
		//Every transaction is saved in database
		bankingTransactionnResponse=transactionService.createTransaction(bankingTransactionnResponse);
		Optional<List<BankAccount>> bankAccounts=Optional.of(accountsService.getAccounts(bAccountId));
		BankAccount bankAccount=bankAccounts.get().get(0);
		return Response.status(Response.Status.OK).entity(bankAccount).build();
	}

	@Path("/account")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
	@ApiResponse(code=201,message="Account created successfully")
	})
	public Response createAccount(BankAccount bAccount) throws Exception{
		log.info("Create Account");
		BankingTransactionnResponse bankingTransactionnResponse
							=new BankingTransactionBuilder()
							.setTransactionId()
							.setFromAccHolderName(bAccount.getBankAccHolderName())
							.setTypeOfTransaction("CREATE_ACCOUNT")
							.build();
		//Every transaction is saved in database
		bankingTransactionnResponse=transactionService.createTransaction(bankingTransactionnResponse);
		//Check if the account with the details already exists.
		BankAccType typeAcc=bAccount.getAccountType();
		accountsService.validateAccount(bAccount);
		accountsService.createAccount(bAccount);
		BankAccount bankAccount=accountsService.getAccounts(bAccount.getSSID()).stream().
								filter(bAcc -> bAcc.getAccountType().equals(typeAcc)).collect(Collectors.toList()).get(0);
		bankingTransactionnResponse.setFromAccount(bankAccount.getBankAccId());
		
		return Response.status(Response.Status.CREATED).entity(bankingTransactionnResponse).build();
	}

	@Path("/account/{accId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Account deleted successfully")
	})
	public Response deleteAccount(@PathParam("accId") String bAccountId) throws Exception{
		log.info("Delete Account");
		BankingTransactionnResponse bankingTransactionnResponse
				=new BankingTransactionBuilder()
				.setTransactionId()
				.setFromAccount(Long.parseLong(bAccountId))
				.setTypeOfTransaction("DELETE_ACCOUNT")
				.build();
		//Every transaction is saved in database
		bankingTransactionnResponse=transactionService.createTransaction(bankingTransactionnResponse);
		accountsService.deleteAccount(bAccountId);
		bankingTransactionnResponse.setFromAccount(Long.parseLong(bAccountId));

		return Response.status(Response.Status.OK).entity(bankingTransactionnResponse).build();
	}

	@Path("/account/{accId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Account updated successfully")
	})
	public Response updateBalance(@PathParam("accId") String bAccountId,BigDecimal balance) throws Exception{
		log.info("Update Account with Balance:"+balance);

		BankingTransactionnResponse bankingTransactionnResponse
				=new BankingTransactionBuilder()
				.setTransactionId()
				.setFromAccount(Long.parseLong(bAccountId))
				.setTypeOfTransaction("UPDATE_BALANCE")
				.build();
		//Every transaction is saved in database
		bankingTransactionnResponse=transactionService.createTransaction(bankingTransactionnResponse);
		accountsService.updateAccount(bAccountId, balance);
		bankingTransactionnResponse.setFromAccount(Long.parseLong(bAccountId));

		return Response.status(Response.Status.OK).entity(bankingTransactionnResponse).build();
	}

	@Path("/account/from/{fromAccount}/to/{toAccount}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
			@ApiResponse(code=201,message="Account created successfully")
	})
	public Response transferFund(long fromAccount,long toAccount,BigDecimal transferredAmount) throws Exception{
		log.info("Create Account");
		BankingTransactionnResponse bankingTransactionnResponse
				=new BankingTransactionBuilder()
				.setTransactionId()
				.setFromAccount(fromAccount)
				.setToAccount(toAccount)
				.setTypeOfTransaction("TRANSFER_FUND")
				.build();
		//Every transaction is saved in database
		bankingTransactionnResponse=transactionService.createTransaction(bankingTransactionnResponse);
		//Check if the account with the details already exists.
		accountsService.validateAccount(bAccount);
		accountsService.createAccount(bAccount);
		BankAccount bankAccount=accountsService.getAccounts(bAccount.getSSID()).stream().
				filter(bAcc -> bAcc.getAccountType().equals(typeAcc)).collect(Collectors.toList()).get(0);
		bankingTransactionnResponse.setFromAccount(bankAccount.getBankAccId());

		return Response.status(Response.Status.CREATED).entity(bankingTransactionnResponse).build();
	}
}

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

/**
 *
 * Banking Rest Controller
 *
 * Following actions are implemented:
 * 1.	Health of the service
 * 2. 	Get information of account. GET (/account/{accountId})
 * 3.	Create a new account POST (/account)
 * 4.	Deleted Account DELETE (/account/{accountId})
 * 5. 	Update Balance PUT (/account/{accountId})
 * 6. 	Transfer Fund POST (/account/from/{fromAccount}/to/{toAccount})
 *
 */
@Path("/")
@Api(value = "/", description = "Service for banking")
public class BankingResource {

	static Logger log = Logger.getLogger(BankingResource.class.getName());

	private final AccountService accountsService;
	private final TransactionService transactionService;

	public BankingResource() throws SQLException {
		this.accountsService = new AccountService();
		this.transactionService = new TransactionService();
	}

	@Path("/health")
	@GET
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful") })
	public String check() {
		log.info("in the controller");
		return "success";
	}

	/**
	 * Get Account information from Account Id
	 *
	 * @param bAccountId
	 * @return
	 * @throws Exception
	 */
	@Path("/account/{accId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Account information returned successfully")
	})
	public Response getAccount(@PathParam("accId") long bAccountId) throws Exception{
		log.info("Get Account");
		BankingTransactionnResponse bankingTransactionnResponse
				=new BankingTransactionBuilder()
				.setTransactionId()
				.setFromAccount(bAccountId)
				.setTypeOfTransaction("GET_ACCOUNT")
				.setStatus("IP")// In progress
				.build();
		//Every transaction is saved in database
		bankingTransactionnResponse=transactionService.createTransaction(bankingTransactionnResponse);
		Optional<List<BankAccount>> bankAccounts=Optional.of(accountsService.getAccounts(bAccountId));
		BankAccount bankAccount=bankAccounts.get().get(0);
		bankingTransactionnResponse.setStatus("P");
		return Response.status(Response.Status.OK).entity(bankAccount).build();
	}

	/**
	 * Create a New Account
	 *
	 * @param bAccount
	 * @return
	 * @throws Exception
	 */
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
							.setStatus("IP")
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
		bankingTransactionnResponse.setStatus("P");
		
		return Response.status(Response.Status.CREATED).entity(bankingTransactionnResponse).build();
	}

	/**
	 *
	 * Delete Account with Account Id
	 *
	 * @param bAccountId
	 * @return
	 * @throws Exception
	 */
	@Path("/account/{accId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Account deleted successfully")
	})
	public Response deleteAccount(@PathParam("accId") Long bAccountId) throws Exception{
		log.info("Delete Account");
		BankingTransactionnResponse bankingTransactionnResponse
				=new BankingTransactionBuilder()
				.setTransactionId()
				.setFromAccount(bAccountId)
				.setTypeOfTransaction("DELETE_ACCOUNT")
				.setStatus("IP")
				.build();
		//Every transaction is saved in database
		bankingTransactionnResponse=transactionService.createTransaction(bankingTransactionnResponse);
		accountsService.deleteAccount(bAccountId);
		bankingTransactionnResponse.setFromAccount(bAccountId);
		bankingTransactionnResponse.setStatus("P");

		return Response.status(Response.Status.OK).entity(bankingTransactionnResponse).build();
	}

	/**
	 *
	 * Update Account details
	 *
	 * @param bAccountId
	 * @param balance
	 * @return
	 * @throws Exception
	 */
	@Path("/account/{accId}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Account updated successfully")
	})
	public Response updateBalance(@PathParam("accId") Long bAccountId,BigDecimal balance) throws Exception{
		log.info("Update Account with Balance:"+balance);

		BankingTransactionnResponse bankingTransactionnResponse
				=new BankingTransactionBuilder()
				.setTransactionId()
				.setFromAccount(bAccountId)
				.setTypeOfTransaction("UPDATE_BALANCE")
				.setStatus("IP")
				.build();
		//Every transaction is saved in database
		bankingTransactionnResponse=transactionService.createTransaction(bankingTransactionnResponse);
		accountsService.updateAccount(bAccountId, balance);
		bankingTransactionnResponse.setFromAccount(bAccountId);
		bankingTransactionnResponse.setStatus("P");

		return Response.status(Response.Status.OK).entity(bankingTransactionnResponse).build();
	}

	/**
	 *
	 * Transfer Fund from Account with Id to Account with Id
	 *
	 *
	 * @param fromAccount
	 * @param toAccount
	 * @param transferredAmount
	 * @return
	 * @throws Exception
	 */
	@Path("/account/from/{fromAccount}/to/{toAccount}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(value= {
			@ApiResponse(code=200,message="Fund transferred successfully")
	})
	public Response transferFund(@PathParam("fromAccount") long fromAccount,@PathParam("toAccount") long toAccount,BigDecimal transferredAmount) throws Exception{
		log.info("Create Account");
		BankingTransactionnResponse bankingTransactionnResponse
				=new BankingTransactionBuilder()
				.setTransactionId()
				.setFromAccount(fromAccount)
				.setToAccount(toAccount)
				.setTypeOfTransaction("TRANSFER_FUND")
				.setStatus("IP")
				.build();
		//Every transaction is saved in database
		bankingTransactionnResponse=transactionService.createTransaction(bankingTransactionnResponse);
		//Check if the account with the details already exists.
		accountsService.fundTransfer(fromAccount,toAccount,transferredAmount);
		BankAccount frmBankAccount=accountsService.getAccounts(fromAccount).get(0);
		bankingTransactionnResponse.setFromAccount(frmBankAccount.getBankAccId());
		BankAccount toBankAccount=accountsService.getAccounts(toAccount).get(0);
		bankingTransactionnResponse.setFromAccount(toBankAccount.getBankAccId());
		bankingTransactionnResponse.setStatus("P");

		transactionService.updateTransaction(bankingTransactionnResponse);

		return Response.status(Response.Status.OK).entity(bankingTransactionnResponse).build();
	}
}

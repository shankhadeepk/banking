package com.revolut.banking.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.revolut.banking.config.DatabaseInitialization;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankingTransactionBuilder;
import com.revolut.banking.model.BankingTransactionnResponse;

public class BankingTransactionDaoImplTest {
	
	static Logger log = Logger.getLogger(BankingTransactionDaoImplTest.class.getName());
	private final static DatabaseInitialization databaseInitialization=new DatabaseInitialization();

	@Test
	public void testSaveTransaction() {
		BankingTransactionBuilder builder=null;
		BankingTransactionDao daoImpl=null;	
		try {
			daoImpl=new BankingTransactionDaoImpl();
			builder=new BankingTransactionBuilder();
			assertTrue(daoImpl.saveTransaction(builder.setTransactionId().setFromAccHolderName("Shankhadeep").setTypeOfTransaction("CREATE").build()));
		} catch (GeneralBankingException e) {
			log.error("Error while creating new transaction",e);
		} catch (SQLException e) {
			log.error("Error while creating new transaction",e);
		}
	}

	@Test
	public void testGetTransactions() {
		BankingTransactionDao daoImpl=null;
		BankingTransactionBuilder builder=null;
		BankingTransactionnResponse transaction=null;
		try {
			daoImpl=new BankingTransactionDaoImpl();
			builder=new BankingTransactionBuilder();
			transaction=builder.setTransactionId().setFromAccHolderName("Shankhadeep").setTypeOfTransaction("CREATE").build();
			daoImpl.saveTransaction(transaction);
			assertNotNull(daoImpl.getTransactions(transaction.getTransactionId()));
		} catch (SQLException e) {
			log.error("Error while get transaction",e);
		} catch (GeneralBankingException e) {
			log.error("Error while get transaction",e);
		}
	}

}

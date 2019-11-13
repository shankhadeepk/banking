package com.revolut.banking.dao;

import com.revolut.banking.config.H2Factory;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankingTransactionBuilder;
import com.revolut.banking.model.BankingTransactionnResponse;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BankingTransactionDaoImplTest {
	
	static Logger log = Logger.getLogger(BankingTransactionDaoImplTest.class.getName());

	@Before
	public void setUp(){
		H2Factory.populateData();
	}

	@After
	public void destroy(){
		H2Factory.closeConnection();
	}

	@Test
	public void testSaveTransaction() {
		BankingTransactionBuilder builder=null;
		BankingTransactionDao daoImpl=null;	
		try {
			daoImpl=new BankingTransactionDaoImpl();
			builder=new BankingTransactionBuilder();
			assertNotNull(daoImpl.saveTransaction(builder.setTransactionId().setFromAccHolderName("Shankhadeep").setTypeOfTransaction("CREATE").build()));
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

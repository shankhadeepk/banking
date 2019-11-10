package com.revolut.banking.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revolut.banking.config.DatabaseInitialization;
import com.revolut.banking.config.DatabaseInitializationTest;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;

public class BankingDaoImplTest {
	
	static Logger log = Logger.getLogger(BankingDaoImplTest.class.getName());
	
	private static final String insert="INSERT INTO BANKACCOUNT(SSID,BANKACCHOLDERNAME,BALANCE,EMAILID,CONTACT,ACCOUNTTYPE) VALUES(?,?,?,?,?,?)";
	private static final String delete="DELETE FROM BANKACCOUNT WHERE SSID = ?";
	private final static DatabaseInitialization databaseInitialization=new DatabaseInitialization();
	

	@Test
	public void testGetAccounts() {
		BankingAccountDao bankingDao=null;
		List<BankAccount> bankAccounts=null;
		try {
			BankAccount bankAccount =new BankAccount("Shankhadeep", new BigDecimal(2000.00), "GBP", "shankha@gmail.com" , "M87887", "+917878787789");
			bankAccount.setStrAccountType("CURR");
			
			bankingDao=new BankingAccountDaoImpl();			
			bankingDao.createNewAccount(bankAccount);
			
			bankAccount = new BankAccount("Shankhadeep", new BigDecimal(2000.00), "GBP", "shankha@gmail.com" , "M87887", "+917878787789");
			bankAccount.setStrAccountType("SAV");
			
			bankingDao.createNewAccount(bankAccount);
			
			bankAccounts=bankingDao.getAccounts("M87887");
			
			assertTrue(bankAccounts.size() == 2);
			
			bankingDao.deleteBankAccountsAsPerSSID("M87887");
			
		} catch (SQLException e) {
			log.error("error while doing database operation ",e);
		} catch (GeneralBankingException e) {
			log.error("error while doing database operation ",e);
		}finally{
			try {
				databaseInitialization.closeConnection();
			} catch (SQLException e) {
				log.error("error on closing connections",e);
			}
		}
		
	}

}

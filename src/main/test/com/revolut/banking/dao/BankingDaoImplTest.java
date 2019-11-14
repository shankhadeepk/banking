package com.revolut.banking.dao;

import com.revolut.banking.config.H2DatabaseFactory;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class BankingDaoImplTest {
	
	static Logger log = Logger.getLogger(BankingDaoImplTest.class.getName());
	
	private static final String insert="INSERT INTO BANKACCOUNT(SSID,BANKACCHOLDERNAME,BALANCE,EMAILID,CONTACT,ACCOUNTTYPE) VALUES(?,?,?,?,?,?)";
	private static final String delete="DELETE FROM BANKACCOUNT WHERE SSID = ?";


	@Before
	public void setUp(){
		H2DatabaseFactory.populateData();
	}


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
			
		}  catch (GeneralBankingException e) {
			log.error("error while doing database operation ",e);
		}
	}


}

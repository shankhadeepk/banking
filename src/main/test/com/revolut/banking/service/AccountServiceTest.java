package com.revolut.banking.service;

import com.revolut.banking.config.H2DatabaseFactory;
import com.revolut.banking.dao.BankingAccountDao;
import com.revolut.banking.dao.BankingAccountDaoImpl;
import com.revolut.banking.exceptions.AccountNotFoundException;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class AccountServiceTest {

    private AccountService accountService;

    @Before
    public void setUp(){

        try {
            this.accountService=new AccountService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        H2DatabaseFactory.populateData();
    }

    @Test
    public void createAccount() {
        BankAccount bankAccount = null;
        try {
            bankAccount = new BankAccount("Shankhadeep", new BigDecimal(2000.00), "GBP", "shankha@gmail.com" , "M87887", "+917878787789");
            bankAccount.setStrAccountType("CURR");
            assertNotNull(accountService.createAccount(bankAccount));
        } catch (GeneralBankingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAccount() {
        BankAccount bankAccount = null;
        try {
            bankAccount = new BankAccount("Shankhadeep", new BigDecimal(2000.00), "GBP", "shankha@gmail.com" , "M87887", "+917878787789");
            bankAccount.setStrAccountType("CURR");
            assertNotNull(accountService.createAccount(bankAccount));

            assertTrue(accountService.deleteAccount(1L));
        } catch (GeneralBankingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = AccountNotFoundException.class)
    public void deleteNonExistingAccount() throws Exception {
        accountService.deleteAccount(1L);
    }
    @Test
    public void updateAccount() {
        BankAccount bankAccount = null;
        try {
            bankAccount = new BankAccount("Shankhadeep", new BigDecimal(2000.00), "GBP", "shankha@gmail.com" , "M87887", "+917878787789");
            bankAccount.setStrAccountType("CURR");
            assertNotNull(accountService.createAccount(bankAccount));

            assertTrue(accountService.updateAccount(1L,new BigDecimal(230.0)));
        } catch (GeneralBankingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fundTransfer() {
        BankAccount bankAccount = null;
        BankingAccountDao bankingDao=null;
        try {
            bankAccount = new BankAccount("Shankhadeep", new BigDecimal(2000.00), "GBP", "shankha@gmail.com" , "M87887", "+917878787789");
            bankAccount.setStrAccountType("CURR");
            assertNotNull(accountService.createAccount(bankAccount));

            bankAccount = new BankAccount("Shankhadeep", new BigDecimal(1200.00), "GBP", "shankha@gmail.com" , "M87885", "+917878787722");
            bankAccount.setStrAccountType("CURR");
            assertNotNull(accountService.createAccount(bankAccount));

            assertTrue(accountService.fundTransfer(1,2,new BigDecimal(230.0)));

            bankingDao=new BankingAccountDaoImpl();
            List<BankAccount> accounts=bankingDao.getAccounts(2);

            if(accounts !=null)
            assertTrue(accounts.get(0).getBalance().compareTo(new BigDecimal(1430.00))==0);
        } catch (GeneralBankingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
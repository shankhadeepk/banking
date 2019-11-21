package com.revolut.banking.service;

import com.revolut.banking.config.H2DatabaseFactory;
import com.revolut.banking.dao.BankingAccountDao;
import com.revolut.banking.dao.BankingAccountDaoImpl;
import com.revolut.banking.exceptions.AccountNotFoundException;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;
import com.revolut.banking.model.BankingTransactionnResponse;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.annotation.Priority;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.DEFAULT)
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
            BankAccount account=accountService.createAccount(bankAccount);
            assertNotNull(account);
            assertTrue(accountService.deleteAccount(account.getBankAccId()));
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
            BankAccount account=accountService.createAccount(bankAccount);
            assertNotNull(account);

            assertTrue(accountService.updateAccount(account.getBankAccId(),new BigDecimal(230.0)));
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
            BankAccount fromAccount=accountService.createAccount(bankAccount);
            assertNotNull(fromAccount);

            bankAccount = new BankAccount("Shankhadeep", new BigDecimal(1200.00), "GBP", "shankha@gmail.com" , "M87885", "+917878787722");
            bankAccount.setStrAccountType("CURR");
            BankAccount toAccount=accountService.createAccount(bankAccount);
            assertNotNull(toAccount);

            assertTrue(accountService.fundTransfer(fromAccount.getBankAccId(),toAccount.getBankAccId(),new BigDecimal(230.0)));

            bankingDao=new BankingAccountDaoImpl();
            List<BankAccount> accounts=bankingDao.getAccounts(toAccount.getBankAccId());

            if(accounts !=null)
            assertTrue(accounts.get(0).getBalance().compareTo(new BigDecimal(1430.00))==0);
        } catch (GeneralBankingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
package com.revolut.banking.service;

import com.revolut.banking.config.H2DatabaseFactory;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankingTransactionBuilder;
import com.revolut.banking.model.BankingTransactionnResponse;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    private TransactionService transactionService;

    @Before
    public void setUp(){

        try {
            this.transactionService=new TransactionService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        H2DatabaseFactory.populateData();
    }

    @Test
    public void createNullTransaction() {
        try {
            assertNull(transactionService.createTransaction(null));
        } catch (GeneralBankingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void createTransaction() {
        BankingTransactionnResponse bankingTransactionnResponse
                =new BankingTransactionBuilder()
                .setTransactionId()
                .setFromAccount(1001)
                .setTypeOfTransaction("UPDATE_BALANCE")
                .build();
        try {
            assertNotNull(transactionService.createTransaction(bankingTransactionnResponse));
        } catch (GeneralBankingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllTransactions() {

        try {
            BankingTransactionnResponse bankingTransactionnResponse
                    =new BankingTransactionBuilder()
                    .setTransactionId()
                    .setFromAccount(1001)
                    .setTypeOfTransaction("UPDATE_BALANCE")
                    .setStatus("IP")
                    .build();
            transactionService.createTransaction(bankingTransactionnResponse);
            bankingTransactionnResponse
                    =new BankingTransactionBuilder()
                    .setTransactionId()
                    .setFromAccount(1002)
                    .setTypeOfTransaction("CREATE_ACCOUNT")
                    .setStatus("IP")
                    .build();
            transactionService.createTransaction(bankingTransactionnResponse);
            transactionService.getAllTransactions().ifPresent(list -> assertTrue(list.size()>0));
        } catch (GeneralBankingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTransaction() {
        try {
            BankingTransactionnResponse bankingTransactionnResponse
                    =new BankingTransactionBuilder()
                    .setTransactionId()
                    .setFromAccount(1001)
                    .setTypeOfTransaction("UPDATE_BALANCE")
                    .setStatus("IP")
                    .build();
            transactionService.createTransaction(bankingTransactionnResponse);
            bankingTransactionnResponse.setStatus("P");
            assertNotNull(transactionService.updateTransaction(bankingTransactionnResponse));

        } catch (GeneralBankingException e) {
            e.printStackTrace();
        }
    }
}
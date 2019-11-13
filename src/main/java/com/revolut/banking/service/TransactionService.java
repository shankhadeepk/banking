package com.revolut.banking.service;

import com.revolut.banking.dao.BankingTransactionDao;
import com.revolut.banking.dao.BankingTransactionDaoImpl;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankingTransactionnResponse;

import java.sql.SQLException;

public class TransactionService {

	private final BankingTransactionDao bankingTransactionDao;

	public TransactionService() throws SQLException {
		bankingTransactionDao = new BankingTransactionDaoImpl();
	}

	public synchronized BankingTransactionnResponse createTransaction(BankingTransactionnResponse transaction) throws GeneralBankingException {
		BankingTransactionnResponse response=bankingTransactionDao.saveTransaction(transaction);
		return response;
	}

}

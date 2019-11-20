package com.revolut.banking.service;

import com.revolut.banking.dao.BankingTransactionDao;
import com.revolut.banking.dao.BankingTransactionDaoImpl;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankingTransactionnResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TransactionService {

	private final BankingTransactionDao bankingTransactionDao;

	public TransactionService() throws SQLException {
		bankingTransactionDao = new BankingTransactionDaoImpl();
	}

	public synchronized BankingTransactionnResponse createTransaction(BankingTransactionnResponse transaction) throws GeneralBankingException {
		return  bankingTransactionDao.saveTransaction(transaction);
	}

	public synchronized Optional<List<BankingTransactionnResponse>> getAllTransactions() throws GeneralBankingException {
		Optional<List<BankingTransactionnResponse>> bankTransactions = Optional.of(bankingTransactionDao.getTransactions(null));
		return bankTransactions;
	}

	public synchronized BankingTransactionnResponse updateTransaction(BankingTransactionnResponse transaction) throws GeneralBankingException {
		return bankingTransactionDao.updateTransaction(transaction);
	}
}

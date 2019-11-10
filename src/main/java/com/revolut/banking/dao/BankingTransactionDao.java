package com.revolut.banking.dao;

import java.util.List;

import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankingTransactionnResponse;

public interface BankingTransactionDao {

	public boolean saveTransaction(BankingTransactionnResponse transaction) throws GeneralBankingException;
	public List<BankingTransactionnResponse> getTransactions(String transactionId) throws GeneralBankingException;
}

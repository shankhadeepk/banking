package com.revolut.banking.dao;

import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankingTransactionnResponse;

import java.util.List;

public interface BankingTransactionDao {

	public boolean saveTransaction(BankingTransactionnResponse transaction) throws GeneralBankingException;
	public List<BankingTransactionnResponse> getTransactions(String transactionId) throws GeneralBankingException;
}

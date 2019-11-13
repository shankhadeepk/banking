package com.revolut.banking.service;

import com.revolut.banking.dao.BankingAccountDao;
import com.revolut.banking.dao.BankingAccountDaoImpl;
import com.revolut.banking.exceptions.AccountsAlreadyExists;
import com.revolut.banking.exceptions.BadAccountRequestException;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;

import java.sql.SQLException;

public class AccountService {

	private final BankingAccountDao bankingDao;

	public AccountService() throws SQLException {
		bankingDao = new BankingAccountDaoImpl();
	}

	public synchronized BankAccount createAccount(BankAccount account) throws GeneralBankingException {
		bankingDao.createNewAccount(account);
		return account;
	}

	public synchronized boolean deleteAccount(String accountId) throws GeneralBankingException {
		bankingDao.deleteBankAccountsAsPerAccountId(accountId);
		return true;
	}

	public synchronized boolean validateAccount(BankAccount bankAccount) throws AccountsAlreadyExists, BadAccountRequestException, GeneralBankingException {
		if(bankAccount.getSSID().isEmpty() || bankAccount.getEmailId().isEmpty() || bankAccount.getBankAccHolderName().isEmpty()) {
			throw new BadAccountRequestException();
		}
		if (bankAccount.getSSID() != null) {
			if(bankingDao.getAccounts(bankAccount.getSSID()).size()==2) {
				throw new AccountsAlreadyExists();
			}
		}
		return true;
	}
}

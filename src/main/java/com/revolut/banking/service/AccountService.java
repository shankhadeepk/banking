package com.revolut.banking.service;

import java.sql.SQLException;

import com.revolut.banking.dao.BankingDao;
import com.revolut.banking.dao.BankingDaoImpl;
import com.revolut.banking.exceptions.AccountsAlreadyExists;
import com.revolut.banking.exceptions.BadAccountRequestException;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccType;
import com.revolut.banking.model.BankAccount;
import com.revolut.banking.model.BankAccountStatus;

public class AccountService {

	private final BankingDao bankingDao;

	public AccountService() throws SQLException {
		bankingDao = new BankingDaoImpl();
	}

	public synchronized BankAccount createAccount(BankAccount account) throws GeneralBankingException {
		bankingDao.createNewAccount(account);
		return account;
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

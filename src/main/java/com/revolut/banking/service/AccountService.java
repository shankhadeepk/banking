package com.revolut.banking.service;

import com.revolut.banking.dao.BankingAccountDao;
import com.revolut.banking.dao.BankingAccountDaoImpl;
import com.revolut.banking.exceptions.AccountsAlreadyExists;
import com.revolut.banking.exceptions.BadAccountRequestException;
import com.revolut.banking.exceptions.BalanceNotEnoughException;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AccountService {

	private final BankingAccountDao bankingDao;

	public AccountService() throws SQLException {
		bankingDao = new BankingAccountDaoImpl();
	}

	public synchronized BankAccount createAccount(BankAccount account) throws Exception {
		bankingDao.createNewAccount(account);
		return account;
	}

	public synchronized boolean deleteAccount(String accountId) throws Exception {
		bankingDao.deleteBankAccountsAsPerAccountId(accountId);
		return true;
	}

	public synchronized boolean updateAccount(String accountId, BigDecimal addToBalance) throws Exception {
		Optional<List<BankAccount>> bankAccounts=Optional.of(bankingDao.getAccounts(Long.parseLong(accountId)));
		BankAccount bankAccount=bankAccounts.get().get(0);
		bankAccount.deposit(addToBalance);
		bankingDao.updateBankAccountsAsPerAccountId(accountId,bankAccount);
		return true;
	}

	@Transactional
	public synchronized boolean fundTransfer(long frmAccountId,long toAccountId, BigDecimal amount) throws Exception {
		Optional<List<BankAccount>> fromBanksAccounts=Optional.of(bankingDao.getAccounts(frmAccountId));
		BankAccount frmBankAccount=fromBanksAccounts.get().get(0);
		Optional<List<BankAccount>> toBanksAccounts=Optional.of(bankingDao.getAccounts(toAccountId));
		BankAccount toBankAccount=toBanksAccounts.get().get(0);
		frmBankAccount.withDraw(amount);
		toBankAccount.deposit(amount);
		bankingDao.updateBankAccountsAsPerAccountId(frmAccountId,frmBankAccount);
		return true;
	}

	public synchronized List<BankAccount> getAccounts(String SSID) throws Exception {
		return bankingDao.getAccounts(SSID);
	}

	public synchronized boolean validateAccount(BankAccount bankAccount) throws Exception {
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

	public synchronized List<BankAccount> getAccounts(long accountId) throws Exception {
		return bankingDao.getAccounts(accountId);
	}
}

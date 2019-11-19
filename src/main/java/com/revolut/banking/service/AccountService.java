package com.revolut.banking.service;

import com.revolut.banking.dao.BankingAccountDao;
import com.revolut.banking.dao.BankingAccountDaoImpl;
import com.revolut.banking.exceptions.AccountNotFoundException;
import com.revolut.banking.exceptions.AccountsAlreadyExists;
import com.revolut.banking.exceptions.BadAccountRequestException;
import com.revolut.banking.model.BankAccount;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountService {

	static Logger log = Logger.getLogger(AccountService.class.getName());

	private final BankingAccountDao bankingDao;

	public AccountService() throws SQLException {
		this.bankingDao = new BankingAccountDaoImpl();
	}

	public synchronized BankAccount createAccount(BankAccount account) throws Exception {
		bankingDao.createNewAccount(account);
		return account;
	}

	public synchronized boolean deleteAccount(Long accountId) throws Exception {
		if(bankingDao.deleteBankAccountsAsPerAccountId(accountId)<=0){
			throw new AccountNotFoundException();
		}
		return true;
	}

	public synchronized boolean updateAccount(Long accountId, BigDecimal addToBalance) throws Exception {
		Optional<List<BankAccount>> bankAccounts=Optional.of(bankingDao.getAccounts(accountId));
		BankAccount bankAccount=bankAccounts.get().get(0);
		bankAccount.deposit(addToBalance);
		if(bankingDao.updateBankAccountsAsPerAccountId(bankAccount,null)<=0){
			throw new AccountNotFoundException();
		}
		return true;
	}


	public synchronized boolean fundTransfer(long frmAccountId,long toAccountId, BigDecimal amount) throws Exception {
		if(amount.compareTo(BigDecimal.ZERO) > 0) {
			Optional<List<BankAccount>> fromBanksAccounts = Optional.of(bankingDao.getAccounts(frmAccountId));
			BankAccount frmBankAccount = fromBanksAccounts.get().get(0);
			Optional<List<BankAccount>> toBanksAccounts = Optional.of(bankingDao.getAccounts(toAccountId));
			BankAccount toBankAccount = toBanksAccounts.get().get(0);
			frmBankAccount.withDraw(amount);
			toBankAccount.deposit(amount);
			if(bankingDao.updateBankAccountsAsPerAccountId(toBankAccount, frmBankAccount)<=0){
				throw new AccountNotFoundException();
			}
		}else {
			log.error("The amount to be added is less than or equal to 0, which is not allowed");
		}
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

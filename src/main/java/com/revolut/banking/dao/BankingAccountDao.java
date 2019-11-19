package com.revolut.banking.dao;

import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;

import java.util.List;

public interface BankingAccountDao {
	
	public List<BankAccount> getAccounts(String SSID) throws GeneralBankingException ;
	public boolean createNewAccount(BankAccount account) throws GeneralBankingException;
	public boolean deleteBankAccountsAsPerSSID(String SSID) throws GeneralBankingException;
	public int deleteBankAccountsAsPerAccountId(Long accountId) throws GeneralBankingException;
	public int updateBankAccountsAsPerAccountId(BankAccount frmBankAccount,BankAccount toBankAccount) throws GeneralBankingException;
	public List<BankAccount> getAccounts(long bankAccId) throws GeneralBankingException;
}

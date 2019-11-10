package com.revolut.banking.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;

public interface BankingAccountDao {
	
	public List<BankAccount> getAccounts(String SSID) throws GeneralBankingException ;
	public boolean createNewAccount(BankAccount account) throws GeneralBankingException;
	public boolean deleteBankAccountsAsPerSSID(String SSID) throws GeneralBankingException;

}

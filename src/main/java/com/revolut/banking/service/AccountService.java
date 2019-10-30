package com.revolut.banking.service;

import com.revolut.banking.dao.BankingDao;
import com.revolut.banking.dao.BankingDaoImpl;
import com.revolut.banking.model.BankAccType;
import com.revolut.banking.model.BankAccount;
import com.revolut.banking.model.BankAccountStatus;

public class AccountService {
	
	private final BankingDao bankingDao=new BankingDaoImpl();
	
	public BankAccount createAccount(BankAccount account) {
		
		
		return account;
		
	}
	
	public boolean validateAccount(BankAccount bankAccount) {
		
		bankAccount.getAccountType();
		
		return false;
	}
	
	public BankAccType getAccountType(String accountType) {
		
		if(accountType.toUpperCase().contains("CURR")) {
			return BankAccType.CURR;
		}else if(accountType.toUpperCase().contains("SAV")) {
			return BankAccType.SAV;
		}
		return null;		
	}
	
	public BankAccountStatus getAccountStatus(String status) {
		
		if(status.toUpperCase().contains("ACT"))return BankAccountStatus.ACTIVE;
		else if(status.toUpperCase().contains("DOR"))return BankAccountStatus.DORMANT;		
		return null;
	}
		
}

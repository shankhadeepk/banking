package com.revolut.banking.model;

public class BankTransaction {
	
	private String transactionId;
	private BankAccount account;
	public BankTransaction(String transactionId, BankAccount account) {
		super();
		this.transactionId = transactionId;
		this.account = account;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public BankAccount getAccount() {
		return account;
	}
}

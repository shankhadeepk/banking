package com.revolut.banking.model;

import com.revolut.banking.config.AppConstants;

import java.time.LocalDateTime;

public class BankingTransactionBuilder {
	
	private String transactionId;
	private String typeOfTransaction;
	private long fromAccount;
	private long toAccount;
	private String fromAccHolderName;
	private String toAccountHolderName;
	
	public BankingTransactionBuilder setTransactionId() {
		StringBuilder transactId=new StringBuilder();
		int suffix =(int)((Math.random() * 90000)+10000);
		transactId.append(LocalDateTime.now().format(AppConstants.dateFormatterTransact));
		transactId.append(suffix);
		this.transactionId=transactId.toString();
		return this;
	}
	public BankingTransactionBuilder setTypeOfTransaction(String typeOfTransaction) {
		this.typeOfTransaction=typeOfTransaction;
		return this;
	}
	
	public long getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(long fromAccount) {
		this.fromAccount = fromAccount;
	}
	public long getToAccount() {
		return toAccount;
	}
	public void setToAccount(long toAccount) {
		this.toAccount = toAccount;
	}
	public BankingTransactionBuilder setFromAccHolderName(String fromAccHolderName) {
		this.fromAccHolderName = fromAccHolderName;
		return this;
	}
	public BankingTransactionBuilder setToAccountHolderName(String toAccountHolderName) {
		this.toAccountHolderName = toAccountHolderName;
		return this;
	}
	public BankingTransactionnResponse build() {
		return new BankingTransactionnResponse(transactionId,typeOfTransaction,fromAccount,toAccount,fromAccHolderName,toAccountHolderName);
	}

}

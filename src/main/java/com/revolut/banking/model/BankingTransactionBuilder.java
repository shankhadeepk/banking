package com.revolut.banking.model;

import java.time.LocalDateTime;

import com.revolut.banking.config.AppConstants;

public class BankingTransactionBuilder {
	
	private String transactionId;
	private String typeOfTransaction;
	private String fromAccount;
	private String toAccount;
	private String fromAccHolderName;
	private String toAccountHolderName;
	private String dateOfCreation;
	
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
	public BankingTransactionBuilder setFromAccount(String fromAccount) {
		this.fromAccount=fromAccount;
		return this;
	}
	public BankingTransactionBuilder setToAccount(String toAccount) {
		this.toAccount = toAccount;
		return this;
	}
	public BankingTransactionBuilder setDateOfCreation() {
		this.dateOfCreation=LocalDateTime.now().format(AppConstants.dateFormatterTransact);		
		return this;
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
		return new BankingTransactionnResponse(transactionId,typeOfTransaction,fromAccount,toAccount,fromAccHolderName,toAccountHolderName,dateOfCreation);
	}

}

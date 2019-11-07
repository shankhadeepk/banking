package com.revolut.banking.model;

public class BankingTransactionnResponse {
	
	private String transactionId;
	private String typeOfTransaction;
	private String fromAccount;
	private String toAccount;
	private String fromAccHolderName;
	private String toAccountHolderName;
	private String dateOfCreation;			
	
	public BankingTransactionnResponse(String transactionId, String typeOfTransaction, String fromAccount,
			String toAccount, String fromAccHolderName, String toAccountHolderName, String dateOfCreation) {
		super();
		this.transactionId = transactionId;
		this.typeOfTransaction = typeOfTransaction;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.fromAccHolderName = fromAccHolderName;
		this.toAccountHolderName = toAccountHolderName;
		this.dateOfCreation = dateOfCreation;
	}
	public BankingTransactionnResponse(String transactionId, String typeOfTransaction, String fromAccHolderName, String dateOfCreation) {
		super();
		this.transactionId = transactionId;
		this.typeOfTransaction = typeOfTransaction;
		this.fromAccHolderName = fromAccHolderName;
		this.dateOfCreation = dateOfCreation;
	}	
	public BankingTransactionnResponse(String transactionId, String typeOfTransaction, String fromAccHolderName,String toAccountHolderName, String dateOfCreation) {
		super();
		this.transactionId = transactionId;
		this.typeOfTransaction = typeOfTransaction;
		this.fromAccHolderName = fromAccHolderName;
		this.toAccountHolderName = toAccountHolderName;
		this.dateOfCreation = dateOfCreation;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTypeOfTransaction() {
		return typeOfTransaction;
	}
	public void setTypeOfTransaction(String typeOfTransaction) {
		this.typeOfTransaction = typeOfTransaction;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public String getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	
	

}

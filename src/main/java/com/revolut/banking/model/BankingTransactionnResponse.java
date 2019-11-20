package com.revolut.banking.model;

public class BankingTransactionnResponse {
	
	private String transactionId;
	private String typeOfTransaction;
	private long fromAccount;
	private long toAccount;
	private String fromAccHolderName;
	private String toAccountHolderName;
	private String dateOfCreation;
	private String dateOfModification;
	private String status;
	
	public BankingTransactionnResponse(String transactionId, String typeOfTransaction, long fromAccount,
			long toAccount, String fromAccHolderName, String toAccountHolderName) {
		super();
		this.transactionId = transactionId;
		this.typeOfTransaction = typeOfTransaction;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.fromAccHolderName = fromAccHolderName;
		this.toAccountHolderName = toAccountHolderName;
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
	public String getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public String getFromAccHolderName() {
		return fromAccHolderName;
	}
	public String getToAccountHolderName() {
		return toAccountHolderName;
	}
	public String getDateOfModification() {
		return dateOfModification;
	}
	public void setDateOfModification(String dateOfModification) {
		this.dateOfModification = dateOfModification;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}

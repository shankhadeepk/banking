package com.revolut.banking.model;

public enum BankAccountStatus {
	
	ACTIVE("A"),
	DORMANT("D");
	
	private String status;
	
	BankAccountStatus(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}

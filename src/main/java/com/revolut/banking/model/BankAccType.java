package com.revolut.banking.model;

public enum BankAccType {
	
	CURR("Current"),
	SAV("Savings");
	
	private String type;	
	
	BankAccType(String type) {
		this.type=type;
	}

	public String getType() {
		return type;
	}
}

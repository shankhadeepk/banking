package com.revolut.banking.model;

public class BankingError {
	
	private String errorMessage;
	private String respStatus;
	public BankingError(String errorMessage, String respStatus) {
		super();
		this.errorMessage = errorMessage;
		this.respStatus = respStatus;
	}

	@Override
	public String toString() {
		return "BankingError{" +
				"errorMessage='" + errorMessage + '\'' +
				", respStatus='" + respStatus + '\'' +
				'}';
	}
}

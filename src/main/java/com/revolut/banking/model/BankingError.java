package com.revolut.banking.model;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BankingError {
	
	private String errorMessage;
	private String respStatus;
	public BankingError(String errorMessage, String respStatus) {
		super();
		this.errorMessage = errorMessage;
		this.respStatus = respStatus;
	}
	
	
}

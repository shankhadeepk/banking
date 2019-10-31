package com.revolut.banking.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revolut.banking.config.DatabaseInitialization;

public class BankingDaoImplTest {
	
	private static final String insert="INSERT INTO BANKACCOUNT VALUES(?,?)";

	@Test
	public void testGetAccounts() {
		DatabaseInitialization databaseInitialization=new DatabaseInitialization();
		
	}

}

package com.revolut.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import com.revolut.banking.config.DatabaseInitialization;
import com.revolut.banking.model.BankAccount;

public class BankingDaoImpl extends BankingDao{
	
	private final Connection connection;
	private static final String GET_ACC="SELECT * FROM ANKACCOUNT WHERE BANKACCID = ?";
	
	public BankingDaoImpl() throws SQLException {		
		this.connection = DatabaseInitialization.getConnection();			
	}
	
	public synchronized Set<BankAccount> getAccounts(long bankAccount){
		PreparedStatement preparedStatement=null;
		ResultSet result=null;
		try {
			preparedStatement=this.connection.prepareStatement(GET_ACC);
			preparedStatement.setLong(1, bankAccount);
			result=preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Set<BankAccount>) result;		
	}

}

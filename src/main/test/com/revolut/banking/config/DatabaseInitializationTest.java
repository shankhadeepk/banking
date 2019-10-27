package com.revolut.banking.config;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revolut.banking.BankingStarter;

public class DatabaseInitializationTest {
	
	static Logger log = Logger.getLogger(DatabaseInitializationTest.class.getName());
	
	private final static DatabaseInitialization databaseInitialization=new DatabaseInitialization();
	private static Connection connection;
	private static final String createEmployee="CREATE TABLE EMP(EMPID INT PRIMARY KEY, EMPNAME VARCHAR(200))";
	private static final String insert="INSERT INTO EMP VALUES(?,?)";
	private static final String select="SELECT * FROM EMP";
	private static final String dropEmployee="DROP TABLE EMP";
	
	@BeforeClass
	public static void setUp() {
		PreparedStatement preparedStatement=null;
		try {
			connection=databaseInitialization.getConnection();
			preparedStatement = connection.prepareStatement(createEmployee);
			preparedStatement.execute();
		} catch (SQLException e) {
			log.error("error on creating table",e);			
		}
	}
	
	@AfterClass
	public static void destroy() {
		PreparedStatement preparedStatement=null;
		try {
			preparedStatement = connection.prepareStatement(dropEmployee);
			preparedStatement.execute();
		} catch (SQLException e) {
			log.error("error on dropping table",e);
		}
	}

	@Test
	public void checkDatabaseInitialized() {	
		PreparedStatement preparedStatement=null;
		
		try {		
			preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setInt(1, 100);
			preparedStatement.setNString(2, "EMP1");
			
			preparedStatement.execute();
			
			preparedStatement= connection.prepareStatement(select);
			preparedStatement.execute();
			
			
		} catch (SQLException e) {
			log.error("error while doing operations on table",e);
		}
	}

}

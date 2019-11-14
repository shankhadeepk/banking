package com.revolut.banking.config;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2FactoryTest {
	
	static Logger log = Logger.getLogger(H2FactoryTest.class.getName());

	private static final String createEmployee="CREATE TABLE EMP(EMPID INT PRIMARY KEY, EMPNAME VARCHAR(200))";
	private static final String insert="INSERT INTO EMP VALUES(?,?)";
	private static final String select="SELECT * FROM EMP";
	private static final String dropEmployee="DROP TABLE EMP";
	
	@Before
	public void setUp() {
		PreparedStatement preparedStatement=null;
		Connection connection=null;
		try {
			connection=H2DatabaseFactory.getConnection();
			preparedStatement = connection.prepareStatement(createEmployee);
			preparedStatement.execute();
		} catch (SQLException e) {
			log.error("error on creating table",e);			
		}finally {
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}
	}
	
	@After
	public void destroy() {
		PreparedStatement preparedStatement=null;
		Connection connection=null;
		try {
			connection=H2DatabaseFactory.getConnection();
			preparedStatement = connection.prepareStatement(dropEmployee);
			preparedStatement.execute();
		} catch (SQLException e) {
			log.error("error on dropping table",e);
		}finally {
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}
	}

	@Test
	public void checkDatabaseInitialized() {	
		PreparedStatement preparedStatement=null;
		Connection connection=null;
		try {
			connection=H2DatabaseFactory.getConnection();
			preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setInt(1, 100);
			preparedStatement.setNString(2, "EMP1");
			
			preparedStatement.execute();
			
			preparedStatement= connection.prepareStatement(select);
			preparedStatement.execute();
			
			
		} catch (SQLException e) {
			log.error("error while doing operations on table",e);
		}finally {
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}
	}

}

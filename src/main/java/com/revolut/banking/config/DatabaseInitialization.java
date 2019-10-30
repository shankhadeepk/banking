package com.revolut.banking.config;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;

public class DatabaseInitialization {
	private static JdbcConnectionPool connPool = null;
	DataSource dataSource = null;
	public static final String DB_URL = "jdbc:h2:mem:banking;DB_CLOSE_DELAY=-1";

	private static JdbcConnectionPool createConnectionPool() {		
		connPool = JdbcConnectionPool.create(DB_URL,"sa","sa");
		connPool.setMaxConnections(100);
		return connPool;
	}

	public static void closeConnection() throws SQLException {
		if (isConnectionOpen()) {
			connPool.dispose();
			connPool = null;
		}
	}
	
	public static Connection getConnection() throws SQLException {
		if(isConnectionOpen()) {
			return connPool.getConnection();
		}else {
			connPool= createConnectionPool();
			return connPool.getConnection();
		}
	}

	private static boolean isConnectionOpen() {
		return (connPool != null);
	}

}

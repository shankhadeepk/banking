package com.revolut.banking.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;

public class DatabaseInitialization {
	
	static Logger log = Logger.getLogger(DatabaseInitialization.class.getName());
	
	static {
		Connection conn = null;
		try {
			conn = DatabaseInitialization.getConnection();
			RunScript.execute(conn, new FileReader("src/main/resources/initialscripts.sql"));
		} catch (SQLException e) {			
			log.error("LoadInitialData - SQL Error while executing script",e);
		} catch (FileNotFoundException e) {
			log.error("LoadInitialData - Error while finding initialscripts.sql",e);
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("Connection cannot be closed after loading data ",e);
				}
			}
		}
		
	}
	
	private static JdbcConnectionPool connPool = null;
	DataSource dataSource = null;
	public static final String DB_URL = "jdbc:h2:~/banking";

	private static JdbcConnectionPool createConnectionPool() {		
		connPool = JdbcConnectionPool.create(DB_URL,"sa","");
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

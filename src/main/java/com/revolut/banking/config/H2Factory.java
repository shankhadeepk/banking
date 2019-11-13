package com.revolut.banking.config;

import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class H2Factory {
	
	static Logger log = Logger.getLogger(H2Factory.class.getName());

	private static JdbcConnectionPool connPool = null;

	public static void populateData(){
		Connection conn = null;
		try {
			log.info("Data base server started");
			conn = H2Factory.getConnection();
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
	private static JdbcConnectionPool createConnectionPool(){		
		connPool = JdbcConnectionPool.create(AppConstants.DB_URL,AppConstants.DB_USER,AppConstants.DB_PWD);
		log.info("Connection established");
		connPool.setMaxConnections(100);
		return connPool;
	}

	public static void closeConnection(){
		if (isConnectionOpen()) {
			connPool.dispose();
			connPool = null;
		}		
	}
	
	public static Connection getConnection() {
		try {
			if (isConnectionOpen()) {
				return connPool.getConnection();

			} else {
				connPool = createConnectionPool();
				return connPool.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	private static boolean isConnectionOpen() {
		return (connPool != null);
	}

}

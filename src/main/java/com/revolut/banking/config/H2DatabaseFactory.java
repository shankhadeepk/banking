package com.revolut.banking.config;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseFactory {

    static Logger log = Logger.getLogger(H2DatabaseFactory.class.getName());

    H2DatabaseFactory(){
        DbUtils.loadDriver(AppConstants.DB_DRIVER);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(AppConstants.DB_URL,AppConstants.DB_USER,AppConstants.DB_PWD);
    }

    public static void populateData(){
        Connection conn = null;
        try {
            log.info("Data base server started");
            conn = H2DatabaseFactory.getConnection();
            RunScript.execute(conn, new FileReader("src/main/resources/initialscripts.sql"));
        } catch (SQLException e) {
            log.error("LoadInitialData - SQL Error while executing script",e);
        } catch (FileNotFoundException e) {
            log.error("LoadInitialData - Error while finding initialscripts.sql",e);
        }finally {
           DbUtils.closeQuietly(conn);
        }
    }
}

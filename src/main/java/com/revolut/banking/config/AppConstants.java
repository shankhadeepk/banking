package com.revolut.banking.config;

import java.time.format.DateTimeFormatter;

/**
 * Application constants class
 *
 * All properties records are maintained here
 */
public class AppConstants {
	
	public static final String DATE_FORMAT="dd-MM-yyyy HH:mm:ss";
	public static final String DATE_FORMAT_TRANSACT="ddMMyyyyHHmmss";
	public static final DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern(DATE_FORMAT);
	public static final DateTimeFormatter dateFormatterTransact=DateTimeFormatter.ofPattern(DATE_FORMAT_TRANSACT);
	public static final String DB_URL = "jdbc:h2:mem:banking;DB_CLOSE_DELAY=-1";
	public static final String DB_USER = "sa";
	public static final String DB_PWD = "";
	public static final String DB_DRIVER="org.h2.Driver";

}

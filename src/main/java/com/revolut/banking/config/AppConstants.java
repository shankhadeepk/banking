package com.revolut.banking.config;

import java.time.format.DateTimeFormatter;

public class AppConstants {
	
	public static final String DATE_FORMAT="dd-MM-yyyy HH:mm:ss";
	public static final String DATE_FORMAT_TRANSACT="ddMMyyyyHHmmss";
	public static final DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern(DATE_FORMAT);
	public static final DateTimeFormatter dateFormatterTransact=DateTimeFormatter.ofPattern(DATE_FORMAT_TRANSACT);

}

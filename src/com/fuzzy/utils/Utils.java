package com.fuzzy.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
	
	static String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	public static void printError(String message, Exception e){
		System.err.println(String.format(timeStamp + " Error: %s", message));
		System.out.println(e.getMessage());
		e.printStackTrace(System.out);
	}
	
	public static void printWarning(String message, Exception e){
		System.err.println(String.format(timeStamp +" Warning: %s", message));
		System.out.println(e.getMessage());
		e.printStackTrace(System.out);
	}
	
	public static void printInfo(String message){
		System.err.println(String.format(timeStamp + " Info: %s", message));
	}
}

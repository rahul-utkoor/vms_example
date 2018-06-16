package com.aizant.vms.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class test {
public static void main(String[] args) throws ParseException {
	java.util.Date afterSixMonths=new SimpleDateFormat("yyyy-MM-dd").parse("2018-10-06");  
	System.out.println(afterSixMonths);
	Date dt=new Date(afterSixMonths.getTime());
	System.out.println(dt);
}


}

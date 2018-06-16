package com.aizant.vms.model;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

public class SixMonths{

    public static String getSixMonths(String xraydate){
      String dt = xraydate;
       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
       Date date = null;
       try {
           date = (Date)formatter.parse(dt);
       } catch (ParseException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }


       Calendar now = Calendar.getInstance();    
       now.setTime(date);



       System.out.println("Current date : " + now.get(Calendar.DATE)+ "-" +(now.get(Calendar.MONTH) + 1) + "-"
            + now.get(Calendar.YEAR));

       now.add(Calendar.MONTH, 6);

       System.out.println("date after 6 months : " +  now.get(Calendar.DATE)+"-" + (now.get(Calendar.MONTH) + 1) + "-"
            + now.get(Calendar.YEAR));
       String afterSixMonths = now.get(Calendar.YEAR)+"-" + (now.get(Calendar.MONTH) + 1) + "-"
               + now.get(Calendar.DATE);
	return afterSixMonths;
    
    }
}

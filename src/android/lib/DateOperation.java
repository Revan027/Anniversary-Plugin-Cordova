package com.revan.anniversaryplugin.lib;

import java.util.GregorianCalendar;	
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;

public class DateOperation {
		
	public DateOperation(){ 	
      } 

	public static String ConvertToString(Date date, String format){
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
      }

      public static String ConvertToString(GregorianCalendar date, String format){
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
      }

      public static Date ConvertToDate(String date, String format){
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date newDate = new Date();

            try{
                  newDate =  sdf.parse(date);
            } catch(Exception e) {                
                  System.err.println("Exception: " + e.getMessage());
            } 
            return newDate;
      }

      public static Date addDay(Date date, String format){
            Date newDate = new Date();
            GregorianCalendar calStr1 = new GregorianCalendar();//création d'un nouveau calendrier
            calStr1.setTime(date);//initialisation du calendrier avec la date du jour
            calStr1.add(GregorianCalendar.DATE, 1); 
           
            String dateIncre = DateOperation.ConvertToString(calStr1.getTime(), "dd-MM-yyyy HH:mm");
         
            try{
                  newDate = DateOperation.ConvertToDate(dateIncre,format);
            } catch(Exception e) {                
                  System.err.println("Exception: " + e.getMessage());
            } 
           return newDate;
      }

      public static Date addYear(Date date, String format){
            Date newDate = new Date();			
            GregorianCalendar calStr1 = new GregorianCalendar(); 
            calStr1.setTime(date); 
            calStr1.add(GregorianCalendar.YEAR, 1);
            
            String dateIncre = DateOperation.ConvertToString(calStr1.getTime(), format);	

		try{	
                  newDate = DateOperation.ConvertToDate(dateIncre,format);
		} catch(Exception e) {			
		    System.err.println("Exception: " + e.getMessage());
		} 
		return newDate;
      }
      
	public static Date creationRappel(Date date){
		Date dateNow = new Date();
		String dateRappelString = "";
		Date dateRappel = null;

		try{		
			GregorianCalendar gregCalend = new GregorianCalendar();
			gregCalend.setTime(date); 
			gregCalend.add(GregorianCalendar.DATE, -7); 
			
			dateRappelString = ConvertToString(gregCalend.getTime(), "dd-MM-yyyy HH:mm");	
	            dateRappel = ConvertToDate(dateRappelString, "dd-MM-yyyy HH:mm");// date rappel à 7 jours
			
			if (dateNow.compareTo(dateRappel) >= 0 ) {// si la date courante et > que la date anniv-7jours, on regarde avec la date anniv-2 jours			
				gregCalend.setTime(date); 
				gregCalend.add(GregorianCalendar.DATE, -2);
                        dateRappelString =  ConvertToString(gregCalend.getTime(), "dd-MM-yyyy HH:mm");
			      dateRappel = ConvertToDate(dateRappelString, "dd-MM-yyyy HH:mm");// date rappel à 2 jours		 		
			}
		
		} catch(Exception e) {			
		    System.err.println("Exception: " + e.getMessage());
		} 

		return dateRappel;
	}
}
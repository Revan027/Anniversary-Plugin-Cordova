package com.uniclau.alarmplugin.lib;

import java.util.GregorianCalendar;	
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateOperation {
		
	public DateOperation(){ 
	
    } 
	
	public String creationRappel(String dateAnniv){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		Date todaysDate = new Date();
		String dateRappelString="";
		
		try{	
				
			Date date = sdf.parse(dateAnniv);
				
			GregorianCalendar calStr1 = new GregorianCalendar();
			calStr1.setTime(date); 
			calStr1.add(GregorianCalendar.DATE, -7); 
			
			dateRappelString =  sdf.format(calStr1.getTime());	
			Date dateRappel = sdf.parse(dateRappelString);	//creation de la date rappel à 7 jours
			
			if (todaysDate.compareTo(dateRappel) >= 0 ) {	//si la date courante et > que la date anniv-7jours, on regarde avec la date anniv-2jours
				
				calStr1.setTime(date); 
				calStr1.add(GregorianCalendar.DATE, -2); 
				dateRappelString = sdf.format(calStr1.getTime());	
				dateRappel = sdf.parse(dateRappelString);	//creation de la date rappel à 2 jours
				 
				if (todaysDate.compareTo(dateRappel) >= 0) {	//si la date courante et > que la dte anniv-2jours
					
					return dateAnniv;	//nous sommes à  l'anniversaire
					
				}			
			}
		
		} catch(Exception e) {
			
		    System.err.println("Exception: " + e.getMessage());
		} 
		return dateRappelString;
	}
	
	
	public String incrementeDate(String date){
		
		String dateIncre ="";
		try{	
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");//reformatage complet
			Date datef = sdf.parse(date);
			
			GregorianCalendar calStr1 = new GregorianCalendar(); // Création d'un nouveau calendrier
			calStr1.setTime(datef); // Initialisation du calendrier avec la date du jour
			calStr1.add(GregorianCalendar.YEAR, 1); // On retranche 1 année
			
			dateIncre= sdf.format(calStr1.getTime());	
			
		} catch(Exception e) {
			
		    System.err.println("Exception: " + e.getMessage());
		} 
		return dateIncre;
	}
	
	
}
package com.uniclau.alarmplugin.alarme;

import com.uniclau.alarmplugin.lib.DateOperation;
import com.uniclau.alarmplugin.broadcast.*;

import org.apache.cordova.CordovaPlugin;
import android.content.Intent;
import android.content.Context;
import java.util.GregorianCalendar;	
import java.text.SimpleDateFormat;
import java.util.Date;

/********** permet la création d'un Intent avec ses paramètres **************/

public class IntentCreation {
	private String info[] =  new String[1];
    private String date[] =  new String[2];
	private int id_date;
	private Context context;
	
	public IntentCreation(String name, String date,int id_date, Context context){ 

		DateOperation dateo = new DateOperation();
		this.date[0] = dateo.creationRappel(date);	//la date servant pour l'alarme	
		this.date[1] = date;		//la date anniversaire
		this.info[0] = name;	
		this.id_date = id_date;
		this.context = context;
    } 
	
	public String create(){
		
		/***************** création du rappel  **********************/
				
		Intent intent = new Intent(this.context, AlarmReceiver.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("id_date",this.id_date);
		intent.putExtra("info",this.info);
		intent.putExtra("date",this.date);
		
		/***************** création de l'alarme  **********************/
		Alarme alrm = new Alarme(intent,this.context);				
		String message = alrm.createAlarm();	
		
		return message;
	}
	
	
	/***************** création d'alarme sans modification de la date du rappel **********************/
	public String simpleCreate(String dateRappel){
		
		this.date[0] = dateRappel;
		
		Intent intent = new Intent(this.context, AlarmReceiver.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("id_date",this.id_date);
		intent.putExtra("info",this.info);
		intent.putExtra("date",this.date);
		
		/***************** création de l'alarme  **********************/
		Alarme alrm = new Alarme(intent,this.context);				
		String message = alrm.createAlarm();	
		
		return message;
	}
	
	public void setNom(String nom){
		this.info[0] = nom;
	}

	public void setIdDate(int id){
		this.id_date = id;
	}

	/***************** retournes les dates au format sans heure pour la db  **********************/
	public String getDate(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");	
		
		try {	
		
			Date d =  sdf.parse(this.date[1]);
			
			return sdf.format(d);
			
		} catch(Exception e) {
			
			System.err.println("Exception: " + e.getMessage());
			return "Exception: " + e.getMessage()+"";
			
		} 
	}
	
	public String getDateRappel(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");	
		
		try {	
		
			Date d =  sdf.parse(this.date[0]);
			
			return sdf.format(d);
			
		} catch(Exception e) {
			
			System.err.println("Exception: " + e.getMessage());
			return "Exception: " + e.getMessage()+"";
			
		} 
	}
}
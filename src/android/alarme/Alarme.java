package com.revan.anniversaryplugin.alarme;

import com.revan.anniversaryplugin.broadcast.*;
import com.revan.anniversaryplugin.lib.DateOperation;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.app.AlarmManager;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.os.Build;
import java.util.GregorianCalendar;	
import android.os.Bundle;
import android.util.Log;
public class Alarme {
	
	private Context context;
      private Intent intent;
	private Date date;
	private int id;
	
	public Alarme(Intent intent,Context context){ 	
		this.context = context;
		this.intent = intent;
     } 
	
	public String Create()  { 		
            Bundle extras = this.intent.getExtras();

		try {		
                  this.date = DateOperation.ConvertToDate(extras.getString("date"),"dd-MM-yyyy HH:mm");	
  
			AlarmManager alarmMgr = (AlarmManager)(this.context.getSystemService(Context.ALARM_SERVICE));
                  PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,1,this.intent,PendingIntent.FLAG_NO_CREATE);
                  String tes = "non null";
                  
                  if(pendingIntent == null){   //si pas d'alarme existante                  
                        pendingIntent = PendingIntent.getBroadcast(this.context,1,this.intent,0);
                        tes = "null";

                        if(this.date.before(new Date())) this.date = DateOperation.AddDay(this.date);

                        if (Build.VERSION.SDK_INT >= 19) alarmMgr.setExact(AlarmManager.RTC_WAKEUP, this.date.getTime(), pendingIntent);
                        else alarmMgr.set(AlarmManager.RTC_WAKEUP, this.date.getTime(), pendingIntent);
                  }              
                  return  tes;   

		} catch(Exception e) {			
			System.err.println("Exception: " + e.getMessage());
			return "Exception: " + e.getMessage()+"";
		} 
      }
      
      public void Update()  { 		
            Bundle extras = this.intent.getExtras();

		try {		
                  this.date = DateOperation.ConvertToDate(extras.getString("date"),"dd-MM-yyyy HH:mm");	
  
			AlarmManager alarmMgr = (AlarmManager)(this.context.getSystemService(Context.ALARM_SERVICE));
                  PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,1,this.intent,PendingIntent.FLAG_UPDATE_CURRENT);
      
                  if(this.date.before(new Date())) this.date = DateOperation.AddDay(this.date);

                  if (Build.VERSION.SDK_INT >= 19) alarmMgr.setExact(AlarmManager.RTC_WAKEUP, this.date.getTime(), pendingIntent);
                  else alarmMgr.set(AlarmManager.RTC_WAKEUP, this.date.getTime(), pendingIntent);
                              

		} catch(Exception e) {			
			System.err.println("Exception: " + e.getMessage());
		} 
	}
      	
	public void deleteAlarm(String[] whereArgs) {

		for(int i =0; i< whereArgs.length;i++){//Annulation de chaque alarme par son id
			
			int id_alarm =  Integer.parseInt(whereArgs[i]);
			
			AlarmManager alarmMgr = (AlarmManager)(this.context.getSystemService(Context.ALARM_SERVICE));

			Intent intent = new Intent(this.context, AlarmReceiver.class);
			intent.putExtra("id_date",id_alarm);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,id_alarm,intent,0);

			alarmMgr.cancel(pendingIntent);
			//pendingIntent.cancel();
		}
	}
	
	public void deleteOneAlarm(int id) {

		/*AlarmManager alarmMgr = (AlarmManager)(this.context.getSystemService(Context.ALARM_SERVICE));

		Intent intent = new Intent(this.context, AlarmReceiver.class);
		intent.putExtra("id_date",id);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,id,intent,0);

		alarmMgr.cancel(pendingIntent);*/
		//pendingIntent.cancel();
		
	}
	
	/********** check si un pending intent existe **************/
	public  boolean checkedAlarme(int id){
		
		/*Intent intent = new Intent(this.context, AlarmReceiver.class);
		intent.putExtra("id_date",id);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,id,intent, PendingIntent.FLAG_NO_CREATE);
		
		if(pendingIntent==null){
			
			return false;
			
		}else*/ return true;	
	}
	
	public boolean isBissextile (int year){
		
		if((year%4==0) && (year%100!=0) || (year%400==0)){
			return true;			
		}else{
			return false;
		}
	}
}
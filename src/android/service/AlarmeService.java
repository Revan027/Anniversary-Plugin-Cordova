package com.revan.anniversaryplugin.service;

import com.revan.anniversaryplugin.broadcast.*;
import com.revan.anniversaryplugin.service.*;
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


public class AlarmeService {

	public String Create(Intent intent, Context context)  { 		
            Bundle extras = intent.getExtras();
            Date date = new Date();

		try {		
                  date = DateOperation.ConvertToDate(extras.getString("date"),"dd-MM-yyyy HH:mm");	
  
			AlarmManager alarmMgr = (AlarmManager)(context.getSystemService(Context.ALARM_SERVICE));
                  PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1,intent,PendingIntent.FLAG_NO_CREATE);
                  String tes = "non null";
                  
                  if(pendingIntent == null){   //si pas d'alarme existante                  
                        pendingIntent = PendingIntent.getBroadcast(context,1,intent,0);
                        tes = "null";

                        if(date.before(new Date())){} date = DateOperation.addDay(date,"dd-MM-yyyy HH:mm");                  

                        if (Build.VERSION.SDK_INT >= 19) alarmMgr.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);

                        else alarmMgr.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
                  }  
                              
                  return  tes;   
		} catch(Exception e) {			
			System.err.println("Exception: " + e.getMessage());
                  
			return "Exception: " + e.getMessage()+"";
		} 
      }
      
      public void Update(Intent intent, Context context)  { 		
            Bundle extras = intent.getExtras();
            Date date = new Date();

		try {		
                  date = DateOperation.ConvertToDate(extras.getString("date"),"dd-MM-yyyy HH:mm");	
  
			AlarmManager alarmMgr = (AlarmManager)(context.getSystemService(Context.ALARM_SERVICE));
                  PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
      
                  if(date.before(new Date())) date = DateOperation.addDay(date,"dd-MM-yyyy HH:mm");

                  if (Build.VERSION.SDK_INT >= 19) alarmMgr.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);

                  else alarmMgr.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);                           
		} catch(Exception e) {			
			System.err.println("Exception: " + e.getMessage());
		} 
	}
      	
	/*public void deleteAlarm(String[] whereArgs) {

		for(int i =0; i< whereArgs.length;i++){//Annulation de chaque alarme par son id
			
			int id_alarm =  Integer.parseInt(whereArgs[i]);
			
			AlarmManager alarmMgr = (AlarmManager)(this.context.getSystemService(Context.ALARM_SERVICE));

			Intent intent = new Intent(this.context, AlarmReceiver.class);
			intent.putExtra("id_date",id_alarm);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,id_alarm,intent,0);

			alarmMgr.cancel(pendingIntent);
			//pendingIntent.cancel();
		}
	}*/
	
	public void deleteOneAlarm(int id) {

		/*AlarmManager alarmMgr = (AlarmManager)(this.context.getSystemService(Context.ALARM_SERVICE));

		Intent intent = new Intent(this.context, AlarmReceiver.class);
		intent.putExtra("id_date",id);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,id,intent,0);

		alarmMgr.cancel(pendingIntent);*/
		//pendingIntent.cancel();
		
	}
	
	public boolean isBissextile (int year){
		
		if((year%4==0) && (year%100!=0) || (year%400==0)){
			return true;			
		}else{
			return false;
		}
	}
}
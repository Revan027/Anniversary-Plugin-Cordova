package com.uniclau.alarmplugin.alarme;

import com.uniclau.alarmplugin.broadcast.*;
import com.uniclau.alarmplugin.lib.*;
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

/********** Creation et suppression d'alarme **************/
public class Alarme {
	
	private Context context;
    private Intent intent;
	private Date date;
	private int id;
	
	public Alarme(Intent intent,Context context){ 
	
		this.context = context;
		this.intent = intent;
    } 
	
	public String createAlarm()  { 
		
		Bundle extras = this.intent.getExtras();
		this.id =  extras.getInt("id_date");
		
		String date[] = this.intent.getStringArrayExtra("date");

		try {	
		
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm"); 
			this.date = sdf.parse(date[0]);
				
			AlarmManager alarmMgr = (AlarmManager)(this.context.getSystemService(Context.ALARM_SERVICE));
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,this.id,this.intent, PendingIntent.FLAG_CANCEL_CURRENT);	// flag qui supprimera le pending intent qui portera le meme nom
		
			if (Build.VERSION.SDK_INT>=19) {
				
					alarmMgr.setExact(AlarmManager.RTC_WAKEUP, this.date.getTime(), pendingIntent);
				} else {
					
					alarmMgr.set(AlarmManager.RTC_WAKEUP, this.date.getTime(), pendingIntent);
			}
			
			return "Une alarme se lancera le " + date[0] +" : "+Integer.toString(this.id ) ;		
		} catch(Exception e) {
			
			System.err.println("Exception: " + e.getMessage());
			return "Exception: " + e.getMessage()+"";
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

		AlarmManager alarmMgr = (AlarmManager)(this.context.getSystemService(Context.ALARM_SERVICE));

		Intent intent = new Intent(this.context, AlarmReceiver.class);
		intent.putExtra("id_date",id);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,id,intent,0);

		alarmMgr.cancel(pendingIntent);
		//pendingIntent.cancel();
		
	}
	
	/********** check si un pending intent existe **************/
	public  boolean checkedAlarme(int id){
		
		Intent intent = new Intent(this.context, AlarmReceiver.class);
		intent.putExtra("id_date",id);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context,id,intent, PendingIntent.FLAG_NO_CREATE);
		
		if(pendingIntent==null){
			
			return false;
			
		}else return true;	
	}
	
	public boolean isBissextile (int year){
		
		if((year%4==0) && (year%100!=0) || (year%400==0)){
			return true;			
		}else{
			return false;
		}
	}
}
package com.uniclau.alarmplugin;

import com.revan.anniversaryplugin.db.*;
import com.revan.anniversaryplugin.lib.*;
import com.uniclau.alarmplugin.alarme.*;
import com.uniclau.alarmplugin.sms.*;
import com.uniclau.alarmplugin.lib.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import 	android.app.Service;
import java.util.ArrayList;
import java.util.GregorianCalendar;	
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.app.Service;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;  
import android.app.Notification;

public class RebootService extends Service {
 
    @Override  
    public IBinder onBind(Intent intent) { 
	
        return null;  		
    }  
	
    public void onDestroy() {  
	
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();   	 
    } 


    public void onCreate() { 
 	
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();  
    } 
 	

	public int onStartCommand(Intent intent, int flags,  int startId) {
		
		int idmain = 1;
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();  
		
		NotificationCreation nc = new NotificationCreation(this,"Memory-aid","En cours de redémarrage",idmain);
		this.startForeground(idmain,nc.startForegroundNotification());
		
		/*******get all id alarme*********/
		DBHelper dbHelper = new DBHelper(this);
		DBReglage dbReglage = new DBReglage(this);
		
		ArrayList<String> al = new ArrayList<String>();
		al = dbHelper.GetAll();
		
		Date n = new Date();
		int idAlarm = 0;
		
		/*******parcours db*********/
		for(int i = 2; i<al.size(); i=i+ 7){
					
			idAlarm = Integer.parseInt(al.get(i-2));
	
			String name = al.get(i-1);
			String date = al.get(i) ;
			
			String date_complete = date+ " " + dbReglage.getHeure();	
			
			try {
												
				DateOperation dateo = new DateOperation();
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");					
				Date aDate = sdf.parse(date_complete);
				
				if(aDate.before(n)) { //Si la date anniv est < à la date de boot du portable. anniv passée
					
					/*******Notification*********/
					String message = name+ " le "+ date;
					
					Intent intentp = new Intent();
					intentp.setPackage(this.getPackageName());
					intentp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);	// Intent qui lancera vers l'activité MainActivity						
					//context.startActivity(intent); //Pour démarrer l'activity, le programme principal, dès que l'alarme se déclenche
				   
					nc = new NotificationCreation(this,"C'était l'anniversaire de",message,idAlarm + idmain);
					nc.createNotification();

					/*******construit et incrémente la nouvelle alarme*********/									
					date_complete = dateo.incrementeDate(date_complete);

					IntentCreation intentC = new IntentCreation(name,date_complete,idAlarm,this);
					intentC.create();	
					
					/*******update DB avec les nouvelles dates*********/
					dbHelper.update(intentC.getDate(),idAlarm,intentC.getDateRappel());
					
				}else{
					
					IntentCreation intentC = new IntentCreation(name,date_complete,idAlarm,this);
					intentC.create();	
					
					/*******update DB avec les nouvelles dates*********/
					dbHelper.update(intentC.getDate(),idAlarm,intentC.getDateRappel());
				} 					
			}
			catch (Exception e) {
				
				System.err.println("Exception: " + e.getMessage());
				
				this.stopSelf(startId); 
				
				return Service.START_NOT_STICKY;
			}		
		}
		
		this.stopSelf(startId); 
		//super.onStartCommand(intent, flags, startId);		
		return Service.START_REDELIVER_INTENT;
	}
}
package com.uniclau.alarmplugin.broadcast;

import com.uniclau.alarmplugin.WakeUp;
import com.revan.anniversaryplugin.db.*;
import com.uniclau.alarmplugin.lib.*;
import com.revan.anniversaryplugin.lib.*;
import com.uniclau.alarmplugin.alarme.IntentCreation;
import com.uniclau.alarmplugin.sms.*;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.util.Log;
import android.os.Vibrator;
import android.R;
import android.net.Uri;
import android.os.Bundle;
import java.util.GregorianCalendar;	
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Build;
import android.app.AlarmManager;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/***************** reception d'une alarme **********************/
public class AlarmReceiver extends BroadcastReceiver {
	
    @Override
    public void onReceive(Context context, Intent intent) {
		try {
			Log.d("AlarmPlugin", "AlarmReceived"); 	//Envoyez un message de journal DEBUG et enregistrez l'exception.
			
			/*PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			pm.wakeUp(SystemClock.uptimeMillis());*/

			PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
			WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");	//permet de réveiller le téléphone, l'écran lors du'une alarme
			wakeLock.acquire();	// L’arrêt de la veille se fait par la méthode acquire, l’activation par la méthode release 
	 
			KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);	//permet de gérer le clavier
			KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
			keyguardLock.disableKeyguard();

			Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(3000);

			/**********Information de l'alarme recu**************/
			String info[] = intent.getStringArrayExtra("info");	  		 			
			String name = info[0];  
			
			Bundle extras = intent.getExtras();
			int id_date = extras.getInt("id_date");
			
			String date[] = intent.getStringArrayExtra("date");		
			
			DBHelper dbHelper = new DBHelper(context);	
	
			
			/**********test si l'alarme qui à eu lieu est égale à la date anniversaire du contact*********/	
			if(date[0].compareTo(date[1])==0){
				
				ArrayList<String> al = new ArrayList<String>();
				al = dbHelper.getEtatNum(id_date);		
				DBReglage dbReglage = new DBReglage(context);
				
				int etat = Integer.parseInt(al.get(1));
				
				if(etat!=1){
					
					/*******Notification sans sms*********/  
					NotificationCreation nc = new NotificationCreation(context,"C'est l'anniversaire de",name+", le " +date[1],id_date);
					nc.createNotification();
					
				}else{
					
					/******* Sms sent *********/
					SmsCreation sms = new SmsCreation(context,al,dbReglage.getTexte(),name,id_date);
					sms.createSms();					
				}
			
				/******* Lancement de l'activité wakeup *********/
				Intent intent2 = new Intent(context.getApplicationContext(),WakeUp.class);
				intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				intent2.putExtra("nom",name);
				context.startActivity(intent2);
		
				DateOperation dateo = new DateOperation();
				date[1] = dateo.incrementeDate(date[1]);
				
			}else{
				
				/*******Notification*********/  
				NotificationCreation nc = new NotificationCreation(context,"Rappel d'anniversaire",name+" le " +date[1],id_date);
				nc.createNotification();
				
			}
			
			/**********Creation d'une nouvelle alarme*********/			
			IntentCreation intentC = new IntentCreation(name,date[1],id_date,context);
			intentC.create();		
						
			/*******update DB*********/		
			dbHelper.update(intentC.getDate(),id_date,intentC.getDateRappel());
			
			wakeLock.release();

		} catch(Exception e) {
		    System.err.println("Exception: " + e.getMessage());
		} 
    }
}

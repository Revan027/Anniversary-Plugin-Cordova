package com.revan.anniversaryplugin.service;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.content.Context;
import org.apache.cordova.CordovaPlugin;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.R;
import android.os.Build;
import java.util.*;  
import android.app.NotificationChannel;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class NotificationService {

      /**
       * Permet de réveiller le téléphone
       */
      public static void handleWakeLock(Context context){
            PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
            WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");	//
            wakeLock.acquire();// L’arrêt de la veille se fait par la méthode acquire, l’activation par la méthode release          
      }

      /**
       * Permet de gérer le clavier
       */
      public static void handleKeyguardLock(Context context){    
            KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);	
            KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
            keyguardLock.disableKeyguard();          
      }

      /**
       * Permet de gérer la vibration
       */
      public static void handleVibrate(Context context){
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(3000);         
      }

      public static void createNotification(Context context, String titre, String message, int id)  {		
		Intent intent = new Intent();
		intent.setAction("com.revan.anniversaryplugin.ALARM");
		intent.setPackage(context.getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);	

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		Notification.Builder builder;
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder = new Notification.Builder(context,"memory-aid");		
		}else{
			builder = new Notification.Builder(context);
		}		
		Resources r = context.getResources();
            int resourceId = r.getIdentifier("ic_memory", "drawable", context.getPackageName());
		builder.setContentTitle(titre);//titre
		builder.setContentText(message);//contenu
		builder.setSmallIcon(resourceId);//icone
		builder.setContentIntent(pendingIntent);//pendingIntent pour faire rediriger sur l'appli
		builder.setAutoCancel(true);//ferme la notification lors du focus
					
		NotificationManager notifManager = (NotificationManager)
		context.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = builder.getNotification();
		notifManager.notify(id , notification);	
	}	
}      			
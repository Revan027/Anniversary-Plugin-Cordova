package com.revan.anniversaryplugin.service;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.content.Context;

public class NotificationService {

      /**
       * Permet de réveiller le téléphone
       */
      public static void handleWakeLock(Context context){
            PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
            WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");	//
            wakeLock.acquire();	// L’arrêt de la veille se fait par la méthode acquire, l’activation par la méthode release          
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
}      			
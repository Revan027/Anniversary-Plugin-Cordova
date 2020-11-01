package com.revan.anniversaryplugin.service;

import com.revan.anniversaryplugin.db.*;
import com.revan.anniversaryplugin.lib.*;
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
import android.app.PendingIntent;
public class AlarmReceiverService extends Service {
 
      @Override  
      public IBinder onBind(Intent intent) { 	
            return null;  		
      }  

      public void onDestroy() {  
            Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();   
            //this.stopForeground(true);
            
      } 
   
      public void onCreate() {
            NotificationCreation nc = new NotificationCreation(this,"Memory-aid","sdsdsd",1);  
            this.startForeground(120,nc.startForegroundNotification());

            
            /*WakeThread wakeThread = new WakeThread();
            wakeThread.run(); */
      } 
 
      public int onStartCommand (Intent intent, int flags, int startId) {    
           
            //this.stopSelf(startId); 	
		return START_NOT_STICKY ;
      }  
	
      
      public class WakeThread implements Runnable {
            private int i =0;
            private boolean running = true;

            public void run() {
                  while (running) {
                        try {
               
                              Thread.sleep(1000);
                              if(i == 10 )running = false;
                        } catch (InterruptedException ex) {
                              running = false;
                        }
            }
      }}
}
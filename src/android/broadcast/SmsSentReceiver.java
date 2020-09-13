package com.uniclau.alarmplugin.broadcast;

import com.revan.anniversaryplugin.lib.NotificationCreation;

import org.apache.cordova.CordovaPlugin;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import android.view.Gravity;
import android.R;
import java.util.*;  
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import 	android.view.LayoutInflater;
import android.view.ViewGroup;
import 	android.widget.ImageView;
import 	android.widget.TextView; 

public class SmsSentReceiver extends BroadcastReceiver {
	
	private int defaut=0;
	
    @Override
    public void onReceive(Context context, Intent intent) {
		
		switch(getResultCode()) {
			
			case Activity.RESULT_OK:	
				defaut=1;
			break;
			
			default:
				defaut=0;
            break;			
		};
		
		String nom = intent.getStringExtra("nom"); 
		int idAlarm = intent.getIntExtra("idAlarm",0); 
		
		if(defaut==1){

			NotificationCreation nc = new NotificationCreation(context,"Anniversaire de "+nom, "Sms envoyé à "+nom,idAlarm);
			nc.createNotification();
		}else{
			
			NotificationCreation nc = new NotificationCreation(context,"Memory-aid : Impossible d'envoyer le sms à "+nom, "Vérifiez votre réseau et renvoyez le message",idAlarm);
			nc.createNotification();

			/******* Creation du toast personalisé **********/	
			Resources r = context.getResources();
			int resourceId = r.getIdentifier("ic_memory", "drawable", context.getPackageName());
		
			int id1 = r.getIdentifier("toast", "layout", context.getPackageName());	
			int id2 = r.getIdentifier("text", "id", context.getPackageName());	
			int id3 = r.getIdentifier("image", "id", context.getPackageName());	
				
			Toast toast = new Toast(context);
			LayoutInflater inflater = LayoutInflater.from(context);
					
			View toastView = inflater.inflate(id1, null);	
				
			ImageView image = (ImageView) toastView.findViewById(id3);
			image.setImageResource(resourceId);
			
			TextView textView = toastView.findViewById(id2);
			textView.setText("Impossible d'envoyer le sms à "+nom+". Vérifiez votre réseau et renvoyez le message");
			textView.setWidth(300);
			textView.setHeight(100);
			textView.setTextSize(15);
			toast.setView(toastView);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
			toast.show();
			
			
			/******* Methode pour lancer une seconde activitée **********/
			/*Intent intent2 = new Intent();
			intent2.setAction("com.uniclau.alarmplugin.ALARM");
			intent2.setPackage(context.getPackageName());
			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);	//affiche la nouvelle activité
			context.startActivity(intent2);*/
					
			/*Intent intent3 = new Intent(context, ActivityDialog.class); 
			intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			intent3.putExtras(intent);
			context.startActivity(intent3);*/
			
			/******** Methode par lancement de même activité **********/
			/*SharedPreferencesCreation spc = new SharedPreferencesCreation(context);
			spc.put("NON OK");
			
			Intent intent3 = new Intent("SMS"); 
			intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			intent3.putExtras(intent);
			context.startActivity(intent3);*/
		}			
	}
}
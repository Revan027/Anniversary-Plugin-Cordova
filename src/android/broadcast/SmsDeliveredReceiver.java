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

public class SmsDeliveredReceiver extends BroadcastReceiver {
	
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
		
		if(defaut==1){
			
			/*NotificationCreation nc = new NotificationCreation(context,"Sms anniversaire", "Sms recu",0);
			nc.createNotification();*/			
		}else{
			
			/*NotificationCreation nc = new NotificationCreation(context,"Sms anniversaire", "Sms non recu",0);
			nc.createNotification();*/					
		}		
	}
}

package com.uniclau.alarmplugin.broadcast;

import com.revan.anniversaryplugin.db.*;
import com.uniclau.alarmplugin.lib.*;
import com.uniclau.alarmplugin.alarme.IntentCreation;
import com.uniclau.alarmplugin.RebootService;
import com.revan.anniversaryplugin.lib.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Build;
import android.widget.Toast;  

								/***************** traitement rélalisé lors du redémarrage du portable **********************/
public class AlarmBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intentp) {
		
        if (intentp.getAction().equals("android.intent.action.BOOT_COMPLETED")){
			
			Intent intent = new Intent(context, RebootService.class);
			
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { 	//version API 26 
				NotificationCreation nc = new NotificationCreation(context);	// init channel
				nc.CreateNotificationChannel();
				context.startForegroundService(intent);
				
			} else {  
	
				context.startService(intent);
			}
        }
    }
}

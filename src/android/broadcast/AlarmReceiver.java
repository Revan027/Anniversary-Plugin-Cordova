package com.revan.anniversaryplugin.broadcast;

import com.revan.anniversaryplugin.service.*;
import com.revan.anniversaryplugin.activity.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	
      @Override
      public void onReceive(Context context, Intent intent) {
            NotificationService.handleWakeLock(context);
            NotificationService.handleKeyguardLock(context);
            NotificationService.handleVibrate(context);

            Intent intentActivity = new Intent(context,WakeUp.class);
            intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK ); 
            intentActivity.putExtra("nom","ddd");
            context.startActivity(intentActivity);
    }
}
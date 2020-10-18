package com.revan.anniversaryplugin.broadcast;



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
		
    }
}

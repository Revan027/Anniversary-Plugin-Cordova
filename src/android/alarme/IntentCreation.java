package com.revan.anniversaryplugin.alarme;
import com.revan.anniversaryplugin.broadcast.*;
import com.revan.anniversaryplugin.service.*;
import com.revan.anniversaryplugin.lib.DateOperation;
import org.apache.cordova.CordovaPlugin;
import android.content.Intent;
import android.content.Context;
import java.util.GregorianCalendar;	
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentCreation {
      private String date;
	private Context context;
	
	public IntentCreation(String date,Context context){ 
		DateOperation dateOp = new DateOperation();
		this.date = date;//dateOp.creationRappel(date);	//la date servant pour l'alarme	
		this.context = context;
     }  
	
	public Intent Create(){	
		Intent intent = new Intent(this.context, AlarmReceiver.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("date",this.date);
            
            return intent;
	}	
}
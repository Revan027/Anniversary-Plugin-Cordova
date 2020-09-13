package com.uniclau.alarmplugin.sms;

import org.apache.cordova.CordovaPlugin;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import java.util.ArrayList;

/********** Creation de sms **************/
public class SmsCreation{
	
	private Context context;
	private String message;
    private String num;
	private String name;
	private int id;
	
	public SmsCreation(Context context,ArrayList<String> al,String message,String name,int id) { 
	
		this.context = context;
		this.message = message;
		this.num = al.get(0);
		this.name = name;
		this.id = id;
    } 
	
	public void createSms()  { 
			
			this.message = this.message.replaceAll("nom",name);
			
			SmsManager sms = SmsManager.getDefault();
			Intent intent = new Intent("SMS_DELIVERED");	
			intent.putExtra("idAlarm",this.id);
			intent.putExtra("nom",this.name);
			intent.putExtra("num_tel",this.num);
			intent.putExtra("message",this.message);
			
			Intent intent2 = new Intent("SMS_SENT");
			intent2.putExtra("idAlarm",this.id);
			intent2.putExtra("nom",this.name);
			intent2.putExtra("num_tel",this.num);
			intent2.putExtra("message",this.message);
			
			PendingIntent sentPI = PendingIntent.getBroadcast(this.context, this.id , intent2,  PendingIntent.FLAG_CANCEL_CURRENT);
			PendingIntent deliveredPI = PendingIntent.getBroadcast(this.context,this.id , intent,  PendingIntent.FLAG_CANCEL_CURRENT);
			sms.sendTextMessage(this.num, null, this.message,sentPI, deliveredPI);		
	}	
}
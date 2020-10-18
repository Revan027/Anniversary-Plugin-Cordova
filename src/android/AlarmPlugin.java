package com.revan.anniversaryplugin;

import com.revan.anniversaryplugin.db.*;
import com.revan.anniversaryplugin.lib.*;
import com.revan.anniversaryplugin.alarme.*;
import java.text.SimpleDateFormat;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import java.util.ArrayList;
import java.util.Date;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import android.os.Environment;
import android.os.Bundle;
import java.util.*;  
import android.os.Vibrator;
import android.content.Context;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.app.NotificationChannel;
import android.telephony.SmsManager;
import android.R;
import android.provider.ContactsContract;
import 	android.provider.ContactsContract.CommonDataKinds;
import android.content.pm.PackageManager;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.database.Cursor;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Toast;
import org.apache.cordova.PluginResult;
import java.util.HashMap;
import java.util.Date;

/***************** classe liant avec l'interface **********************/
public class AlarmPlugin extends CordovaPlugin{

      public static final int RESULT_OK=-1;
      private CallbackContext callback  = null;
      public Option option = null;
      public User user = null;
      public Anniversary anniversary = null;
      
      
      @Override
	public void onPause(boolean multitasking) {
		
		Log.d("AlarmPlugin", "onPause");
		super.onPause(multitasking);
	}


	@Override
	public void onResume(boolean multitasking) {
		
		Log.d("AlarmPlugin", "onResume " );
		super.onResume(multitasking);
		
		PowerManager pm = (PowerManager)this.cordova.getActivity().getSystemService(Context.POWER_SERVICE);
		WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
		wakeLock.acquire();
 
		KeyguardManager keyguardManager = (KeyguardManager) this.cordova.getActivity().getSystemService(Context.KEYGUARD_SERVICE); 
		KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
		keyguardLock.disableKeyguard();

      }
      

	public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {
            this.callback = callbackContext;
      }
      
      
	/***************** activité pour accès aux contacts **********************/
	@Override
      public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		
		 if (requestCode == 1) {

			if(resultCode == cordova.getActivity().RESULT_OK){// Get the URI that points to the selected contact

				Uri contactUri = data.getData();
				// We only need the NUMBER column, because there will be only one row in the result
				String[] projection = {Phone.NUMBER};
				// Perform the query on the contact to get the NUMBER column
				// We don't need a selection or sort order (there's only one result for the given URI)
				// CAUTION: The query() method should be called from a separate thread to avoid blocking
				// your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
				// Consider using <code><a href="/reference/android/content/CursorLoader.html">CursorLoader</a></code> to perform the query.
				Cursor cursor = this.cordova.getActivity().getContentResolver().query(contactUri, projection, null, null, null);
				cursor.moveToFirst();
				// Retrieve the phone number from the NUMBER column
				int column = cursor.getColumnIndex(Phone.NUMBER);
				String number = cursor.getString(column);

				PluginResult result = new PluginResult(PluginResult.Status.OK, number); 
				result.setKeepCallback(false);
				this.callback.sendPluginResult(result);
				
			}else if(resultCode == cordova.getActivity().RESULT_CANCELED){			
			}
		}
    }

	
	/************************************************************* methode du plugin *************************************************************/
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

            String resp = "";

		try {			
			if("Init".equals(action)){		
                        if(this.Init()) resp = "Init ok";
                        else resp = "Erreur à l'initialisation de l'application";

                        resp = InitAlarm();
                  }
                  else if("PhoneContacts".equals(action)){	
				/*int REQUEST_SELECT_CONTACT = 1;
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
				if (intent.resolveActivity(this.cordova.getActivity().getPackageManager()) != null) {
					this.cordova.getActivity().startActivityForResult(intent, REQUEST_SELECT_CONTACT);	
				}*/	
				int PICK_CONTACT_REQUEST = 1;
				this.callback = callbackContext;
				Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
				pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
			      cordova.startActivityForResult(this,pickContactIntent, PICK_CONTACT_REQUEST);

				return true;
                  } 
                  else if ("GetUsers".equals(action)) resp = this.user.GetAll().toString();
                  else if ("DeleteUser".equals(action)){
                        String[] tabId =  args.getString(0).split(",");

                        for(int i = 0; i < tabId.length;i++){
					tabId[i] = tabId[i].replaceAll("[^0-9,A-Z]", "");     //retrait des caractères spéciaux ([])
				}
                     
                        if(this.user.Delete(tabId))  resp = "ok";
                  } 
                  else if ("UpdateUserState".equals(action)){
                        HashMap data = new HashMap<>();
                        data.put("State",args.getInt(1));
 
                        if(this.user.UpdateState(args.getInt(0),data))  resp = "ok";                     
                  } 
                  else if ("UpdateUser".equals(action)){
                        int id = args.getInt(0);
                        String name = args.getString(1);
                        String phone = args.getString(2);

                        this.user = new User(this.cordova.getActivity(),name,phone); 
    
                        if(this.user.Update(id))  resp = "ok";                     
                  } 
                  else if ("AddUser".equals(action)) {			
                        try {						
                              String date =  args.getString(0);
                              String name = args.getString(1);
                              String phone = args.getString(2);
                              String hour = "";
                              Date obDate = DateOperation.ConvertToDate(date,"dd-MM-yyyy");

                              JSONArray jsonOption = this.option.GetAll();
                              for (int i=0; i < jsonOption.length(); i++) {
                                    JSONObject obj = jsonOption.getJSONObject(i);
                                    hour =  obj.get("hour").toString();
                              }                           
                              String fullDate = date + " " + hour;//date complete
                                                                      
                              if(obDate.before( new Date())) resp = "La date est passée !";
                              else{      
                                   

                                    this.user = new User(this.cordova.getActivity(),name,phone); 
                                    long id = this.user.Add();

                                    this.anniversary = new Anniversary(this.cordova.getActivity(),obDate,obDate,id); 
                                    this.anniversary.Add();

                                    resp = "h";
                              }  
                        } catch(Exception e) {	
                              callbackContext.error(e.getMessage());
                              return false;
                        }                      	
                  } 
                  else if ("GetOptions".equals(action))  resp = this.option.GetAll().toString();	                 
                  else if ("UpdateOptions".equals(action)) {
                        String sms = args.getString(0);
                        String hour = args.getString(1);
                      
                        try {	
                              if(this.option.Update( sms, hour)){                          		
                                    String date = DateOperation.ConvertToString(new Date()) +" "+ hour;
                                    IntentCreation intentC = new IntentCreation(date,this.cordova.getActivity()); 
                                    Alarme alarm = new Alarme(intentC.Create(),this.cordova.getActivity());	
                                    alarm.Update();

                                    resp = "Mise à jour effectuée";	
                              }					
            
                        } catch(Exception e) {	
                              callbackContext.error(e.getMessage());
                              return false;
                        }         	
                  }
                  else if ("SearchDate".equals(action)) {
                        try {	
                              String dateSearch =  args.getString(0);
      
                              resp = this.anniversary.GetByDate(dateSearch).toString();

                        } catch(Exception e) {	
                              callbackContext.error(e.getMessage());

                              return false;
                        }         	
			}			            			
		} catch(Exception e) {	

                  callbackContext.error(e.getMessage());
                  
                  return false;
            } 
            callbackContext.success(resp);
            return true; 
      }


      /***************** Initialisation de l'application (channel de notification) **********************/
      private boolean Init(){
            this.option = new Option(this.cordova.getActivity());
            this.user = new User(this.cordova.getActivity());           
            this.anniversary = new Anniversary(this.cordova.getActivity());

            NotificationCreation nc = new NotificationCreation(this.cordova.getActivity());// init channel
            nc.CreateNotificationChannel();
            

            return true;
				//callbackContext.success(dbHelper.patch2());
				///ArrayList<String> al = new ArrayList<String>();
				//al = dbHelper.GetAll();
						
				/*Fichier fi=new Fichier(al);	
				boolean reussite=fi.create(); */

				/*ArrayList<String> al2 = new ArrayList<String>();
				al2 = dbHelper.getAll2();*/

				/*Context context = this.cordova.getActivity().getApplicationContext();

				Intent intent2 = new Intent(context.getApplicationContext(),WakeUp.class);
				intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				intent2.putExtra("nom","ddd");
				context.startActivity(intent2);*/

				/*if(reussite==true){
					
					callbackContext.success(fi.get_location_absolute());
				}else{
					
					callbackContext.error("Erreur lors de la création du fichier");
				}*/
      } 
      
      private String InitAlarm(){
            String hour = ParseJson.GetHour(this.option);        
            String date = DateOperation.ConvertToString(new Date()) +" "+ hour;
            IntentCreation intentC = new IntentCreation(date,this.cordova.getActivity());           
            Alarme alarm = new Alarme(intentC.Create(),this.cordova.getActivity());				

            return alarm.Create();
      }
}	
package com.revan.anniversaryplugin;

import com.revan.anniversaryplugin.model.*;
import com.revan.anniversaryplugin.db.*;
import com.revan.anniversaryplugin.lib.*;
import com.revan.anniversaryplugin.alarme.*;
import com.revan.anniversaryplugin.service.*;
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


/***************** classe liant avec l'interface **********************/
public class AlarmPlugin extends CordovaPlugin{

      public static final int RESULT_OK = -1;
      private CallbackContext callback = null;
      private OptionService optionServ = null;
      private UserService userServ = null;
      private AlarmeService alarmServ = null;	
          
      @Override
	public void onPause(boolean multitasking) {	
		super.onPause(multitasking);
	}

	@Override
	public void onResume(boolean multitasking) {		
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
                  else if ("GetUsers".equals(action)){
                        String limit = args.getString(0);
                        String offset = args.getString(1);
                        resp = this.userServ.getAllInJSON(limit, offset);
                  }
                  else if ("getUsersNbr".equals(action)){
                        resp = Integer.toString(this.userServ.getUsersNbr());
                  }
                  else if ("DeleteUser".equals(action)){
                        String[] tabId =  args.getString(0).split(",");

                        for(int i = 0; i < tabId.length;i++){
					tabId[i] = tabId[i].replaceAll("[^0-9,A-Z]", "");//retrait des caractères spéciaux ([])
				}
                     
                        if(this.userServ.delete(tabId))  resp = "ok";
                  } 
                  else if ("UpdateUserState".equals(action)){
                        int idUser = args.getInt(0);
                        boolean state = args.getInt(1) == 1 ? true : false;
                        User user = this.userServ.getById(idUser);

                        user.setState(state);
   
                        if(this.userServ.update(user,idUser))  resp = "ok";                     
                  } 
                  else if ("UpdateUser".equals(action)){
                        int idUser = args.getInt(0);
                        String name = args.getString(1);
                        String phone = args.getString(2);
                        User user = this.userServ.getById(idUser);

                        user.setName(name);
                        user.setPhone(phone);

                        if(this.userServ.update(user,idUser))  resp = "ok";                   
                  } 
                  else if ("AddUser".equals(action)) {			
                        try {						
                              String date =  args.getString(0);
                              String name = args.getString(1);
                              String phone = args.getString(2);
                              String hour = "";
                              Date obDate = DateOperation.ConvertToDate(date,"dd-MM-yyyy");
                              Date dateRapel = DateOperation.creationRappel(obDate);

                              hour = this.optionServ.getHour();
                              String fullDate = date + " " + hour;//date complete
                                                                      
                              if(obDate.before( new Date())) resp = "La date est passée !";

                              else{                                   
                                    User user = new User(0,name,phone,obDate,dateRapel,false); 
                                    this.userServ.add(user);

                                    resp = "ok";
                              }  
                        } catch(Exception e) {	
                              callbackContext.error(e.getMessage());

                              return false;
                        }                      	
                  } 
                  else if ("GetOptions".equals(action))  resp = this.optionServ.getAll();	

                  else if ("UpdateOptions".equals(action)) {
                        String sms = args.getString(0);
                        String hour = args.getString(1);
                      
                        try {	
                              if(this.optionServ.update(sms, hour)){                          		
                                    String date = DateOperation.ConvertToString(new Date(),"dd-MM-yyyy") +" "+ hour;
                                    IntentCreation intentC = new IntentCreation(date,this.cordova.getActivity()); 
                                    this.alarmServ.Update(intentC.Create(),this.cordova.getActivity());	

                                    resp = "Mise à jour effectuée";	
                              }					
            
                        } catch(Exception e) {	
                              callbackContext.error(e.getMessage());

                              return false;
                        }         	
                  }
                  else if ("SearchDate".equals(action)) {
                        try {	
                              String dateSearch = args.getString(0);  

                              resp = this.userServ.searchMonthAnniv(dateSearch);

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
            this.optionServ = new OptionService(this.cordova.getActivity());
            this.userServ = new UserService(this.cordova.getActivity());  
            this.alarmServ = new AlarmeService();
           
            NotificationCreation nc = new NotificationCreation(this.cordova.getActivity());// init channel
            nc.CreateNotificationChannel();
            
            return true;
      } 
      
      private String InitAlarm(){
            String hour = this.optionServ.getHour();        
            String date = DateOperation.ConvertToString(new Date(),"dd-MM-yyyy") +" "+ hour;
            IntentCreation intentC = new IntentCreation(date,this.cordova.getActivity());           		

            return this.alarmServ.Create(intentC.Create(),this.cordova.getActivity());
      }
}	
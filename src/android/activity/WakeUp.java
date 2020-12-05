package com.revan.anniversaryplugin.activity;

import com.revan.anniversaryplugin.lib.*;
import com.revan.anniversaryplugin.model.*;
import com.revan.anniversaryplugin.service.*;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.R;
import android.content.res.Resources;
import android.widget.TextView;
import android.content.Intent;
import java.util.Date;
import android.database.Cursor;
import android.widget.Toast; 
import java.util.*;

public class WakeUp extends Activity  {

      private UserService userServ = null;
      private TextView textView = null;
      private String dateNow = null;
      private int notifId = 1;

      @Override
      protected void onCreate(Bundle savedInstanceState) {		
            super.onCreate(savedInstanceState); 

            this.userServ = new UserService(this); 
            this.dateNow = DateOperation.ConvertToString(new Date(),"dd-MM-yyyy");     
          
            String package_name = getApplication().getPackageName();     
            Intent intent = getIntent();// récupère un intent depuis une activitée
            int nbrUserRappel = intent.getIntExtra("nbrUserRappel", 0);
            int nbrUserAnniv = intent.getIntExtra("nbrUserAnniv", 0);

		setContentView(getApplication().getResources().getIdentifier("wake_up_activity", "layout", package_name));//ajout du view

            Resources r = getApplication().getResources();  
            int idTextView = r.getIdentifier("textView", "id", package_name);// récupère l'id du textview
            this.textView = (TextView)findViewById(idTextView);
            
            User[] users = this.userServ.searchUserAnniv(dateNow);
            this.fillingTextViewAnniv(users);

            users = this.userServ.searchUserAnnivRappel(dateNow);
            this.fillingTextViewRappel(users);
      }
		
      private void fillingTextViewRappel(User[] users){        
            String text = "";

            for (User user : users) {
                  Map<String, String> map = new HashMap<String, String>();
                  map.put("name", user.getName());
                  map.put("date", DateOperation.ConvertToString(user.getDateAnniv(),"dd-MM-yyyy"));  

                  text += EnumNotification.BIRTHDAY_REMINDER_CONTENT.replaceContent(map);
                  NotificationService.createNotification(
                        this, 
                        EnumNotification.BIRTHDAY_REMINDER.getLibelle(), 
                        EnumNotification.BIRTHDAY_REMINDER_CONTENT.replaceContent(map),  
                        notifId);
                  notifId++;
                  this.userServ.handleAnniv(user);
            } 
            this.textView.setText(text);       
      }

      private void fillingTextViewAnniv(User[] users){         
            String text = "";

            for (User user : users) {
                  Map<String, String> map = new HashMap<String, String>();
                  map.put("name", user.getName());
                  map.put("date",  DateOperation.ConvertToString(user.getDateAnniv(),"dd-MM-yyyy"));  

                  text += EnumNotification.BIRTHDAY_CONTENT.replaceContent(map);
                  NotificationService.createNotification(
                        this, 
                        EnumNotification.BIRTHDAY.getLibelle(), 
                        EnumNotification.BIRTHDAY_CONTENT.replaceContent(map),  
                        notifId);
                  notifId++;
                  this.userServ.handleAnniv(user);
            } 
            this.textView.setText(text);
      }

      private void fillingTitle( int nbrUserRappel, int nbrUserAnniv){
           ///if(nbrUserRappel > 1)
      }

      @Override
	protected void onPause(){
            super.onPause();

            Toast.makeText(this, "pause", Toast.LENGTH_LONG).show(); 
      }

      /*@Override
	protected void onResume(){
            super.onResume();
            Toast.makeText(this, "resume", Toast.LENGTH_LONG).show(); 
		finish();	
      }*/
}
package com.revan.anniversaryplugin.activity;

import com.revan.anniversaryplugin.lib.*;
import com.revan.anniversaryplugin.db.*;
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

public class WakeUp extends Activity  {

      private UserService userServ = null;
      private TextView textView = null;
      private String dateNow = null;

      @Override
      protected void onCreate(Bundle savedInstanceState) {		
            super.onCreate(savedInstanceState); 

            this.userServ = new UserService(this); 
            this.dateNow = DateOperation.ConvertToString(new Date(),"dd-MM-yyyy");     
            this.createNotification();

            String package_name = getApplication().getPackageName();     
            Intent intent = getIntent();// récupère un intent depuis une activitée
            int nbrUserRappel = intent.getIntExtra("nbrUserRappel", 0);
            int nbrUserAnniv = intent.getIntExtra("nbrUserAnniv", 0);
                

		setContentView(getApplication().getResources().getIdentifier("wake_up_activity", "layout", package_name));//ajout du view

            Resources r = getApplication().getResources();  
            int idTextView = r.getIdentifier("textView", "id", package_name);// récupère l'id du textview
            this.textView = (TextView)findViewById(idTextView);
            
            Cursor cursor = this.userServ.searchUserAnniv(dateNow);
            this.fillingTextViewAnniv(cursor);

            cursor = this.userServ.searchUserAnnivRappel(dateNow);
            this.fillingTextViewRappel(cursor);
      }
	
	@Override
	protected void onPause(){
            super.onPause();

            Toast.makeText(this, "pause", Toast.LENGTH_LONG).show(); 
      }

      private void createNotification(){
            NotificationService.handleWakeLock(this);
            NotificationService.handleKeyguardLock(this);
            NotificationService.handleVibrate(this);
      }

      private void fillingTextViewRappel(Cursor cursor){ 
            String text = "";          
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {                 
                  String name = cursor.getString(1);
                  text += name; 

                  cursor.moveToNext();
            }
            cursor.close();
            this.textView.setText(text);
      }

      private void fillingTextViewAnniv(Cursor cursor){         
            String text = "";
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {                 
                  String name = cursor.getString(1);
                  String dateAnniv = cursor.getString(2);
                  text += "N'oubliez pas l'anniversaire de " + name +" le" + dateAnniv;

                  cursor.moveToNext();
            }
            cursor.close();
            this.textView.setText(text);
      }

      private void fillingTitle( int nbrUserRappel, int nbrUserAnniv){
           ///if(nbrUserRappel > 1)
      }

      /*@Override
	protected void onResume(){
            super.onResume();
            Toast.makeText(this, "resume", Toast.LENGTH_LONG).show(); 
		finish();	
      }*/
}
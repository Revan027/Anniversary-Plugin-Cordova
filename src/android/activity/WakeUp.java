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

      @Override
      protected void onCreate(Bundle savedInstanceState) {		
            super.onCreate(savedInstanceState); 

            this.userServ = new UserService(this); 

            String package_name = getApplication().getPackageName();     
            Intent intent = getIntent();// récupère un intent depuis une activitée
            String nom = intent.getStringExtra("nom");
            String dateNow = DateOperation.ConvertToString(new Date(),"dd-MM-yyyy");

		setContentView(getApplication().getResources().getIdentifier("wake_up_activity", "layout", package_name));//ajout du view

            Resources r = getApplication().getResources();  
            int idTextView = r.getIdentifier("textView", "id", package_name);// récupère l'id du textview
            TextView text = (TextView)findViewById(idTextView);
            
            Cursor cursor = this.userServ.searchUserAnniv(dateNow);
            //cursor.moveToFirst();

            /*text.setText("Aujourd'hui c'est l'anniversaire de :"); 
            while (!cursor.isAfterLast()) {                 
                  String name = cursor.getString(1);
                  text.setText(" \n " + name); 

                  cursor.moveToNext();
            }
            cursor.close();*/

            Cursor cursor = this.userServ.searchUserAnnivRappel(dateNow);

            cursor.moveToFirst();
            String texts = "lol";

            while (!cursor.isAfterLast()) {                 
                  String name = cursor.getString(1);
                  String dateAnniv = cursor.getString(2);
                  texts += "N'oubliez pas l'anniversaire de " + name +" le" + dateAnniv;

                  cursor.moveToNext();
            }
           
            cursor.close();  text.setText(texts);
      }
	
	@Override
	protected void onPause(){
            super.onPause();
            Toast.makeText(this, "pause", Toast.LENGTH_LONG).show(); 
		finish();	
      }

      /*@Override
	protected void onResume(){
            super.onResume();
            Toast.makeText(this, "resume", Toast.LENGTH_LONG).show(); 
		finish();	
      }*/
}
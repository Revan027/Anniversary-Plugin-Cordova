package com.revan.anniversaryplugin.activity;

import com.revan.anniversaryplugin.lib.*;
import com.revan.anniversaryplugin.db.*;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.R;
import android.content.res.Resources;
import android.widget.TextView;
import android.content.Intent;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WakeUp extends Activity  {

      @Override
      protected void onCreate(Bundle savedInstanceState) {		
            super.onCreate(savedInstanceState); 

            String package_name = getApplication().getPackageName();     
            Intent intent = getIntent();// récupère un intent depuis une activitée
            String nom = intent.getStringExtra("nom");
            
		setContentView(getApplication().getResources().getIdentifier("wake_up_activity", "layout", package_name));//ajout du view

            Resources r = getApplication().getResources();  
            int idTextView = r.getIdentifier("textView", "id", package_name);// récupère l'id du textview
            TextView text = (TextView)findViewById(idTextView);

            String date = DateOperation.ConvertToString(new Date());
            //Anniversary anniversary = new Anniversary(this);
            //JSONArray jsonArray = anniversary.GetByCompleteDate(date);
          
            //if(new Date().equals())
                
            ///Users[] users = ParseJson.ParseToUser(jsonArray);

            /*for( User user : users ) {
                  text.setText("Aujourd'hui c'est l'anniversaire de : " + user.getPhone()); 
            }*/
            
      }
	
	@Override
	protected void onPause(){
            super.onPause();
            
		finish();	
      }
}
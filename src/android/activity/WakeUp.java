package com.revan.anniversaryplugin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.R;
import android.content.res.Resources;
import android.widget.TextView;
import android.content.Intent;

public class WakeUp extends Activity  {

      @Override
      protected void onCreate(Bundle savedInstanceState) {		
            super.onCreate(savedInstanceState); 

            String package_name = getApplication().getPackageName();
            Intent intent = getIntent();
            String nom = intent.getStringExtra("nom");
            
		setContentView(getApplication().getResources().getIdentifier("wake_up_activity", "layout", package_name));

		Resources r = getApplication().getResources();  
            int t = r.getIdentifier("textView", "id", package_name);// récupère l'id du textview

		TextView text = (TextView) findViewById(t);
            text.setText("Aujourd'hui c'est l'anniversaire de : " + nom);
     
      }
	
	@Override
	protected void onPause(){
            super.onPause();
            
		finish();	
      }
}
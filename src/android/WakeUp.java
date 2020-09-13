package com.uniclau.alarmplugin;


import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.Window;
import android.app.KeyguardManager;
import android.os.Build;
import android.content.Context;
import android.R;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class WakeUp extends Activity  {
 	private TextView  text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String nom = intent.getStringExtra("nom");
        String package_name = getApplication().getPackageName();
		
		//Permet l'affichage de l'activité, malgré la mise en veille sécurisée du téléphone
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)	
	    {
	        setShowWhenLocked(true);
	        setTurnScreenOn(true);
	        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
	        if(keyguardManager!=null)
	            keyguardManager.requestDismissKeyguard(this, null);
	    }
	    else 
	    {
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
			WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |  
			WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
			WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
	    }

		setContentView(getApplication().getResources().getIdentifier("wake_up_activity", "layout", package_name));

		Resources r = getApplication().getResources();  
        int t = r.getIdentifier("textView", "id", package_name);// récupère l'id du textview
		text = (TextView) findViewById(t);
		text.setText("Aujourd'hui c'est l'anniversaire de : " + nom);
    }

	
	@Override
	protected void onPause(){
		super.onPause();
		super.onDestroy();
		
	}
}
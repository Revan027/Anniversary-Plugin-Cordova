package com.uniclau.alarmplugin.broadcast;

import com.revan.anniversaryplugin.lib.*;
import com.revan.anniversaryplugin.service.*;
import com.revan.anniversaryplugin.activity.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Date;
import android.os.Build;

public class AlarmBoot extends BroadcastReceiver {
      private UserService userServ = null;

      @Override
      public void onReceive(Context context, Intent intentp) {		
            if (intentp.getAction().equals("android.intent.action.BOOT_COMPLETED")){
                  this.userServ = new UserService(context); 

                  Intent intentServ = new Intent(context, AlarmBroadcastService.class);
                  intentServ.putExtra("broadcast","ALARM_RECEIVER");

                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {// version API 26 
                        context.startForegroundService(intentServ);        
                  } else  context.startService(intentServ);

                  String dateNow = DateOperation.ConvertToString(new Date(),"dd-MM-yyyy");
                  int nbrUserRappel = this.userServ.countMonthAnniv(dateNow);
                  int nbrUserAnniv = this.userServ.countUserAnniv(dateNow);

                  if(nbrUserRappel > 0 || nbrUserAnniv > 0)
                  {
                        Intent intentActivity = new Intent(context,WakeUp.class);
                        intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK ); 
                        intentActivity.putExtra("nbrUserRappel",nbrUserRappel);
                        intentActivity.putExtra("nbrUserAnniv",nbrUserAnniv);
                        context.startActivity(intentActivity);
                  }   
            }
    }
}

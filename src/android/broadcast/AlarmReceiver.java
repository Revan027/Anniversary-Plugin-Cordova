package com.revan.anniversaryplugin.broadcast;

import com.revan.anniversaryplugin.lib.*;
import com.revan.anniversaryplugin.service.*;
import com.revan.anniversaryplugin.activity.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
      
      private UserService userServ = null;

      @Override
      public void onReceive(Context context, Intent intent) {
            this.userServ = new UserService(context); 

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
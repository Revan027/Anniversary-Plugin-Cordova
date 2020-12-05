package com.revan.anniversaryplugin.lib;

import org.apache.cordova.CordovaPlugin;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.R;
import android.os.Build;
import java.util.*;  
import android.app.NotificationChannel;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.revan.anniversaryplugin.activity.*;

public class NotificationCreation{
	
	private Context context;
	private String nom;
      private String titre;
	private String message;
	private int id;
	
	public NotificationCreation(Context context, String titre, String message, int id) { 
		this.context = context;
		this.titre = titre;
		this.message = message;
		this.id = id;
      } 
	
	public NotificationCreation(Context context) { 	
		this.context = context;
     } 
	
	public void createNotification()  {		
		Intent intent = new Intent();
		intent.setAction("com.revan.anniversaryplugin.ALARM");
		intent.setPackage(context.getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);	

		PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, intent, 0);
		Notification.Builder builder;
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder = new Notification.Builder(this.context,"memory-aid");		
		}else{
			builder = new Notification.Builder(this.context);
		}		
		Resources r = context.getResources();
            int resourceId = r.getIdentifier("ic_memory", "drawable", context.getPackageName());
		builder.setContentTitle(this.titre);//titre
		builder.setContentText(this.message);//contenu
		builder.setSmallIcon(resourceId);//icone
		builder.setContentIntent(pendingIntent);//pendingIntent pour faire rediriger sur l'appli
		builder.setAutoCancel(true);//ferme la notification lors du focus
					
		NotificationManager notifManager = (NotificationManager)
		this.context.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = builder.getNotification();
		notifManager.notify(this.id , notification);	

	}	

	/***************** Créer le NotificationChannel, seulement pour API 26+	**********************/
	public void CreateNotificationChannel() {
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			
			CharSequence name = "memory-aid channel";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel("memory-aid", name, importance);
			channel.setDescription("Notification memory-aid");
			// Enregister le canal sur le système : attention de ne plus rien modifier après
			NotificationManager notificationManager = this.context.getSystemService(NotificationManager.class);
			Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
		}
	}   
	
	
	
	/***************** Créer le NotificationChannel, seulement pour API 26+	**********************/
	public Notification startForegroundNotification() {
		
		this.CreateNotificationChannel();
            Intent fullScreenIntent = new Intent(this.context, WakeUp.class);
            fullScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK ); 
            fullScreenIntent.putExtra("nom","ddd");
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this.context, 2,fullScreenIntent,0);
		Notification.Builder builder;
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder = new Notification.Builder(this.context,"memory-aid");		// Construction d'un builder pour la notifiacation	
		}else{
			builder = new Notification.Builder(this.context);		// Construction d'un builder pour la notifiacation
		}
		
		Resources r = context.getResources();
        int resourceId = r.getIdentifier("ic_memory", "drawable", context.getPackageName());
        builder.setContentTitle("Incoming call");
        builder.setContentText("(919) 555-1234");
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setCategory(Notification.CATEGORY_CALL);
		builder.setSmallIcon(resourceId);	//icone
            builder.setFullScreenIntent(fullScreenPendingIntent, true);


		/*NotificationManager notifManager = (NotificationManager)
		this.context.getSystemService(Context.NOTIFICATION_SERVICE);*/

            /*NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
               manager.notify(1, notification.build());*/
            //Notification notification = builder.getNotification();
            NotificationManager notifManager = (NotificationManager)
		this.context.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = builder.getNotification();
		notifManager.notify(1 , notification);	

		return notification;
      }   
      
      public Notification test(){
           /*this.CreateNotificationChannel();

            Intent fullScreenIntent = new Intent(this.context, WakeUp.class);
            fullScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK ); 
            fullScreenIntent.putExtra("nom","ddd");
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this.context, 2,fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this.context, "memory-aid")
            .setSmallIcon(R.drawable.ic_call_green)
            .setContentTitle("Incoming call")
            .setContentText("(919) 555-1234")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setFullScreenIntent(fullScreenPendingIntent, true);

            Notification incomingCallNotification = notificationBuilder.build();
            return incomingCallNotification;*/
   return null;
      }
}
package com.revan.anniversaryplugin.service;

import com.revan.anniversaryplugin.db.*;
import java.util.Date;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class OptionService {       
      private OptionRepository OptionRepository;

      public OptionService(Context context){
            this.OptionRepository = new OptionRepository(context);
      }
    
      public String getAll(){
            return this.OptionRepository.getAll().toString();
      }

      public String getHour(){
            String hour = "";

            try{
                  JSONArray jsonOption = this.OptionRepository.getAll();
                  
                  for (int i = 0; i < jsonOption.length(); i++) {
                        JSONObject obj = jsonOption.getJSONObject(i);
                        hour =  obj.get("hour").toString();
                  }   
            } 
            catch(Exception e) {	
                  return "Erreur : " + e.getMessage();
            }    	

            return hour;
      }

      public boolean update(String sms, String hour){
            return this.OptionRepository.update(sms, hour);
      }
}
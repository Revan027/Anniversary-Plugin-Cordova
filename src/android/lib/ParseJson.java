package com.revan.anniversaryplugin.lib;

import com.revan.anniversaryplugin.db.Option;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;  

public class ParseJson {
	
	public static String GetHour(Option option){	
            String hour = "";

            try{
                  JSONArray jsonOption = option.GetAll();
                  
                  for (int i=0; i < jsonOption.length(); i++) {
                        JSONObject obj = jsonOption.getJSONObject(i);
                        hour =  obj.get("hour").toString();
                  }   
            } catch(Exception e) {	
                  return "Erreur : " + e.getMessage();
            }    	
            return hour;
	}
}
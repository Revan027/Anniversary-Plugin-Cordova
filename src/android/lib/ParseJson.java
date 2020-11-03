package com.revan.anniversaryplugin.lib;

import com.revan.anniversaryplugin.model.*;
import com.revan.anniversaryplugin.db.Option;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;  

public class ParseJson {

      public static User[] ParseToUser(JSONArray jsonArray){
           /* int length = jsonArray.length();
            User users = new User[length];

            try{                 
                  for (int i = 0; i < length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.get("name").toString();
                        String phone = jsonObject.get("date").toString();
                        String dateAnniv = jsonObject.get("name").toString();
                        String state = jsonObject.get("name").toString();

                        users[i] = new user(name,phone,dateAnniv,state);
                  }   
            } catch(Exception e) {	
                  return "Erreur : " + e.getMessage();
            }    	*/
            return null;
	}
      
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
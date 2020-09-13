package com.revan.anniversaryplugin.db;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.ContentValues;
import android.text.TextUtils;
import java.util.HashMap;

public final class User extends DAO {
      public static final String TABLE_NAME = "user";
      public static final String COLUMN_ID = "Id";
      public static final String COLUMN_NAME = "Name";	
	public static final String COLUMN_PHONE = "Phone";
	public static final String COLUMN_STATE = "State";
      public static final String COLUMN_ID_ANNIVERSARY = "Id_anniversary";
      
      private String Name;
      private String Phone;
      private boolean State;
      private int Id_Wakeup;

      public User(Context context,String name, String phone){
            super(context);   
            this.Name = name;
            this.Phone = phone;   
      }

      public User(Context context){
            super(context);   
      }

      public boolean Add(){
            try {
                  SQLiteDatabase db = this.getWritableDatabase();
                  ContentValues contentValues = new ContentValues();    
                  contentValues.put(COLUMN_NAME, this.Name);
                  contentValues.put(COLUMN_PHONE, this.Phone);	
                  contentValues.put(COLUMN_ID_ANNIVERSARY, 1);		
                  db.insert(TABLE_NAME, null, contentValues);
                  db.close();
                  
                  return true;
            }
            catch(Exception e) {
                  return false;
            }          
      }

      public boolean Update (int id,HashMap dataMap) {		
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues data = new ContentValues();
            Object state = dataMap.get("State");

            if(state != null)  data.put(COLUMN_STATE,(int)state);
          
            db.update(TABLE_NAME, data, COLUMN_ID +" = "+id, null);

            return true;
      }

	public boolean Delete (String[] tabId) {			
            SQLiteDatabase db = this.getWritableDatabase();
            String idsCSV = TextUtils.join(",", tabId);
		db.delete(TABLE_NAME, "Id IN (" + idsCSV + ")", null); 
            db.close();   
            
            return true;
      }

      public JSONArray GetAll() {	         
            ArrayList<String> array_list = new ArrayList<String>();
            JSONArray array = new JSONArray();     
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) { 
                  try {	
                        JSONObject obj = new JSONObject();
                        obj.put("id", cursor.getInt(0));
                        obj.put("name", cursor.getString(1));
                        obj.put("phone", cursor.getString(2));
                        obj.put("state", cursor.getString(3));
                        array.put(obj);
                        
                  }catch(JSONException e) {
                        System.err.println("Exception: " + e.getMessage());
                  }
                  cursor.moveToNext();
            }
            cursor.close();
            return array;
      }
}
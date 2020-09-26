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

public final class Option extends DAO  {

      private String Text;
      private String LastName;
      private String Hour;

      public Option(Context context){
            super(context);      
      }

      public JSONArray GetAll() {	           
            ArrayList<String> array_list = new ArrayList<String>();
            JSONArray array = new JSONArray();              
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT "+COLUMN_TEXT+", "+COLUMN_HOUR+" FROM "+TABLE_NAME_3, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) { 
                  try {	
                        JSONObject obj = new JSONObject();
                        obj.put("sms", cursor.getString(0));
                        obj.put("hour", cursor.getString(1));
                        array.put(obj);
                        
                  }catch(JSONException e) {
                        System.err.println("Exception: " + e.getMessage());
                  }
                  cursor.moveToNext();
            }
            cursor.close();
            return array;
      }

      public boolean Update (String sms, String hour) {
            try {
                  SQLiteDatabase db = this.getWritableDatabase();
                  ContentValues data = new ContentValues();
                  data.put(COLUMN_TEXT,sms);
                  data.put(COLUMN_HOUR,hour);
                  db.update(TABLE_NAME_3, data, COLUMN_ID_3 +" = 1", null);
                  db.close(); 
                  return true;
            }
            catch(Exception e) {
                  return false;
            }    
      }
}
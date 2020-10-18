package com.revan.anniversaryplugin.db;
import java.util.Date;
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
import com.revan.anniversaryplugin.lib.DateOperation;

public final class Anniversary extends DAO {
      public static final String TABLE_NAME = "anniversary";
      public static final String COLUMN_ID  = "Id";
      public static final String COLUMN_DATE  = "Date";	
      public static final String COLUMN_DATE_RAPPEL = "DateRappel";
      public static final String COLUMN_ID_USER = "IdUser";

      private Date Date;
      private Date DateRappel;
      private long IdUser;

      public Anniversary(Context context,Date date, Date dateRappel,long idUser){
            super(context);   
            this.Date = date;
            this.DateRappel = dateRappel;   
            this.IdUser = idUser; 
      }

      public Anniversary(Context context){
            super(context);   
      }

      public long Add(){
            long ret = 0;
            try {
                  SQLiteDatabase db = this.getWritableDatabase();
                  ContentValues contentValues = new ContentValues();    
                  contentValues.put(COLUMN_DATE, DateOperation.ConvertToString(this.Date));
                  contentValues.put(COLUMN_DATE_RAPPEL,DateOperation.ConvertToString(this.DateRappel));
                  contentValues.put(COLUMN_ID_USER,this.IdUser);		
                  ret = db.insert(TABLE_NAME, null, contentValues);
                  db.close();
                  
                  return ret;
            }
            catch(Exception e) {
                  return ret;
            }          
      }

      public JSONArray GetByDate(String dateSearch){
            ArrayList<String> array_list = new ArrayList<String>();
            JSONArray array = new JSONArray();     
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT user.id as id, user.name as name, anniv.Date as date FROM "+TABLE_NAME+" as anniv "+
            "INNER JOIN "+ User.TABLE_NAME +" as user ON user.Id = anniv.IdUser WHERE user.Id IN "+
            "(SELECT IdUser FROM "+TABLE_NAME+" WHERE substr(anniv.Date, 4, 7) LIKE '"+dateSearch+"')";

            Cursor cursor = db.rawQuery(query, null); 
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) { 
                  try {	
                        JSONObject obj = new JSONObject();
                        obj.put("id", cursor.getInt(0));
                        obj.put("name", cursor.getString(1));
                        obj.put("date", cursor.getString(2));
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
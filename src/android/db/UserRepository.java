package com.revan.anniversaryplugin.db;

import com.revan.anniversaryplugin.lib.DateOperation;
import com.revan.anniversaryplugin.model.*;
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
import java.util.Date;

public final class UserRepository extends DAO {  
       
      private User User;

      public UserRepository(Context context){
            super(context);   
      }

      public int countUsers(){  
            int count = 0;
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT count(*) FROM "+DAO.TABLE_NAME+"",null);
            cursor.moveToFirst();
          
            while (!cursor.isAfterLast()) {          
                  count = cursor.getInt(0);
                        
                  cursor.moveToNext();
            }
            cursor.close();

            return count;
      }

      public int countMonthAnniv(String dateSearch){
            int count = 0;
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT count(*) FROM "+DAO.TABLE_NAME+" WHERE "+DAO.COLUMN_DATE_RAPPEL+" LIKE ? ";
            Cursor cursor = db.rawQuery(query, new String[] {dateSearch}); 
            cursor.moveToFirst();
          
            while (!cursor.isAfterLast()) {          
                  count = cursor.getInt(0);
                        
                  cursor.moveToNext();
            }
            cursor.close();

            return count;
      }

      public int countUserAnniv(String dateSearch){
            int count = 0;
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT count(*) FROM "+DAO.TABLE_NAME+" WHERE "+DAO.COLUMN_DATE_ANNIV+" LIKE ? ";
            Cursor cursor = db.rawQuery(query, new String[] {dateSearch}); 
            cursor.moveToFirst();
          
            while (!cursor.isAfterLast()) {          
                  count = cursor.getInt(0);
                        
                  cursor.moveToNext();
            }
            cursor.close();

            return count;
      }

      public long add(User user){
            long ret = 0;
            try {
                  SQLiteDatabase db = this.getWritableDatabase();
                  ContentValues contentValues = new ContentValues();    
                  contentValues.put(DAO.COLUMN_NAME, user.getName());
                  contentValues.put(DAO.COLUMN_PHONE, user.getPhone());	
                  contentValues.put(DAO.COLUMN_DATE_ANNIV, DateOperation.ConvertToString(user.getDateAnniv(),"dd-MM-yyyy"));
                  contentValues.put(DAO.COLUMN_DATE_RAPPEL, DateOperation.ConvertToString(user.getDateRappel(),"dd-MM-yyyy"));	
                  contentValues.put(DAO.COLUMN_STATE, user.getState());	

                  ret = db.insert(DAO.TABLE_NAME, null, contentValues);
                  db.close();  

                  return ret;
            }
            catch(Exception e) {
                  return ret;
            }          
      }

      public boolean update (User user, int id) {		
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues data = new ContentValues();

            data.put(DAO.COLUMN_PHONE,user.getPhone());
            data.put(DAO.COLUMN_NAME, user.getName());
            data.put(DAO.COLUMN_DATE_RAPPEL, DateOperation.ConvertToString(user.getDateRappel(),"dd-MM-yyyy")); 
            data.put(DAO.COLUMN_DATE_ANNIV, DateOperation.ConvertToString(user.getDateAnniv(),"dd-MM-yyyy")); 
            data.put(DAO.COLUMN_STATE,user.getState());      
            db.update(DAO.TABLE_NAME, data, DAO.COLUMN_ID +" = ?", new String[] {Integer.toString(id)});

            return true;
      }

	public boolean delete (String[] tabId) {			
            SQLiteDatabase db = this.getWritableDatabase();
            String idsCSV = TextUtils.join(",", tabId);
		db.delete(DAO.TABLE_NAME, DAO.COLUMN_ID+" IN (" + idsCSV + ")", null); 
            db.close();   

            return true;
      }

      public User getById(int id) {	         
            User user = null;    
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+DAO.TABLE_NAME+" WHERE "+DAO.COLUMN_ID+" = ?",new String[] {Integer.toString(id)});
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {          
                  String name = cursor.getString(1);
                  String phone = cursor.getString(2);
                  boolean state = cursor.getInt(3) == 1 ? true : false;
                  Date dateAnniv = DateOperation.ConvertToDate(cursor.getString(4),"dd-MM-yyyy");
                  Date dateRappel = DateOperation.ConvertToDate(cursor.getString(5),"dd-MM-yyyy");

                  user = new User(id,name,phone,dateAnniv,dateRappel,state);             
                  cursor.moveToNext();
            }
            cursor.close();

            return user;
      }

      public JSONArray getAll(String limit, String offset) {	
            JSONArray array = new JSONArray();     
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT "+DAO.COLUMN_ID+", "+DAO.COLUMN_NAME+", "+DAO.COLUMN_PHONE+", "+DAO.COLUMN_STATE+
                  " ,substr("+DAO.COLUMN_DATE_ANNIV+", 1, 11) as date, "+DAO.COLUMN_DATE_RAPPEL+" FROM "+DAO.TABLE_NAME+
                  " LIMIT "+limit+ " OFFSET "+offset, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) { 
                  try {	
                        JSONObject obj = new JSONObject();
                        obj.put("id", cursor.getInt(0));
                        obj.put("name", cursor.getString(1));
                        obj.put("phone", cursor.getString(2));
                        obj.put("state", cursor.getString(3));
                        obj.put("date", cursor.getString(4));
                        obj.put("dateRappel", cursor.getString(5));
                        array.put(obj);
                        
                  }catch(JSONException e) {
                        System.err.println("Exception: " + e.getMessage());
                  }
                  cursor.moveToNext();
            }
            cursor.close();

            return array;
      }

      public JSONArray getByMonthAnniv(String dateSearch){
            JSONArray array = new JSONArray();     
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT "+DAO.COLUMN_ID+", "+DAO.COLUMN_NAME+", "+DAO.COLUMN_DATE_ANNIV+" FROM "+DAO.TABLE_NAME+ 
                  " WHERE substr("+DAO.COLUMN_DATE_ANNIV+", 4, 7) LIKE ? ";
            Cursor cursor = db.rawQuery(query, new String[] {dateSearch}); 
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

      public User[] getByDateAnniv(String dateSearch){
            int i = 0;   
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM "+DAO.TABLE_NAME+ 
                  " WHERE substr("+DAO.COLUMN_DATE_ANNIV+", 1, 11) LIKE ? ";
            Cursor cursor = db.rawQuery(query, new String[] {dateSearch}); 
            User[] user = new User[cursor.getCount()];      
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {        
                  int id = cursor.getInt(0);  
                  String name = cursor.getString(1);
                  String phone = cursor.getString(2);              
                  Date dateAnniv = DateOperation.ConvertToDate(cursor.getString(3),"dd-MM-yyyy");
                  Date dateRappel = DateOperation.ConvertToDate(cursor.getString(4),"dd-MM-yyyy");
                  boolean state = cursor.getInt(5) == 1 ? true : false;

                  user[i] = new User(id,name,phone,dateAnniv,dateRappel,state);             
                  cursor.moveToNext();
                  i++;
            }
            cursor.close();

            return user;
      }

      public User[] getByDateRappel(String dateSearch){   
            int i = 0;   
            
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM "+DAO.TABLE_NAME+ 
                  " WHERE "+DAO.COLUMN_DATE_RAPPEL+" LIKE ? ";
            Cursor cursor = db.rawQuery(query, new String[] {dateSearch}); 
            User[] user = new User[cursor.getCount()];
            cursor.moveToFirst();
            

            while (!cursor.isAfterLast()) {        
                  int id = cursor.getInt(0);  
                  String name = cursor.getString(1);
                  String phone = cursor.getString(2);              
                  Date dateAnniv = DateOperation.ConvertToDate(cursor.getString(3),"dd-MM-yyyy");
                  Date dateRappel = DateOperation.ConvertToDate(cursor.getString(4),"dd-MM-yyyy");
                  boolean state = cursor.getInt(5) == 1 ? true : false;

                  user[i] = new User(id,name,phone,dateAnniv,dateRappel,state);             
                  cursor.moveToNext();
                  i++;
            }
            cursor.close();

            return user;
      }
}
package com.revan.anniversaryplugin.db;

import com.uniclau.alarmplugin.lib.Fichier;
import com.uniclau.alarmplugin.lib.*;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import 	android.os.Environment;
import java.util.*;  
import android.content.ContentValues;
import java.util.GregorianCalendar;	
import java.text.SimpleDateFormat;
import java.util.Date;
import 	java.io.InputStream;
import 	java.io.InputStreamReader;
import 	java.io.BufferedReader;
import java.io.FileInputStream;

public abstract class DAO extends SQLiteOpenHelper {

      public static final int DATABASE_VERSION = 1;  
      public static final String DATABASE_NAME = "MemoryAidAnniversary.db";

      /****************************************** Table Utilisateur********************************************************************/
      public static final String TABLE_NAME = "user";
      public static final String COLUMN_ID = "Id";
      public static final String COLUMN_NAME = "Name";	
	public static final String COLUMN_PHONE = "Phone";
	public static final String COLUMN_STATE = "State";
	public static final String COLUMN_ID_ANNIVERSARY = "Id_anniversary";

      /****************************************** Table Date ********************************************************************/
      public static final String TABLE_NAME_2 = "anniversary";
      public static final String COLUMN_ID_2  = "Id";
      public static final String COLUMN_DATE  = "Date";	
      public static final String COLUMN_DATE_RAPPEL = "DateRappel";

      /****************************************** Table Option ********************************************************************/
      public static final String TABLE_NAME_3 = "option";
      public static final String COLUMN_ID_3  = "Id";
      public static final String COLUMN_TEXT  = "Text";	
      public static final String COLUMN_HOUR = "Hour";

   
      String[] allColumns = {COLUMN_ID,COLUMN_NAME,COLUMN_PHONE,COLUMN_STATE,COLUMN_ID_ANNIVERSARY};
      
     
      /***************** le constructeur parent verifie et appelle si besoin les fonctions onCreate et onUpgrade **********************/
      public DAO(Context context ) {	
            
            super(context, DATABASE_NAME, null, DATABASE_VERSION);              
      }
      
      
	/***************** création des table si elles n'existent pas **********************/
      @Override
      public void onCreate(SQLiteDatabase db) {

            db.execSQL("create table IF NOT EXISTS " +TABLE_NAME_3+"("+COLUMN_ID_3+" integer primary key AUTOINCREMENT,"+ 
                  COLUMN_TEXT+" text VARCHAR(400) NOT NULL,"+
                  COLUMN_HOUR+" text VARCHAR(5) NOT NULL)");

            ContentValues contentValues = new ContentValues();      
            contentValues.put(COLUMN_TEXT, "Joyeux anniversaire nom");	
            contentValues.put(COLUMN_HOUR, "09:00");	
              
            db.insert(TABLE_NAME_3, null, contentValues);

            db.execSQL("create table IF NOT EXISTS "+TABLE_NAME_2+"("+COLUMN_ID_2+"  integer primary key AUTOINCREMENT, "+COLUMN_DATE+" text, "+COLUMN_DATE_RAPPEL+" VARCHAR(400))");
                  
            db.execSQL("create table IF NOT EXISTS "+TABLE_NAME+"( "+COLUMN_ID+" integer primary key, "+COLUMN_NAME+" text,"+
                  COLUMN_PHONE+" VARCHAR(400) NOT NULL DEFAULT 'Aucun numéro', "+COLUMN_STATE+" INT(2) NOT NULL DEFAULT 0,"+
                  COLUMN_ID_ANNIVERSARY+" integer, FOREIGN KEY("+COLUMN_ID_ANNIVERSARY+") REFERENCES " +TABLE_NAME_2+"("+COLUMN_ID_2+") )");           	         
      }
      
      
	/***************** drop table et créer la table si changement de version **********************/
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_2);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);   
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_3);

            onCreate(db);
      }


      /***************** insertion d'un anniversaire **********************/
      public void Insert(int id,String date, String name, String num_tel,String date_rappel,int id_date) {	
            
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();    
            contentValues.put("id", id);	
            contentValues.put("name", name);
            contentValues.put("num_tel", num_tel);	
            contentValues.put("id_date", id_date);		
            db.insert(TABLE_NAME, null, contentValues);
            db.close();

            this.callFichier();
      }


      /***************** get toutes les entrées des anniversaires **********************/
      public  JSONArray GetAll() {	
            
           /* ArrayList<String> array_list = new ArrayList<String>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT Id FROM "+TABLE_NAME, null);
            cursor.moveToFirst();
            array_list.add("100");
            while (!cursor.isAfterLast()) {
                  
            array_list.add(
                  Integer.toString(cursor.getInt(0)));
                 
                  cursor.moveToNext();
            }
            cursor.close();
            return array_list;*/
            return null;
      }
	/***************** renvoie un boolean si la colonne existe **********************/
	public boolean rechercherColonne(String colonne){
		
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "PRAGMA table_info(" + TABLE_NAME + ")"; /*retourne les noms des colonnes de la table*/
	    boolean retour = false;
        Cursor c = db.rawQuery(sql, null);
		
		if (c.moveToFirst()){
			
			do {
				
				if(colonne.compareTo(c.getString(1))==0){
					
					retour = true;
				}

			} while(c.moveToNext());
		}
		
		c.close();
		db.close();
		return retour;
	}
	
	/****************************************** Table Utilisateur ********************************************************************/
	
	/***************** insertion d'un anniversaire **********************/
    public void insert(int id,String date, String name, String num_tel,String date_rappel,int id_date) {	
	

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();    
		contentValues.put("id", id);	
	    contentValues.put("name", name);
		contentValues.put("num_tel", num_tel);	
		contentValues.put("id_date", id_date);		
        db.insert(TABLE_NAME, null, contentValues);
		db.close();

		this.callFichier();
    }
	
	
	/***************** update de l'heure **********************/
    public boolean updateHeure (String nouvelle_heure, String heure_remplace) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE "+ TABLE_NAME +" SET "+COLUMN_DATE+" = replace("+COLUMN_DATE+", "+"'"+heure_remplace+"'"+","+"'"+nouvelle_heure+"'"+") WHERE date LIKE '%"+heure_remplace+"'");
		db.close();
		
		db = this.getWritableDatabase();
		db.execSQL("UPDATE "+ TABLE_NAME +" SET "+COLUMN_DATE_RAPPEL+" = replace("+COLUMN_DATE_RAPPEL+", "+"'"+heure_remplace+"'"+","+"'"+nouvelle_heure+"'"+") WHERE date_rappel LIKE '%"+heure_remplace+"'");
		db.close();
		
		this.callFichier();	
        return  true;
    }
	
	
	/***************** update de l'etat **********************/
    public boolean updateEtat (int id,int etat) {
		
        SQLiteDatabase db = this.getWritableDatabase();
		ContentValues data=new ContentValues();
		data.put(COLUMN_STATE,etat);
		db.update(TABLE_NAME, data, COLUMN_ID +" = "+id, null);
	
		this.callFichier();
        return true;
    }
	
	
	/***************** update la nouvelle date d'anniversaire **********************/
    public boolean update (String date, int id, String dateRappel) {
		
        SQLiteDatabase db = this.getWritableDatabase();
		ContentValues data=new ContentValues();
		
		data.put(COLUMN_DATE,date);
		db.update(TABLE_NAME, data, COLUMN_ID +" = "+id, null);
		db.close();
		
		db = this.getWritableDatabase();
		data=new ContentValues();
		
		data.put(COLUMN_DATE_RAPPEL,dateRappel);
		db.update(TABLE_NAME, data, COLUMN_ID +" = "+id, null);
		db.close();
		
		this.callFichier();
		
        return true;
    }
	
	
	/***************** update un nouveau nom et un numéro de téléphone **********************/
	public boolean modif (int id,String name,String num_tel) {
		
        SQLiteDatabase db = this.getWritableDatabase();
		ContentValues data=new ContentValues();
		
		if(name.compareTo("")!=0){
			data.put(COLUMN_NAME,name);
		}
		if(num_tel.compareTo("")!=0){
			data.put(COLUMN_PHONE,num_tel);
		}

		db.update(TABLE_NAME, data, COLUMN_ID +" = "+id, null);
		db.close();
		
		this.callFichier();
        return true;
    }
	/*modif avec un tableau de string
	public boolean modif (String[] whereArgs,String name,String num_tel) {
		
        SQLiteDatabase db = this.getWritableDatabase();
		String idsCSV = TextUtils.join(",", whereArgs);
		ContentValues data=new ContentValues();
		
		if(name.compareTo("")!=0){
			data.put(COLUMN_NAME,name);
		}
		if(num_tel.compareTo("")!=0){
			data.put(COLUMN_NUM_TEL,num_tel);
		}

		db.update(TABLE_NAME, data, COLUMN_ID +" = "+idsCSV, null);
		db.close();
		
		this.callFichier();
        return true;
    }*/
	
	
	/***************** delete un anniversaire **********************/
	public void delete (String[] whereArgs) {		
	
        SQLiteDatabase db = this.getWritableDatabase();
		String idsCSV = TextUtils.join(",", whereArgs);
		db.delete(TABLE_NAME, "id IN (" + idsCSV + ")", null); 
		db.close();
		
		this.callFichier();
	}
	
	
	
	/***************** get etat // a recoder en fonction id date **********************/
	public ArrayList<String> getEtatNum(int id){	
	
		ArrayList<String> array_list = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		
        Cursor cursor = db.rawQuery("SELECT num_tel,etat FROM "+ TABLE_NAME +" WHERE id = "+id, null);		
		cursor.moveToFirst();
		
        while (!cursor.isAfterLast()) {
			
            array_list.add(cursor.getString(0));
			array_list.add(Integer.toString(cursor.getInt(1)));
			cursor.moveToNext();
        }
        cursor.close();
        return array_list;
	}
	
	
	/***************** get le dernier ID de l'anniversaire enregistré **********************/
	public int getLastId(){	
	
		SQLiteDatabase db = this.getWritableDatabase();
		int id = 0;
        Cursor c = db.rawQuery("SELECT MAX(id) FROM "+ TABLE_NAME +"", null);
		
		if (c.moveToFirst()){
			do {
				id = c.getInt(0);
			} while(c.moveToNext());
		}
		c.close();
		db.close();
		return id;
	}
	
	/***************** get toutes les entrées des anniversaires avec les dates **********************/
    public ArrayList<String> getAll2() {	
	
        ArrayList<String> array_list = new ArrayList<String>();
	    SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT date, date_rappel from "+TABLE_NAME_2+" ", null);
	    cursor.moveToFirst();

       while (!cursor.isAfterLast()) {
	

			array_list.add(cursor.getString(0));
			array_list.add(cursor.getString(1));
	
			cursor.moveToNext();
		  }
		cursor.close();
		db.close();
		Test fi=new Test(array_list);
		fi.createJSON();
		return array_list;
    }	



	/****************************************** Table Date ********************************************************************/
    /***************** find a date if exist  **********************/
	public int selectDate (String date) {
		
		int id = 0;
	    SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT id from "+TABLE_NAME_2+" WHERE date = '"+date+"'", null);
	    cursor.moveToFirst();
		
        while (!cursor.isAfterLast()) {
			
            id = cursor.getInt(0);
			cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return id;
    }

    /***************** return tab id same date **********************/
	public ArrayList<String> selectName (int id) {	
		ArrayList<String> list = new ArrayList<String>();
	    SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT "+TABLE_NAME+".name from "+TABLE_NAME+" INNER JOIN "+TABLE_NAME_2+" ON "+TABLE_NAME+".id_date = "+TABLE_NAME_2+".id WHERE "+TABLE_NAME_2+".id="+id+"", null);
	    cursor.moveToFirst();
		
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
			cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

	/***************** insert dates **********************/
	public void insertDate (String date, String date_rappel) {
		
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();    
		contentValues.put("date", date);
		contentValues.put("date_rappel", date_rappel);				
        db.insert(TABLE_NAME_2, null, contentValues);
		db.close();
		this.callFichier();
    }

	/***************** call class fichier pour créer un fhcier json**********************/
	private void callFichier() {
	
		/*Fichier fi = new Fichier(this.GetAll());
		fi.create();*/
	}




    public void insertPatch() {	
	
	    SQLiteDatabase db = this.getWritableDatabase();

	    db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_2);
	    db.execSQL("create table " +TABLE_NAME_2+"(id integer primary key AUTOINCREMENT, date text, date_rappel VARCHAR(400), id_contact integer, FOREIGN KEY(id_contact) REFERENCES " +TABLE_NAME_2+"(id))");
       
        Cursor cursor = db.query(TABLE_NAME,allColumns, null, null, null, null, "name");
        cursor.moveToFirst();
		
        while (!cursor.isAfterLast()) {

			SQLiteDatabase db2 = this.getWritableDatabase();
			String date = cursor.getString(2);
			String date_rappel = cursor.getString(6);

	        ContentValues contentValues = new ContentValues();    	
			contentValues.put("date", date);	
			contentValues.put("date_rappel", date_rappel);
			contentValues.put("id_contact", cursor.getInt(0));		

	        db2.insert(TABLE_NAME_2, null, contentValues);
			db2.close();

			cursor.moveToNext();
        }
        cursor.close();
        db.close();
    }

	/***************** met à jour la db **********************/
    public void patch() {
		
		if(!rechercherColonne("id_message")){
				
			SQLiteDatabase db=this.getWritableDatabase();
			db.execSQL("ALTER TABLE "+TABLE_NAME+" ADD COLUMN 'id_message' INT(2) NOT NULL DEFAULT 0");
			
		}
		
		if(!rechercherColonne("num_tel")){
			
			SQLiteDatabase db=this.getWritableDatabase();
			db.execSQL("ALTER TABLE "+TABLE_NAME+" ADD COLUMN 'num_tel' VARCHAR(400) NOT NULL DEFAULT 'Aucun numéro'");
			db.close();
		}
		
		if(!rechercherColonne("etat")){
			
			SQLiteDatabase db=this.getWritableDatabase();
			db.execSQL("ALTER TABLE "+TABLE_NAME+" ADD COLUMN 'etat' INT(2) NOT NULL DEFAULT 0");
			db.close();
		}
		
    }
	
	/***************** lecture fichier JSON puis insertion dans la base de donnée **********************/
	public String patch2(){

		String buffer_ligne="";
		try{
			
			InputStream flux = new FileInputStream(Environment.getExternalStorageDirectory()+"/alarmes.json"); 
			InputStreamReader lecture = new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			
			String ligne;
			
			
			while ((ligne = buff.readLine())!=null){
				
				buffer_ligne = buffer_ligne+ligne;
			}
			
			buff.close(); 
			lecture.close();
			flux.close();
			
			ContentValues contentValues; 
			SQLiteDatabase db;
			
			String retour ="";
			try {
			
				JSONArray jsonArray = new JSONArray(buffer_ligne);
				 
				for (int i = 0; i < jsonArray.length(); i++) {
					
					contentValues = new ContentValues(); 
					JSONObject item = jsonArray.getJSONObject(i);
					db = this.getWritableDatabase();   
					
					retour = retour +	item.getString("Name");
					contentValues.put("id", item.getString("ID"));	
					contentValues.put("name", item.getString("Name"));
					contentValues.put("date", item.getString("Date"));	
					contentValues.put("num_tel", item.getString("num_tel"));
					contentValues.put("id_message", item.getString("id_message"));
										
					contentValues.put("etat", item.getString("etat"));	
					
					db.insert(TABLE_NAME, null, contentValues);
					db.close();
					
				}
				return retour;
								
			}catch(JSONException e) {
				
				System.err.println("Exception: " + e.getMessage());
				return "Erreur json traitement";					
			}	
		}catch (Exception e){
			
			System.err.println(e.toString());
			return "Erreur du flux d'ouverture de fichier";
		}
	}
  }
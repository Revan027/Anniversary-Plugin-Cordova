package com.revan.anniversaryplugin.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.util.*;  

public abstract class DAO extends SQLiteOpenHelper {

      public static final int DATABASE_VERSION = 1;  
      public static final String DATABASE_NAME = "MemoryAidAnniversary.db";

      /****************************************** Table Utilisateur********************************************************************/
      public static final String TABLE_NAME = "user";
      public static final String COLUMN_ID = "id";
      public static final String COLUMN_NAME = "name";	
      public static final String COLUMN_PHONE = "phone";
      public static final String COLUMN_DATE_ANNIV  = "dateAnniv";	
      public static final String COLUMN_DATE_RAPPEL = "dateRappel";
	public static final String COLUMN_STATE = "state";

      /****************************************** Table Option ********************************************************************/
      public static final String TABLE_NAME_2 = "option";
      public static final String COLUMN_ID_2  = "Id";
      public static final String COLUMN_TEXT  = "text";	
      public static final String COLUMN_HOUR = "hour";
          
      /***************** le constructeur parent verifie et appelle si besoin les fonctions onCreate et onUpgrade **********************/
      public DAO(Context context ) {	         
            super(context, DATABASE_NAME, null, DATABASE_VERSION);              
      }
      
      @Override
      public void onOpen(SQLiteDatabase db){
          super.onOpen(db);

          db.execSQL("PRAGMA foreign_keys=ON;");//activation des foreign key sur sql lite
      }

	/***************** création des table si elles n'existent pas **********************/
      @Override
      public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table IF NOT EXISTS " +TABLE_NAME_2+"("+COLUMN_ID_2+" integer primary key AUTOINCREMENT,"+ 
                  COLUMN_TEXT+" text VARCHAR(400) NOT NULL,"+
                  COLUMN_HOUR+" text VARCHAR(5) NOT NULL)");

            ContentValues contentValues = new ContentValues();      
            contentValues.put(COLUMN_TEXT, "Joyeux anniversaire nom");	
            contentValues.put(COLUMN_HOUR, "09:00");	
              
            db.insert(TABLE_NAME_2, null, contentValues);

            db.execSQL("create table IF NOT EXISTS "+TABLE_NAME+"( "+COLUMN_ID+" integer primary key, "+COLUMN_NAME+" text,"+
                  COLUMN_PHONE+" VARCHAR(400) NOT NULL DEFAULT 'Aucun numéro',"+COLUMN_DATE_ANNIV+" DATE, "+COLUMN_DATE_RAPPEL+" DATE, "+COLUMN_STATE+" INT(2) NOT NULL DEFAULT 0)");           	                        	         
      }
          
	/***************** drop table et créer la table si changement de version **********************/
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_2);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);   
            
            onCreate(db);
      }					
  }
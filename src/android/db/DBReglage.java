package com.revan.anniversaryplugin.db;

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

public class DBReglage extends SQLiteOpenHelper {

      private static final int DATABASE_VERSION = 1;
      public static final String DATABASE_NAME = "DBReglage.db";
      public static final String TABLE_NAME = "Reglage";
      public static final String COLUMN_ID = "id";
      public static final String COLUMN_TEXTE = "texte";
      public static final String COLUMN_HEURE = "heure";
 
    String[] allColumns = {COLUMN_ID,COLUMN_TEXTE,COLUMN_HEURE};
	
	/***************** le constructeur parent verifie et appelle si besoin les fonctions onCreate et onUpgrade **********************/
    public DBReglage(Context context) {
		
        super(context, DATABASE_NAME, null, DATABASE_VERSION); 	
    }
	
	/***************** création table si elle n'existe pas **********************/
    @Override
    public void onCreate(SQLiteDatabase db) {
		
        db.execSQL("create table " +TABLE_NAME+"(id integer primary key AUTOINCREMENT, texte text VARCHAR(400) NOT NULL DEFAULT 'Joyeux anniversaire nom', heure text VARCHAR(5) NOT NULL DEFAULT '09:00')");
		
		insertion("Joyeux anniversaire nom","09:00",db);
	}
	
	
	/***************** drop table et créer la table ensuite changement de version **********************/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);
		
    }

	/***************** insertion des colonnes à la création de la table **********************/
    public boolean insertion(String texte, String heure, SQLiteDatabase db) {
		
        ContentValues contentValues = new ContentValues();      
	    contentValues.put("texte", texte);	
		contentValues.put("heure", heure);	
		
        db.insert(TABLE_NAME, null, contentValues);

        return true;
    }

	
	/***************** update texte sms **********************/
    public boolean update (String texte) {
		
        SQLiteDatabase db = this.getWritableDatabase();
		ContentValues data=new ContentValues();
		data.put(COLUMN_TEXTE,texte);
		db.update(TABLE_NAME, data, COLUMN_ID +" = 1", null);
		db.close();
		
        return true;
		
    }
	
	/***************** update heure rappels **********************/
    public boolean updateHeure (String heure) {
		
        SQLiteDatabase db = this.getWritableDatabase();
		ContentValues data=new ContentValues();
		data.put(COLUMN_HEURE,heure);
		db.update(TABLE_NAME, data, COLUMN_ID +" = 1", null);
		db.close();
		
        return true;
		
    }
	
	/***************** retourne l'heure des rappels **********************/
    public String getHeure () {
	
	    SQLiteDatabase db = this.getWritableDatabase();
		String heure = "";
        Cursor c = db.rawQuery("SELECT "+COLUMN_HEURE+" FROM "+ TABLE_NAME +"", null);
		
		if (c.moveToFirst()){
			
			do {
				
				heure = c.getString(0);
				
			} while(c.moveToNext());
			
		}
		
		c.close();
		db.close();
		
		return heure;
    }
	
	
	/***************** get texte **********************/
	public String getTexte(){
		
		SQLiteDatabase db = this.getWritableDatabase();
		String texte = "";
        Cursor c = db.rawQuery("SELECT "+COLUMN_TEXTE+" FROM "+ TABLE_NAME +"", null);
		
		if (c.moveToFirst()){
			do {
				texte = c.getString(0);
			} while(c.moveToNext());
		}
		c.close();
		db.close();
		return texte;
	}

}
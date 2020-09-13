package com.uniclau.alarmplugin.lib;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import android.os.Environment;
import java.util.*;  



/*****************  **********************/
public class Fichier {
	
	private String location=Environment.getExternalStorageDirectory()+"/alarmes.json";
	private String location_absolute=Environment.getExternalStorageDirectory().getAbsolutePath();
	private ArrayList<String> list;

	public Fichier(ArrayList<String> list){ 
	
		this.list=list;
    } 

	public String get_location_absolute(){
		return 	this.location_absolute;
	}
	
	public boolean create(){
		
		File file = new File(this.location);
		try {	
			if(!file.exists()){
				file.createNewFile();
				createJSON();
				return true;				
			}else{
				file.delete();
				file.createNewFile();
				createJSON();
				return true;	
			}			
		}catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			return false;
		}			
	}
	
	public void createJSON(){
		
		JSONArray obj = new JSONArray();
		Iterator itr=this.list.iterator();
		
		while(itr.hasNext()){  
		
			try {	
				JSONObject list1 = new JSONObject();
				list1.put("id", itr.next());
				list1.put("name", itr.next());
				list1.put("num_tel",itr.next());
				list1.put("etat",itr.next());
				list1.put("id_message",itr.next());
				list1.put("id_date",itr.next());
				list1.put("date",itr.next());
				list1.put("date_rappel",itr.next());
				obj.put(list1);
				
			}catch(JSONException e) {
				System.err.println("Exception: " + e.getMessage());
			}
		}          
		// Création du fichier de sortie
		FileWriter fs = null;
		try {
			fs = new FileWriter(this.location);
			fs.write(obj.toString());
			fs.flush();
			fs.close();
		} catch(IOException e) {
			System.err.println("Exception: " + e.getMessage());
		}
	}
}
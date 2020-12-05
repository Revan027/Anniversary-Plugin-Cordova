package com.revan.anniversaryplugin.lib;

import java.util.*;  

public enum EnumNotification {

      BIRTHDAY_REMINDER("RAPPEL D'ANNIVERSAIRE"), 
      BIRTHDAY("ANNIVERSAIRE"), 
      BIRTHDAY_REMINDER_CONTENT("N'oubliez pas l'anniversaire de %name% le %date%"),
      BIRTHDAY_CONTENT("C'est l'anniversaire de %name% (%date%)");;

      private String libelle; 
  
      public String getLibelle() 
      { 
            return this.libelle; 
      } 

      private EnumNotification(String libelle) {
            this.libelle = libelle;
      }

      public String replaceContent(Map<String, String> map) 
      {                  
            for (Map.Entry<String,String> entry : map.entrySet()) {
                 this.libelle = this.libelle.replaceAll("\\%"+entry.getKey()+"%", entry.getValue());
            } 

            return this.libelle;
      } 
}

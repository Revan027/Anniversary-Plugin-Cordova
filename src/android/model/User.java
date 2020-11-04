package com.revan.anniversaryplugin.model;

import java.util.Date;
import android.content.Context;

public final class User {       
      private String Name;
      private String Phone;
      private Date DateAnniv;
      private Date DateRappel;
      private boolean State;

      public User(String name,String phone,Date dateAnniv,Date dateRappel,boolean state){
            this.Name = name;
            this.Phone = phone;
            this.DateAnniv = dateAnniv;
            this.DateRappel = dateRappel;
            this.State = state;
      }

      public User(){
      }

      public String getName(){
            return this.Name;
      }

      public String getPhone(){
            return this.Phone;
      }

      public Date getDateAnniv(){
            return this.DateAnniv;
      }

      public Date getDateRappel(){
            return this.DateRappel;
      }

      public boolean getState(){
            return this.State;
      }

      public void setName(String name){
            this.Name = name;
      }

      public void setPhone(String phone){
            this.Phone = phone;
      }

      public void setState(boolean state){
            this.State = state;
      }
}
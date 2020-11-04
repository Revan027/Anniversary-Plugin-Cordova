package com.revan.anniversaryplugin.model;

import java.util.Date;
import android.content.Context;

public final class Option {       
      private String Text;
      private String LastName;
      private String Hour;

      public Option(String text,String lastName,String hour){
            this.Text = text;
            this.LastName = lastName;
            this.Hour = hour;
      }

      public Option(){
      }

      public String getText(){
            return this.Text;
      }

      public String getLastName(){
            return this.LastName;
      }

      public String getHour(){
            return this.Hour;
      }

      public void setText(String text){
            this.Text = text;
      }

      public void setLastName(String lastName){
            this.LastName = lastName;
      }

      public void setHour(String hour){
            this.Hour = hour;
      }
}
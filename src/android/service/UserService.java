package com.revan.anniversaryplugin.model;

import com.revan.anniversaryplugin.db.*;
import java.util.Date;
import android.content.Context;

public final class UserService {       
      private String Name;
      private String Phone;
      private Date DateAnniv;
      private Date DateRappel;
      private boolean State;
      private UserRepository UserRepository;

      public UserService(Context context){
            this.UserRepository = new UserRepository(context);
      }
    
      public long add(User user){
            return this.UserRepository.add(user);
      }

      public boolean update(User user, int id){
            return this.UserRepository.update(user, id);
      }

      public boolean delete(String[] tabId){
            return this.UserRepository.delete(tabId);
      }

      public String getAllInJSON(){
            return this.UserRepository.getAll().toString();
      }

      public String searchMonthAnniv(String dateSearch){
            return this.UserRepository.getByDate(dateSearch).toString();
      }

      public User getById(int id){
            return this.UserRepository.getById(id);
      }
}
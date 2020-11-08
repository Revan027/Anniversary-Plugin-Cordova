package com.revan.anniversaryplugin.service;

import com.revan.anniversaryplugin.model.*;
import com.revan.anniversaryplugin.db.*;
import java.util.Date;
import android.content.Context;
import android.database.Cursor;

public final class UserService {      

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
            return this.UserRepository.getByMonthAnniv(dateSearch).toString();
      }

      public Cursor searchUserAnniv(String dateSearch){
            return this.UserRepository.getByDateAnniv(dateSearch);
      }

      public Cursor searchUserAnnivRappel(String dateSearch){
            return this.UserRepository.getByDateRappel(dateSearch);
      }

      public User getById(int id){
            return this.UserRepository.getById(id);
      }
}
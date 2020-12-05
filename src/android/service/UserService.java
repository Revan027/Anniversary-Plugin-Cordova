package com.revan.anniversaryplugin.service;

import com.revan.anniversaryplugin.model.*;
import com.revan.anniversaryplugin.db.*;
import com.revan.anniversaryplugin.lib.*;
import java.util.Date;
import android.content.Context;
import android.database.Cursor;

public final class UserService {      

      private UserRepository UserRepository;

      public UserService(Context context){
            this.UserRepository = new UserRepository(context);
      }
    
      public int getUsersNbr(){
            return this.UserRepository.countUsers();
      }

      public int countMonthAnniv(String dateSearch){
            return this.UserRepository.countMonthAnniv(dateSearch);
      }

      public int countUserAnniv(String dateSearch){
            return this.UserRepository.countUserAnniv(dateSearch);
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

      public String getAllInJSON(String limit, String offset){
            return this.UserRepository.getAll(limit, offset).toString();
      }

      public String searchMonthAnniv(String dateSearch){
            return this.UserRepository.getByMonthAnniv(dateSearch).toString();
      }

      public User[] searchUserAnniv(String dateSearch){
            return this.UserRepository.getByDateAnniv(dateSearch);
      }

      public User[] searchUserAnnivRappel(String dateSearch){
            return this.UserRepository.getByDateRappel(dateSearch);
      }

      public User getById(int id){
            return this.UserRepository.getById(id);
      }
      
      public void handleAnniv(User user){
            Date dateNow = new Date();
            Date dateAnniv = user.getDateAnniv();

            if (dateNow.compareTo(dateAnniv) >= 0){
                  Date newDate = DateOperation.addYear(dateAnniv, "dd-MM-yyyy");
                  user.setDateAnniv(newDate); 
                  user.setDateRappel(DateOperation.creationRappel(newDate));          
            }else{
                  user.setDateRappel(DateOperation.creationRappel(dateAnniv));
            }          
           this.update(user, user.getId());
      }
}
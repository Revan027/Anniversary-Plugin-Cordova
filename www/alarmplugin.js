var alarm = {
      init: function(rien, successCallback, errorCallback) {
             cordova.exec(
                 successCallback,
                 errorCallback,
                 "AlarmPlugin",
                 "Init",
                 [rien]
             );
      },
      phoneContacts: function(rien,successCallback, errorCallback) {       
            cordova.exec(
                successCallback,
                errorCallback,
                "AlarmPlugin",
                "PhoneContacts",
                [rien]
            );
      },
      getUsers: function(rien, successCallback, errorCallback) {
            cordova.exec(
                  successCallback,
                  errorCallback,
                  "AlarmPlugin",
                  "GetUsers",
                  [rien]
            );
      },
      addUser: function(alarmDate,name,phone, successCallback, errorCallback) {
            cordova.exec(
                  successCallback,
                  errorCallback,
                  "AlarmPlugin",
                  "AddUser",
                  [alarmDate,name,phone]
            );
      },
      getOptions: function(rien,successCallback, errorCallback) { 
            cordova.exec(
                successCallback,
                errorCallback,
                "AlarmPlugin",
                "GetOptions",
                [rien]
            );
      },
      updateOptions: function(sms,hour,successCallback, errorCallback) {  
            cordova.exec(
                  successCallback,
                  errorCallback,
                  "AlarmPlugin",
                  "UpdateOptions",
                  [sms,hour]
            );
      },
	deleteUser: function(tabId, successCallback, errorCallback) {     
        cordova.exec(
            successCallback,
            errorCallback,
            "AlarmPlugin",
            "DeleteUser",
            [tabId]
        );
      },
      updateUserState: function(id,data, successCallback, errorCallback) {     
            cordova.exec(
            successCallback,
            errorCallback,
            "AlarmPlugin",
            "UpdateUserState",
            [id,data]
            );
      },
      searchDate: function(dateSearch,successCallback, errorCallback) { 
            cordova.exec(
                successCallback,
                errorCallback,
                "AlarmPlugin",
                "SearchDate",
                [dateSearch]
            );
      },
	modif: function(tabId,new_name,new_num_tel, successCallback, errorCallback) {
       
        cordova.exec(
            successCallback,
            errorCallback,
            "AlarmPlugin",
            "programModif",
            [tabId,new_name,new_num_tel]
        );
    },
   
    modifTextSms: function(texte,successCallback, errorCallback) {
       
        cordova.exec(
            successCallback,
            errorCallback,
            "AlarmPlugin",
            "modifTextSms",
            [texte]
        );
    },
    
    modifEtat: function(id,etat,successCallback, errorCallback) {
       
        cordova.exec(
            successCallback,
            errorCallback,
            "AlarmPlugin",
            "modifEtat",
            [id,etat]
        );
    }, 
	modifHeure: function(heure,successCallback, errorCallback) {
       
        cordova.exec(
            successCallback,
            errorCallback,
            "AlarmPlugin",
            "modifHeure",
            [heure]
        );
    }
	
};
module.exports = alarm;

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
      getUsersNbr: function(rien,successCallback, errorCallback) {
            cordova.exec(
                  successCallback,
                  errorCallback,
                  "AlarmPlugin",
                  "getUsersNbr",
                  [rien]
            );
      },
      getUsers: function(limit,offset, successCallback, errorCallback) {
            cordova.exec(
                  successCallback,
                  errorCallback,
                  "AlarmPlugin",
                  "GetUsers",
                  [limit,offset]
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
      deleteUser: function(tabId, successCallback, errorCallback) {     
            cordova.exec(
                successCallback,
                errorCallback,
                "AlarmPlugin",
                "DeleteUser",
                [tabId]
            );
      },
      updateUser: function(id,name,phone, successCallback, errorCallback) {
            cordova.exec(
                  successCallback,
                  errorCallback,
                  "AlarmPlugin",
                  "UpdateUser",
                  [id,name,phone]
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
      searchDate: function(dateSearch,successCallback, errorCallback) { 
            cordova.exec(
                successCallback,
                errorCallback,
                "AlarmPlugin",
                "SearchDate",
                [dateSearch]
            );
      },
};
module.exports = alarm;
package com.uniclau.alarmplugin.lib;

public final class SingletonServiceManager {

    private static SingletonServiceManager sSoleInstance;
 public static boolean isMyServiceRunning = false;
    private SingletonServiceManager(){}  //private constructor.

    public static SingletonServiceManager getInstance(){
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new SingletonServiceManager();
        }

        return sSoleInstance;
    }
}

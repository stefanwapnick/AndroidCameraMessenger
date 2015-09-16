package com.example.stefan.cameramessengerapp.services;

import android.util.Log;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;

public class Module{

    /**
     *  We must instanciate all otto service classes here in order to call register in the constructor method
     *  If not, then no register event called and Otto cannot find @Subscribe method
     */
    public static void register(CameraMessengerApplication application){
        Log.i("Module", "In Memory register call");
        new InMemoryAccountService(application);
    }
}



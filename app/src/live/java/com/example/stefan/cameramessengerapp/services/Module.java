package com.example.stefan.cameramessengerapp.services;

import android.util.Log;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;

public class Module{

    public static void register(CameraMessengerApplication application){
        Log.i("Module", "Live register file called");
    }
}



package com.example.stefan.cameramessengerapp.infrastructure;

import android.app.Application;

import com.squareup.otto.Bus;

public class CameraMessengerApplication extends Application {

    private Auth auth;
    private Bus bus;

    public CameraMessengerApplication(){
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new Auth(this);
    }

    public Auth getAuth() {
        return auth;
    }

    public Bus getBus() { return bus; }
}

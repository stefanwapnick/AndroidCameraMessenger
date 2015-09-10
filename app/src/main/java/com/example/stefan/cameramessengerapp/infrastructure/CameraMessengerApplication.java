package com.example.stefan.cameramessengerapp.infrastructure;

import android.app.Application;

public class CameraMessengerApplication extends Application {

    private Auth auth;

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new Auth(this);
    }

    public Auth getAuth() {
        return auth;
    }
}

package com.example.stefan.cameramessengerapp.infrastructure;

import android.app.Application;
import android.net.Uri;

import com.example.stefan.cameramessengerapp.services.Module;
import com.squareup.otto.Bus;

public class CameraMessengerApplication extends Application {

    public static final Uri API_ENDPOINT = Uri.parse("http://yora-playground.3dbuzz.com");
    public static final String STUDENT_TOKEN = "601cfcc4208d44808ba5b31f67e61d73";

    private Auth auth;
    private Bus bus;

    public CameraMessengerApplication(){
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new Auth(this);
        Module.register(this);
    }

    public Auth getAuth() {
        return auth;
    }

    public Bus getBus() { return bus; }
}

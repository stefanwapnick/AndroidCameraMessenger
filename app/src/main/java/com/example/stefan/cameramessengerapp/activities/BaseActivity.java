package com.example.stefan.cameramessengerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;

public class BaseActivity extends AppCompatActivity {

    protected CameraMessengerApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (CameraMessengerApplication)getApplication();

    }


}

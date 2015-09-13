package com.example.stefan.cameramessengerapp.activities;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;
import com.example.stefan.cameramessengerapp.views.NavDrawer;

public class BaseActivity extends AppCompatActivity {

    protected CameraMessengerApplication application;
    protected Toolbar toolbar;
    protected NavDrawer navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (CameraMessengerApplication)getApplication();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId){
        super.setContentView(layoutResId);

        // Get toolbar if id exists in current layout
        toolbar = (Toolbar)findViewById(R.id.include_toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    protected void setToolbarTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    protected void setNavDrawer(NavDrawer navDrawer){
        this.navDrawer = navDrawer;
        this.navDrawer.create();
    }

    public Toolbar getToolbar(){
        return this.toolbar;
    }
}

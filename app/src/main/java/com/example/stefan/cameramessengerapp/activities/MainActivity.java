package com.example.stefan.cameramessengerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.views.MainNavDrawer;

public class MainActivity extends BaseAuthenticatedActivity {

    @Override
    protected void onCreateAuth(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);     // call base class setContentViw

        setToolbarTitle("Inbox");
        setNavDrawer(new MainNavDrawer(this));
    }

}

package com.example.stefan.cameramessengerapp.activities;

import android.os.Bundle;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.views.NavDrawer;

public class ProfileActivity extends BaseAuthenticatedActivity{
    @Override
    protected void onCreateAuth(Bundle savedState) {
        setContentView(R.layout.activity_profile);
        setNavDrawer(new NavDrawer(this));
    }
}

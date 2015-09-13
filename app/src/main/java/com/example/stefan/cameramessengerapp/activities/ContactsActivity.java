package com.example.stefan.cameramessengerapp.activities;

import android.os.Bundle;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.views.MainNavDrawer;

/**
 * Created by Stefan on 2015-09-13.
 */
public class ContactsActivity extends BaseAuthenticatedActivity {
    @Override
    protected void onCreateAuth(Bundle savedState) {
        setContentView(R.layout.activity_contacts);
        setNavDrawer(new MainNavDrawer(this));
    }
}

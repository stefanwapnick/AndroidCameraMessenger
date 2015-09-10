package com.example.stefan.cameramessengerapp.activities;

import android.content.Intent;
import android.os.Bundle;

public abstract class BaseAuthenticatedActivity extends BaseActivity {

    @Override
    protected final void onCreate(Bundle savedState){
        super.onCreate(savedState);

        // If not logged in, shoot user to Log In activity
        if(!application.getAuth().getUser().isLoggedIn()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        onAuthCreate(savedState);
    }

    /**
     * Substitute for onCreate method. This onCreate method will only run if user is authenticated.
     * If we do not use this pattern, then subclasses will override onCreate method.
     * But if user is then not logged in then no point in sub classes running onCreate (waste of initializations)
     * Thus, here we prevent subclasses from overriding onCreate and instead only let them implement onAuthCreate which will only be run if authenticated.
     */
    protected abstract void onAuthCreate(Bundle savedState);

}

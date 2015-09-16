package com.example.stefan.cameramessengerapp.infrastructure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.stefan.cameramessengerapp.activities.LoginActivity;

/**
 * User authentication class
 */
public class Auth {

    private static final String AUTH_PREFERENCES = "AUTH_PREFERENCES";
    private static final String AUTH_PREFERENCES_TOKEN = "AUTH_PREFERENCES_TOKEN";

    private final Context context;
    private final SharedPreferences preferences;
    private String authToken;
    private User user;

    public Auth(Context context) {
        this.context = context;
        user = new User();

        // Get auth preferences collection of shared preferences
        preferences = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);

        // Get value stored at key location. If you can't find it, return null
        authToken = preferences.getString(AUTH_PREFERENCES_TOKEN, null);
    }

    public User getUser() {
        return user;
    }

    public String getAuthToken(){
        return authToken;
    }

    public Boolean hasAuthToken(){
        return authToken != null && !authToken.isEmpty();
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AUTH_PREFERENCES_TOKEN, authToken);
        editor.commit();
    }

    public void logout(){
        setAuthToken(null);

        Intent loginIntent = new Intent(context, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(loginIntent);
    }
}

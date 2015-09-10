package com.example.stefan.cameramessengerapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.stefan.cameramessengerapp.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        findViewById(R.id.activity_login_loginButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        application.getAuth().getUser().setIsLoggedIn(true);
        startActivity(new Intent(this, LoginNarrowActivity.class));
        finish();
    }
}

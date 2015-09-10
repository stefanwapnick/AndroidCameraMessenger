package com.example.stefan.cameramessengerapp.activities;

import android.os.Bundle;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.fragments.LoginFragment;

public class LoginNarrowActivity extends BaseActivity implements LoginFragment.CallbackActivity {

    @Override
    protected void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.activity_login_narrow);
    }

    @Override
    public void onLoggedIn() {
        setResult(RESULT_OK);
        finish();
    }
}

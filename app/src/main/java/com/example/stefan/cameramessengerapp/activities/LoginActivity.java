package com.example.stefan.cameramessengerapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.stefan.cameramessengerapp.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final int REQUEST_NARROW_LOGIN = 1;
    private static final int REQUEST_REGISTER = 2;
    private static final int REQUEST_EXTERNAL_LOGIN = 3;
    private View loginButton;
    private View registerButton;
    private View googleLoginButton;
    private View facebookLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        // Reference layout buttons
        loginButton = findViewById(R.id.activity_login_loginButton);
        googleLoginButton = findViewById(R.id.activity_login_googleLogin);
        facebookLoginButton = findViewById(R.id.activity_login_facebookLogin);

        if(loginButton != null){
            loginButton.setOnClickListener(this);
        }

        registerButton = findViewById(R.id.activity_login_registerButton);
        registerButton.setOnClickListener(this);
        googleLoginButton.setOnClickListener(this);
        facebookLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == loginButton) {
            startActivityForResult(new Intent(this, LoginNarrowActivity.class), REQUEST_NARROW_LOGIN);
        }else if(view == registerButton){
            startActivityForResult(new Intent(this, RegisterActivity.class), REQUEST_REGISTER);
        }else if(view == facebookLoginButton){
            doExternalLogin("Facebook");
        }else if(view == googleLoginButton){
            doExternalLogin("Google"); 
        }
    }

    private void doExternalLogin(String externalProvider) {
        Intent intent  = new Intent(this, ExternalLoginActivity.class);
        intent.putExtra(ExternalLoginActivity.EXTRA_EXTERNAL_SERVICE, externalProvider); 
        startActivityForResult(intent, REQUEST_EXTERNAL_LOGIN);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_NARROW_LOGIN ||
                requestCode == REQUEST_REGISTER ||
                requestCode == REQUEST_EXTERNAL_LOGIN){
            finishLogin();
        }
    }

    private void finishLogin(){
        application.getAuth().getUser().setIsLoggedIn(true);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}

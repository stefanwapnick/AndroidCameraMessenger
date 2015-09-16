package com.example.stefan.cameramessengerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.services.Account;
import com.squareup.otto.Subscribe;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText userNameText;
    private EditText passwordText;
    private EditText emailText;
    private Button registerButton;
    private View progressBar;

    private String defaultRegisterButtonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameText = (EditText)findViewById(R.id.activity_register_email);
        passwordText = (EditText)findViewById(R.id.activity_register_password);
        emailText = (EditText)findViewById(R.id.activity_register_email);
        registerButton = (Button)findViewById(R.id.activity_register_button);
        progressBar = findViewById(R.id.activity_register_progressBar);

        registerButton.setOnClickListener(this);
        progressBar.setVisibility(View.GONE);

        defaultRegisterButtonText = registerButton.getText().toString();
    }

    @Override
    public void onClick(View view){
        if(view == registerButton){

            progressBar.setVisibility(View.VISIBLE);
            registerButton.setText("");
            registerButton.setEnabled(false);
            userNameText.setEnabled(false);
            passwordText.setEnabled(false);
            emailText.setEnabled(false);

            bus.post(new Account.RegisterRequest(
                    userNameText.getText().toString(),
                    emailText.getText().toString(),
                    passwordText.getText().toString()));

        }
    }

    @Subscribe
    public void registerResponse(Account.RegisterResponse response){
        onUserResponse(response);
    }

    @Subscribe
    public void externalRegisterResponse(Account.RegisterWithExternalTokenResponse response){
        onUserResponse(response);
    }


    private void onUserResponse(Account.UserResponse response){

        if(response.isSuccess()){
            setResult(RESULT_OK);
            finish();
            return;
        }

        response.showErrorToast(this);
        userNameText.setError(response.getPropertyError("userName"));
        passwordText.setError(response.getPropertyError("password"));
        emailText.setError("email");

        registerButton.setEnabled(true);
        emailText.setEnabled(true);
        passwordText.setEnabled(true);
        userNameText.setEnabled(true);

        progressBar.setVisibility(View.GONE);
        registerButton.setText(defaultRegisterButtonText);

    }

}

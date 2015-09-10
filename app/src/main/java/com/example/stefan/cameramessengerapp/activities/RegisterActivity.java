package com.example.stefan.cameramessengerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.stefan.cameramessengerapp.R;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText userNameText;
    private EditText passwordText;
    private EditText emailText;
    private Button registerButton;
    private View progressBar;

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
    }

    @Override
    public void onClick(View view){
        if(view == registerButton){
            setResult(RESULT_OK);
        }
    }

}

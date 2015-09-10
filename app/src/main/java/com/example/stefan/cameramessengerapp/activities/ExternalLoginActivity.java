package com.example.stefan.cameramessengerapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.example.stefan.cameramessengerapp.R;

public class ExternalLoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_EXTERNAL_SERVICE = "EXTRA_EXTERNAL_SERVICE";

    private View testButton;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_login);

        testButton = findViewById(R.id.activity_external_login_testButton);
        webView = (WebView)findViewById(R.id.activity_external_login_webView);
    }

    @Override
    public void onClick(View view) {
        if(view == testButton){
            setResult(RESULT_OK);
            finish();
        }
    }
}

package com.example.stefan.cameramessengerapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.services.Account;
import com.squareup.otto.Subscribe;

public class LoginFragment extends BaseFragment implements View.OnClickListener{

    private String defaultLoginButonText;
    private CallbackActivity callbacks;

    private View progressBar;
    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedState){
        super.onCreateView(inflater, root, savedState);

        View view = inflater.inflate(R.layout.fragment_login, root, false);
        loginButton = (Button)view.findViewById(R.id.fragment_login_loginButton);
        usernameText = (EditText)view.findViewById(R.id.fragment_login_userName);
        passwordText = (EditText)view.findViewById(R.id.fragment_login_password);
        progressBar = view.findViewById(R.id.fragment_login_progressBar);

        loginButton.setOnClickListener(this);

        defaultLoginButonText = loginButton.getText().toString();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (CallbackActivity)activity;
    }

    @Override
    public  void onDetach(){
        super.onDetach();
        callbacks = null;
    }

    public void onClick(View v){
        if(v.getId() == R.id.fragment_login_loginButton){

            progressBar.setVisibility(View.VISIBLE);
            loginButton.setText("");
            usernameText.setEnabled(false);
            passwordText.setEnabled(false);
            loginButton.setEnabled(false);

            bus.post(new Account.LoginUserRequest(
                    usernameText.getText().toString(),
                    passwordText.getText().toString()));
        }
    }

    public interface CallbackActivity{
        void onLoggedIn();
    }


    @Subscribe
    public void onLoginWithUsername(Account.LoginUserResponse response){

        response.showErrorToast(getActivity());

        if(response.isSuccess()){
            callbacks.onLoggedIn();
            return;
        }

        usernameText.setError(response.getPropertyError("username"));
        usernameText.setEnabled(true);

        passwordText.setError(response.getPropertyError("password"));
        passwordText.setEnabled(true);

        loginButton.setText(defaultLoginButonText);
        loginButton.setEnabled(true);

        progressBar.setVisibility(View.GONE);
    }
}

package com.example.stefan.cameramessengerapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stefan.cameramessengerapp.R;

public class LoginFragment extends BaseFragment implements View.OnClickListener{

    private CallbackActivity callbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root, Bundle savedState){
        View view = inflater.inflate(R.layout.fragment_login, root, false);

        view.findViewById(R.id.fragment_login_loginButton).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.fragment_login_loginButton){
            callbacks.onLoggedIn();
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        callbacks = (CallbackActivity)context;
    }

    @Override
    public  void onDetach(){
        super.onDetach();
        callbacks = null;
    }

    public interface CallbackActivity{
        void onLoggedIn();
    }

}

package com.example.stefan.cameramessengerapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stefan.cameramessengerapp.R;

public class LoginFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup root, Bundle savedState){

        View view = inflator.inflate(R.layout.fragment_login, root, false);
        return view;
    }

}

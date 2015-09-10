package com.example.stefan.cameramessengerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;

public class BaseFragment extends Fragment {

    protected CameraMessengerApplication application;

    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);

        // Get parent activity, then get parent application
        application = (CameraMessengerApplication)getActivity().getApplication();
    }


}

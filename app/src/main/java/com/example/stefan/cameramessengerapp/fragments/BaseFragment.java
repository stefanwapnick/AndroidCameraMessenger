package com.example.stefan.cameramessengerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;
import com.squareup.otto.Bus;

public class BaseFragment extends Fragment {

    protected CameraMessengerApplication application;
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);

        // Get parent activity, then get parent application
        application = (CameraMessengerApplication)getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        bus.unregister(this);
    }


}

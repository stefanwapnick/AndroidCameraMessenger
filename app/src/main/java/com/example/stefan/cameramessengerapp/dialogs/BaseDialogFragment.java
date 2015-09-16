package com.example.stefan.cameramessengerapp.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;
import com.squareup.otto.Bus;

public class BaseDialogFragment extends DialogFragment{
    protected CameraMessengerApplication application;
    protected Bus bus;


    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);
        application = (CameraMessengerApplication)getActivity().getApplication();

        bus = application.getBus();
        bus.register(this);
    }

    @Override
    public  void onDestroy(){
        super.onDestroy();
        bus.unregister(this);
    }


}

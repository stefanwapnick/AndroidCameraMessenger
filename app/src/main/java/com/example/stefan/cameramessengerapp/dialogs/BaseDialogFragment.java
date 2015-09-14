package com.example.stefan.cameramessengerapp.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;

public class BaseDialogFragment extends DialogFragment{
    protected CameraMessengerApplication application;

    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);
        application = (CameraMessengerApplication)getActivity().getApplication();
    }


}

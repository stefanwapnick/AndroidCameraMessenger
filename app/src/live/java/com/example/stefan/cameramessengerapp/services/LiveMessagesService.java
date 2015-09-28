package com.example.stefan.cameramessengerapp.services;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;

public class LiveMessagesService extends BaseLiveService {

    public LiveMessagesService(WebService api, CameraMessengerApplication application) {
        super(api, application);
    }
}

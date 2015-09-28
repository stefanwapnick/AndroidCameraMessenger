package com.example.stefan.cameramessengerapp.services;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;
import com.squareup.otto.Bus;

public abstract class BaseLiveService {

    protected final Bus bus;
    protected final WebService api;
    protected final CameraMessengerApplication application;

    public BaseLiveService(WebService api, CameraMessengerApplication application) {
        this.api = api;
        this.application = application;
        bus = application.getBus();
        bus.register(this);
    }
}

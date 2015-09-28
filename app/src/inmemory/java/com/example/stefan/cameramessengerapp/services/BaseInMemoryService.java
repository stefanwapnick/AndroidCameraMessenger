package com.example.stefan.cameramessengerapp.services;

import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;
import com.squareup.otto.Bus;

import java.util.Random;
import android.os.Handler;

public abstract class BaseInMemoryService {

    protected final Bus bus;
    protected final CameraMessengerApplication application;
    protected final Handler handler;
    protected final Random random;

    protected BaseInMemoryService(CameraMessengerApplication application){
        this.application = application;
        bus = application.getBus();
        handler = new Handler();
        random = new Random();
        bus.register(this);
    }

    protected void invokeDelayed(Runnable runnable, long msMin, long msMax){
        if(msMin > msMax)
            throw new IllegalArgumentException("Min must be smaller than max");

        long delay = (long)(random.nextDouble()) * (msMax - msMin) + msMin;

        // Delay thread run by by delay amount
        handler.postDelayed(runnable, delay);
    }

    protected void postDelayed(final Object post, long msMin, long msMax){

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                bus.post(post);
            }
        }, msMin, msMax);

    }
    protected void postDelayed(Object event, long ms){
        postDelayed(event, ms, ms);
    }
    protected void postDelayed(Object event){
        postDelayed(event, 500, 700);
    }


}

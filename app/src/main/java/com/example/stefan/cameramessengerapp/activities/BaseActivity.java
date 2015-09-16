package com.example.stefan.cameramessengerapp.activities;

import android.animation.Animator;
import android.app.Application;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;
import com.example.stefan.cameramessengerapp.views.NavDrawer;
import com.squareup.otto.Bus;

public class BaseActivity extends AppCompatActivity {

    protected CameraMessengerApplication application;
    protected Toolbar toolbar;
    protected NavDrawer navDrawer;
    protected Bus bus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (CameraMessengerApplication)getApplication();
        bus = application.getBus();

        bus.register(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId){
        super.setContentView(layoutResId);

        // Get toolbar if id exists in current layout
        toolbar = (Toolbar)findViewById(R.id.include_toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    protected void setToolbarTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    protected void setNavDrawer(NavDrawer navDrawer){
        this.navDrawer = navDrawer;
        this.navDrawer.create();

        // Disable default activity animations for activities that have a nav drawer
        overridePendingTransition(0,0);
        View rootView = findViewById(android.R.id.content);
        rootView.setAlpha(0);
        rootView.animate().alpha(1).setDuration(350).start();
    }

    public Toolbar getToolbar(){
        return this.toolbar;
    }

    public CameraMessengerApplication getCameraMessengerApplication(){
        return this.application;
    }

    public void fadeOut(final FadeOutListener listener){

        // android.R contains resources and such from actual SDK
        // content = entirely of window frame
        View rootView = findViewById(android.R.id.content);
        rootView.animate().alpha(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onFadeOutEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setDuration(350).start();
    }

    // Interface to implement callback method in android
    public interface FadeOutListener{
        void onFadeOutEnd();
    }

}

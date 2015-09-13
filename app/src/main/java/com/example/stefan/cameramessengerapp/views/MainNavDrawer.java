package com.example.stefan.cameramessengerapp.views;


import android.view.View;
import android.widget.Toast;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.activities.BaseActivity;
import com.example.stefan.cameramessengerapp.activities.MainActivity;

public class MainNavDrawer extends NavDrawer{

    public MainNavDrawer(final BaseActivity activity) {
        super(activity);

        addItem(new ActivityNavDrawerItem(MainActivity.class, "inbox", null, R.drawable.ic_action_unread, R.id.include_main_nav_drawer_topItems));

        addItem(new BasicNavDrawerItem("Logout", null, R.drawable.ic_action_backspace, R.id.include_main_nav_drawer_bottomItems){

            @Override
            public void onClick(View view){
                Toast.makeText(activity, "You have logged out!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

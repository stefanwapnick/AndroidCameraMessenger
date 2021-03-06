package com.example.stefan.cameramessengerapp.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.activities.BaseActivity;
import com.example.stefan.cameramessengerapp.activities.ContactsActivity;
import com.example.stefan.cameramessengerapp.activities.MainActivity;
import com.example.stefan.cameramessengerapp.activities.ProfileActivity;
import com.example.stefan.cameramessengerapp.activities.SentMessagesActivity;
import com.example.stefan.cameramessengerapp.infrastructure.User;
import com.example.stefan.cameramessengerapp.services.Account;
import com.squareup.otto.Subscribe;

public class MainNavDrawer extends NavDrawer{

    private final TextView displayNameText;
    private final ImageView avatarImage;


    public MainNavDrawer(final BaseActivity activity) {
        super(activity);

        addItem(new ActivityNavDrawerItem(MainActivity.class, "Inbox", null, R.drawable.ic_action_unread, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(SentMessagesActivity.class, "Sent Messages", null, R.drawable.ic_action_send_now, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ContactsActivity.class, "Contacts", null, R.drawable.ic_action_group, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ProfileActivity.class, "Profile", null, R.drawable.ic_action_person, R.id.include_main_nav_drawer_topItems));


        addItem(new BasicNavDrawerItem("Logout", null, R.drawable.ic_action_backspace, R.id.include_main_nav_drawer_bottomItems){

            @Override
            public void onClick(View view){
                activity.getCameraMessengerApplication().getAuth().logout();
            }
        });

        displayNameText = (TextView)navDrawerView.findViewById(R.id.include_main_nav_drawer_displayName);
        avatarImage = (ImageView)navDrawerView.findViewById(R.id.include_main_nav_drawer_avatar);

        User user = activity.getCameraMessengerApplication().getAuth().getUser();
        displayNameText.setText(user.getDisplayName());

        // TODO: Set avatar image
    }

    @Subscribe
    public void onUserDetailsUpdate(Account.UserDetailsUpdateEvent response){
        displayNameText.setText(response.User.getDisplayName());
    }

}

package com.example.stefan.cameramessengerapp.services;

import android.util.Log;

import com.example.stefan.cameramessengerapp.infrastructure.Auth;
import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;
import com.example.stefan.cameramessengerapp.infrastructure.User;
import com.squareup.otto.Subscribe;

public class InMemoryAccountService extends BaseInMemoryService{


    private CameraMessengerApplication application;

    public InMemoryAccountService(CameraMessengerApplication application) {
        super(application);
    }

    @Subscribe
    public void updateProfile(final Account.UpdateProfileRequest request){
        final Account.UpdateProfileResponse response = new Account.UpdateProfileResponse();

        if(!(request.DisplayName.length() > 0)){
            response.setPropertyError("displayName", "Name cannot be blank");
        }

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setDisplayName(request.DisplayName);
                user.setEmail(request.Email);

                bus.post(response);
                bus.post(new Account.UserDetailsUpdateEvent(user));
            }
        }, 2000, 3000);
    }

    @Subscribe
    public void changeAvatar(final Account.ChangeAvatarRequest request){

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                User user = application.getAuth().getUser();
                user.setAvatarUrl(request.NewAvatarURL.toString());

                bus.post(new Account.ChangeAvatarResponse());
                bus.post(new Account.UserDetailsUpdateEvent(user));
            }
        }, 4000, 5000);

    }

    @Subscribe
    public void changePassword(Account.ChangePasswordRequest request){
        Account.ChangePasswordResponse response = new Account.ChangePasswordResponse();

        if(!request.NewPassword.equals(request.ConfirmNewPassword)){
            response.setPropertyError("newPassword", "Passwords must match");
            response.setPropertyError("confirmNewPassword", "Passwords must match");
        }

        postDelayed(response, 4000);
    }

    @Subscribe
    public void loginWithUsername(Account.LoginUserRequest request) {

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginUserResponse response = new Account.LoginUserResponse();

                // Pass and receive response by reference to loginUser
                loginUser(response);

                // Post populated response
                bus.post(response);
            }
        }, 1000, 2000);
    }

    @Subscribe
    public void loginWithExternalToken(Account.LoginUserWithExternalTokenRequest request){

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginUserWithExternalTokenResponse response = new Account.LoginUserWithExternalTokenResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 1000, 2000);

    }

    @Subscribe
    public void register(Account.RegisterRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.RegisterResponse response = new Account.RegisterResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 2000, 3000);
    }

    @Subscribe
    public void externalRegister(Account.RegisterWithExternalTokenRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.RegisterWithExternalTokenResponse response = new Account.RegisterWithExternalTokenResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 2000, 3000);

    }

    @Subscribe
    public void loginWithLocalToken(Account.LoginUserWithLocalTokenRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginUserWithLocalTokenResponse response = new Account.LoginUserWithLocalTokenResponse();
                loginUser(response);
                bus.post(response);
            }
        }, 2000, 3000);
    }

    private void loginUser(Account.UserResponse response){
        User user = application.getAuth().getUser();

        user.setDisplayName("Stefan Wapnick");
        user.setUserName("stefanwapnick");
        user.setEmail("s_wapnic@encs.concordia.ca");
        user.setAvatarUrl("http://www.gravatar.com/avatar1?d=identicon");

        bus.post(new Account.UserDetailsUpdateEvent(user));

        application.getAuth().setAuthToken("fakeauthtoken");

        response.DisplayName = user.getDisplayName();
        response.UserName = user.getUserName();
        response.Email = user.getEmail();
        response.AvatarUrl = user.getAvatarUrl();
        response.Id  = user.getId();
        response.AvatarUrl = user.getAvatarUrl();
    }

}

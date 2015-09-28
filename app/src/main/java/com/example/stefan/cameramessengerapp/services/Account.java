package com.example.stefan.cameramessengerapp.services;

import android.net.Uri;

import com.example.stefan.cameramessengerapp.infrastructure.User;

/**
 * Contains classes used for sending requests and responses through Otto event bus
 */
public final class Account{

    private static String CLIENT_ID = "android";

    private Account(){}

    public static class ChangeAvatarRequest{
        public Uri NewAvatarURL;

        public ChangeAvatarRequest(Uri newAvatarUri){
            NewAvatarURL = newAvatarUri;
        }
    }

    public static class ChangeAvatarResponse extends ServiceResponse{
        public String AvatarUrl;
    }

    public static class UpdateProfileRequest{

        public String DisplayName;
        public String Email;

        public UpdateProfileRequest(String displayName, String email) {
            DisplayName = displayName;
            Email = email;
        }
    }

    public static class UpdateProfileResponse extends ServiceResponse{
        public String DisplayName;
        public String Email;
    }

    public static class ChangePasswordRequest{
        public String CurrentPassword;
        public String NewPassword;
        public String ConfirmNewPassword;

        public ChangePasswordRequest(String currentPassword, String newPassword, String confirmNewPassword) {
            CurrentPassword = currentPassword;
            NewPassword = newPassword;
            ConfirmNewPassword = confirmNewPassword;
        }
    }

    public static class ChangePasswordResponse extends ServiceResponse{

    }

    /**
     * Login request and response objects
     */
    public static class UserResponse extends ServiceResponse{
        public int Id;
        public String AvatarUrl;
        public String DisplayName;
        public String UserName;
        public String Email;
        public String AuthToken;
        public boolean hasPassword;
    }

    public static class LoginUserRequest{
        public String Username;
        public String Password;

        public LoginUserRequest(String username, String password) {
            Username = username;
            Password = password;
        }
    }

    public static class LoginUserResponse extends UserResponse{
        public String Username;
        public String Password;
    }

    /**
     * Auth token stored in local storage of phone to persist user login
     * However don't just want to log user in without checking server -> Possible that user account that deleted
     * Thus send login token to server. Server response with user info.
     */
    public static class LoginUserWithLocalTokenRequest{
        public String AuthToken;

        public LoginUserWithLocalTokenRequest(String authToken) {
            AuthToken = authToken;
        }
    }
    public static class LoginUserWithLocalTokenResponse extends UserResponse{
    }

    /**
     * Same as Auth Token login as above but using external login provider such as google
     */
    public static class LoginUserWithExternalTokenRequest{
        public String Provider;
        public String Token;
        public String ClientId;

        public LoginUserWithExternalTokenRequest(String provider, String token) {
            Provider = provider;
            Token = token;
            ClientId = CLIENT_ID;
        }
    }

    public static class LoginUserWithExternalTokenResponse extends UserResponse{
    }

    public static class RegisterRequest{
        public String Username;
        public String Email;
        public String Password;
        public String ClientId;

        public RegisterRequest(String username, String email, String password) {
            Username = username;
            Email = email;
            Password = password;
            ClientId = CLIENT_ID;
        }
    }
    public static class RegisterResponse extends UserResponse{
    }

    public static class RegisterWithExternalTokenRequest{
        public String Username;
        public String Email;
        public String Provider;
        public String AuthToken;
        public String ClientId;

        public RegisterWithExternalTokenRequest(String username, String email, String provider, String authToken) {
            Username = username;
            Email = email;
            Provider = provider;
            AuthToken = authToken;
            ClientId = CLIENT_ID;
        }
    }

    public static class RegisterWithExternalTokenResponse extends UserResponse{
    }

    public static class UserDetailsUpdateEvent{
        public User User;

        public UserDetailsUpdateEvent(User user){
            User = user;
        }
    }


}

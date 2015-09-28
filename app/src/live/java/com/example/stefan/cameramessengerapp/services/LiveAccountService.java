package com.example.stefan.cameramessengerapp.services;

import com.example.stefan.cameramessengerapp.infrastructure.Auth;
import com.example.stefan.cameramessengerapp.infrastructure.CameraMessengerApplication;
import com.example.stefan.cameramessengerapp.infrastructure.RetrofitCallback;
import com.example.stefan.cameramessengerapp.infrastructure.RetrofitCallbackPost;
import com.example.stefan.cameramessengerapp.infrastructure.User;
import com.squareup.otto.Subscribe;

import java.io.File;

import retrofit.mime.TypedFile;

public class LiveAccountService extends BaseLiveService {

    private final Auth auth;

    public LiveAccountService(WebService api, CameraMessengerApplication application) {
        super(api, application);
        auth = application.getAuth();
    }


    @Subscribe
    public void register(Account.RegisterRequest request){

        api.register(request, new RetrofitCallback<Account.RegisterResponse>(Account.RegisterResponse.class) {
            @Override
            protected void onResponse(Account.RegisterResponse response) {
                if (response.isSuccess()) {
                    loginUser(response);
                }

                bus.post(response);
            }
        });
    }

    @Subscribe
    public void loginWithUsername(final Account.LoginUserRequest request){

        api.login(
                request.Username,
                request.Password,
                "android",
                "password",
                new RetrofitCallback<WebService.LoginResponse>(WebService.LoginResponse.class) {
                    @Override
                    protected void onResponse(WebService.LoginResponse loginResponse) {
                        if (!loginResponse.isSuccess()) {
                            Account.LoginUserResponse response = new Account.LoginUserResponse();
                            response.setPropertyError("username", loginResponse.ErrorDescription);
                            bus.post(response);
                            return;
                        }

                        auth.setAuthToken(loginResponse.Token);
                        api.getAccount(new RetrofitCallback<Account.LoginUserWithLocalTokenResponse>(Account.LoginUserWithLocalTokenResponse.class) {
                            @Override
                            protected void onResponse(Account.LoginUserWithLocalTokenResponse loginWithLocalTokenResponse) {
                                if (!loginWithLocalTokenResponse.isSuccess()) {
                                    Account.LoginUserResponse response = new Account.LoginUserResponse();
                                    response.setOperationError(loginWithLocalTokenResponse.getOperationError());
                                    bus.post(response);
                                    return;
                                }

                                // Log user in
                                loginUser(loginWithLocalTokenResponse);
                                bus.post(new Account.LoginUserResponse());
                            }
                        });

                    }
                }
        );
    }


    @Subscribe
    public void loginWithLocalToken(final Account.LoginUserWithLocalTokenRequest request) {
        api.getAccount(new RetrofitCallbackPost<Account.LoginUserWithLocalTokenResponse>(Account.LoginUserWithLocalTokenResponse.class, bus) {
            @Override
            protected void onResponse(Account.LoginUserWithLocalTokenResponse loginWithLocalTokenResponse) {
                loginUser(loginWithLocalTokenResponse);
                super.onResponse(loginWithLocalTokenResponse);
            }
        });
    }

    @Subscribe
    public void updateProfile(final Account.UpdateProfileRequest request) {
        api.updateProfile(request, new RetrofitCallbackPost<Account.UpdateProfileResponse>(Account.UpdateProfileResponse.class, bus) {
            @Override
            protected void onResponse(Account.UpdateProfileResponse response) {
                User user = auth.getUser();
                user.setDisplayName(response.DisplayName);
                user.setEmail(response.Email);
                super.onResponse(response);
                bus.post(new Account.UserDetailsUpdateEvent(user));
            }
        });
    }

    @Subscribe
    public void updateAvatar(final Account.ChangeAvatarRequest request) {
        api.updateAvatar(
                new TypedFile("image/jpeg", new File(request.NewAvatarURL.getPath())),
                new RetrofitCallbackPost<Account.ChangeAvatarResponse>(Account.ChangeAvatarResponse.class, bus) {
                    @Override
                    protected void onResponse(Account.ChangeAvatarResponse response) {
                        User user = auth.getUser();
                        user.setAvatarUrl(response.AvatarUrl);
                        super.onResponse(response);
                        bus.post(new Account.UserDetailsUpdateEvent(user));
                    }
                });
    }

    @Subscribe
    public void changePassword(Account.ChangePasswordRequest request) {
        api.updatePassword(request, new RetrofitCallbackPost<Account.ChangePasswordResponse>(Account.ChangePasswordResponse.class, bus) {
            @Override
            protected void onResponse(Account.ChangePasswordResponse response) {
                if (response.isSuccess()) {
                    auth.getUser().setHasPassword(true);
                }

                super.onResponse(response);
            }
        });
    }

    @Subscribe
    public void loginWithExternalToken(Account.LoginUserWithExternalTokenRequest request) {
        api.loginWithExternalToken(request, new RetrofitCallbackPost<Account.LoginUserWithExternalTokenResponse>(Account.LoginUserWithExternalTokenResponse.class, bus) {
            @Override
            protected void onResponse(Account.LoginUserWithExternalTokenResponse response) {
                if (response.isSuccess()) {
                    loginUser(response);
                }

                super.onResponse(response);
            }
        });
    }

    @Subscribe
    public void registerWithExternalToken(Account.RegisterWithExternalTokenRequest request) {
        api.registerExternal(request, new RetrofitCallbackPost<Account.RegisterWithExternalTokenResponse>(Account.RegisterWithExternalTokenResponse.class, bus) {
            @Override
            protected void onResponse(Account.RegisterWithExternalTokenResponse response) {
                if (response.isSuccess()) {
                    loginUser(response);
                }

                super.onResponse(response);
            }
        });
    }



    private void loginUser(Account.UserResponse response){

        if(response.AuthToken != null && ! response.AuthToken.isEmpty()){
            auth.setAuthToken(response.AuthToken);
        }

        User user = auth.getUser();
        user.setId(response.Id);
        user.setDisplayName(response.DisplayName);
        user.setUserName(response.UserName);
        user.setEmail(response.Email);
        user.setAvatarUrl(response.AvatarUrl);
        user.setHasPassword(response.hasPassword);
        user.setIsLoggedIn(true);

        bus.post(new Account.UserDetailsUpdateEvent(user));

    }


}

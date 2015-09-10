package com.example.stefan.cameramessengerapp.infrastructure;

public class User {

    /** ID for user */
    private int id;
    /** User name */
    private String userName;
    /** Display name for user. In this application userName cannot be changed however display name can */
    private String displayName;
    /** User email address */
    private String email;
    /** URL to user picture*/
    private String avatarUrl;
    /** True if user logged in */
    private boolean isLoggedIn;
    /** True if user has password. May be false if user logged in by external service (google, facebook, etc) */
    private boolean hasPassword;

    public User() {
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

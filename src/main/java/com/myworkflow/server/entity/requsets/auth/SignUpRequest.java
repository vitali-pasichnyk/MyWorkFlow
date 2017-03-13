package com.myworkflow.server.entity.requsets.auth;

/**
 * Created by User
 * creation date: 12-Mar-17
 * email: code.crosser@gmail.com
 */
public class SignUpRequest {

    private String authToken;
    private String userName;
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(String authtoken, String user, String password) {
        this.authToken = authtoken;
        this.userName = user;
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

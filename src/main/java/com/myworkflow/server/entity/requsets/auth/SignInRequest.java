package com.myworkflow.server.entity.requsets.auth;

/**
 * created by: Vitalii Pasichnyk
 * creation date: 3/13/2017
 * email: code.crosser@gmail.com
 */
public class SignInRequest {

    private String authToken;
    private String userName;
    private String password;

    public SignInRequest() {
    }

    public SignInRequest(String authtoken, String user, String password) {
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

package com.myworkflow.server.entity.responses.auth;

/**
 * Created by User
 * creation date: 12-Mar-17
 * email: code.crosser@gmail.com
 */
public class SignUpResponse {

    private String status;

    private String token;

    public SignUpResponse() {
    }

    public SignUpResponse(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

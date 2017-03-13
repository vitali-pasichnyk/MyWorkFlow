package com.myworkflow.server.entity.responses.auth;

/**
 * created by: Vitalii Pasichnyk
 * creation date: 3/13/2017
 * email: code.crosser@gmail.com
 */
public class SignInResponse {

    private String status;

    private String token;

    public SignInResponse() {
    }

    public SignInResponse(String status, String token) {
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

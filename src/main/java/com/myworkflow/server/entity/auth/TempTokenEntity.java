package com.myworkflow.server.entity.auth;

import java.util.Objects;

/**
 * Created by User
 * creation date: 11-Mar-17
 * email: code.crosser@gmail.com
 */
public class TempTokenEntity {

    private String tokenBody;

    private long localServerTime;

    private long expirationTime;

    public TempTokenEntity() {
    }

    public TempTokenEntity(String tokenBody, long localServerTime, long expirationTime) {
        this.tokenBody = tokenBody;
        this.localServerTime = localServerTime;
        this.expirationTime = expirationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempTokenEntity that = (TempTokenEntity) o;
        return Objects.equals(tokenBody, that.tokenBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenBody);
    }

    public String getTokenBody() {
        return tokenBody;
    }

    public void setTokenBody(String tokenBody) {
        this.tokenBody = tokenBody;
    }

    public long getLocalServerTime() {
        return localServerTime;
    }

    public void setLocalServerTime(long localServerTime) {
        this.localServerTime = localServerTime;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}

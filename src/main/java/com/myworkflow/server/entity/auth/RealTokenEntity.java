package com.myworkflow.server.entity.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by User
 * creation date: 12-Mar-17
 * email: code.crosser@gmail.com
 */
@Entity
@Table(name = "real_token_table")
public class RealTokenEntity {

    @Id
    @Column(name = "real_token_string", nullable = false, unique = true)
    private String realTokenBody;

    public RealTokenEntity() {
    }

    public RealTokenEntity(String realTokenString) {
        this.realTokenBody = realTokenString;
    }

    public String getRealTokenBody() {
        return realTokenBody;
    }

    public void setRealTokenBody(String realTokenBody) {
        this.realTokenBody = realTokenBody;
    }
}

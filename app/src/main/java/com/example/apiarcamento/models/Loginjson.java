package com.example.apiarcamento.models;

import com.example.apiarcamento.models.Credentials;

public class Loginjson {
    private String msg;
    private Credentials credentials;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String value) {
        this.msg = value;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials value) {
        this.credentials = value;
    }
}

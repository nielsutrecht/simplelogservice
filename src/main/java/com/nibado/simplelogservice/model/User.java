package com.nibado.simplelogservice.model;

public class User {
    private final String user;
    private final String ip;
    private String state;

    public User(String user, String ip, String state) {
        this.user = user;
        this.ip = ip;
        this.state = state;
    }

    public String getUser() {
        return user;
    }

    public String getIp() {
        return ip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

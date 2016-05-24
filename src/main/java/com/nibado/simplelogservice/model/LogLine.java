package com.nibado.simplelogservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static java.lang.System.currentTimeMillis;

@Entity
public class LogLine {
    @Id
    @GeneratedValue
    private long id;
    private String ip;
    private long timestamp;
    private String message;
    private String user;

    public LogLine() {

    }

    public LogLine(String ip, String message) {
        this(ip, message, currentTimeMillis(), null);
    }

    public LogLine(String ip, String message, long timestamp, String user) {
        this.ip = ip;
        this.message = message;
        this.timestamp = timestamp;
        this.user = user;
    }

    public LogLine addUser(String user) {
        return new LogLine(ip, message, timestamp, user);
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

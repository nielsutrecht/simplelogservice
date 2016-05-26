package com.nibado.simplelogservice.service;

import com.nibado.simplelogservice.model.LogLine;

import com.nibado.simplelogservice.model.LogLineRowMapper;
import com.nibado.simplelogservice.model.User;
import com.nibado.simplelogservice.model.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogService {
    private JdbcTemplate jdbcTemplate;

    private Map<String, String> stateMap = new HashMap<>();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public LogLine add(LogLine logLine) {
        insert(logLine);

        return logLine;
    }

    public void insert(LogLine logLine) {
        jdbcTemplate.update(
                "INSERT INTO logline(ip, created, message) VALUES(?, ?, ?)",
                logLine.getIp(), new Timestamp(logLine.getTimestamp()), logLine.getMessage());

    }

    public List<LogLine> getAll() {
        return jdbcTemplate.query("SELECT l.ip, l.created, l.message, u.name FROM logline AS l LEFT OUTER JOIN user AS u ON l.ip = u.ip ORDER BY created DESC", new LogLineRowMapper());
    }

    public List<LogLine> get(String ip) {
        return jdbcTemplate.query("SELECT l.ip, l.created, l.message, u.name FROM logline AS l LEFT OUTER JOIN user AS u ON l.ip = u.ip WHERE l.ip = ? ORDER BY created DESC", new Object[] {ip}, new LogLineRowMapper());
    }

    public void setName(String ip, String user) {
        jdbcTemplate.update("MERGE INTO user KEY(ip) VALUES(?, ?)", ip, user);
    }

    public void setState(String ip, String state) {
        stateMap.put(ip, state);
    }

    public String getState(String ip) {
        return stateMap.get(ip);
    }

    public List<User> getUsers() {
        List<User> users = jdbcTemplate.query(UserRowMapper.QUERY, new UserRowMapper());

        users.forEach(u -> u.setState(stateMap.get(u.getIp())));

        return users;
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS logline(ip VARCHAR(255), created TIMESTAMP, message VARCHAR(10000));");
        jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS logline_ip ON logline(ip);");
        jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS logline_created ON logline(created);");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user (ip VARCHAR(255), name VARCHAR(255), PRIMARY KEY(ip));");
    }
}

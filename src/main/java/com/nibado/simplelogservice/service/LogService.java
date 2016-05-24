package com.nibado.simplelogservice.service;

import com.nibado.simplelogservice.model.LogLine;
import com.nibado.simplelogservice.model.LogLineRowMapper;
import com.nibado.simplelogservice.model.SingleStringMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class LogService {
    private JdbcTemplate jdbcTemplate;

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

    public List<String> getUsers() {
        return jdbcTemplate.query("SELECT name FROM user", new SingleStringMapper(1));
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS logline(ip VARCHAR(255), created TIMESTAMP, message VARCHAR(255));");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user(ip VARCHAR(255), name VARCHAR(255));");
    }

}

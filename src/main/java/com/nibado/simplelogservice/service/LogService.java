package com.nibado.simplelogservice.service;

import com.nibado.simplelogservice.model.LogLine;
import com.nibado.simplelogservice.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    private Map<String, String> userMap = new HashMap<>();

    public LogService() {
    }

    public LogLine add(LogLine logLine) {
        logRepository.save(logLine);

        logRepository.findAll().forEach(System.out::println);

        return logLine;
    }

    public List<LogLine> getAll() {
        List<LogLine> list = new ArrayList<>();
        logRepository.findAll().forEach(list::add);

        return list;
    }

    public List<LogLine> get(String ip) {
        return logRepository.findByIp(ip);
    }

    public void setName(String ip, String user) {
        userMap.put(ip, user);
    }

    public List<String> getUsers() {
        return userMap.values().stream().collect(toList());
    }

    private LogLine mapUser(LogLine line) {
        return line;
    }

}

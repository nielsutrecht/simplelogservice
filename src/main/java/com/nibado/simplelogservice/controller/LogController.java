package com.nibado.simplelogservice.controller;

import com.nibado.simplelogservice.service.LogService;
import com.nibado.simplelogservice.model.LogLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {
    private static final Logger LOG = LoggerFactory.getLogger(LogController.class);
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LogService logService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<LogLine> get() {
        return getFor(getIp());
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<LogLine> getAll() {
        return logService.getAll();
    }

    @RequestMapping(value = "/{ip}/", method = RequestMethod.GET)
    public List<LogLine> getFor(@PathVariable String ip) {
        return logService.get(ip);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public LogLine add(@RequestBody String message) {
        LogLine line = logService.add(new LogLine(getIp(), message));
        LOG.info("Ip = {}, user = {}, message = {}", line.getIp(), line.getUser(), line.getMessage());
        return line;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<String> getUsers() {
        return logService.getUsers();
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public void setUser(@RequestBody String user) {
        LOG.info("Ip = {} set to user name {}", getIp(), user);
        logService.setName(getIp(), user);
    }

    @RequestMapping(value = "/state", method = RequestMethod.PUT)
    public void setState(@RequestBody String state) {

    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public void getState() {

    }

    private String getIp() {
        return request.getRemoteAddr();
    }
}

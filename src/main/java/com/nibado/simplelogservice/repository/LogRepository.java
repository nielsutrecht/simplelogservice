package com.nibado.simplelogservice.repository;

import com.nibado.simplelogservice.model.LogLine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogRepository extends CrudRepository<LogLine, String> {
    List<LogLine> findByIp(String ip);
}

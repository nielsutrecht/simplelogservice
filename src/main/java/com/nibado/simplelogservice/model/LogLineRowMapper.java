package com.nibado.simplelogservice.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogLineRowMapper implements RowMapper<LogLine> {
    @Override
    public LogLine mapRow(ResultSet resultSet, int i) throws SQLException {
        return new LogLine(resultSet.getString(1), resultSet.getString(3), resultSet.getTimestamp(2).getTime(),  resultSet.getString(4));
    }
}

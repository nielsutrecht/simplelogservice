package com.nibado.simplelogservice.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public static final String QUERY = "SELECT u.name, u.ip, s.state FROM user AS u LEFT OUTER JOIN state AS s ON u.ip = s.ip";
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
    }
}

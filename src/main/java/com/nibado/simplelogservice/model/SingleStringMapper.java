package com.nibado.simplelogservice.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jdriven on 24/05/16.
 */
public class SingleStringMapper implements RowMapper<String> {
    private int index;
    public SingleStringMapper(int index) {
        this.index = index;
    }
    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(index);
    }
}

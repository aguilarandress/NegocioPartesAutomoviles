package com.negocio.automoviles.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnioMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("anio");
    }
}

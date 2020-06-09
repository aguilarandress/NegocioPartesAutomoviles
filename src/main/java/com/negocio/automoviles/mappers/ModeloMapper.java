package com.negocio.automoviles.mappers;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase para mappear los modelos de los automoviles
 */
public class ModeloMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("modelo");
    }
}

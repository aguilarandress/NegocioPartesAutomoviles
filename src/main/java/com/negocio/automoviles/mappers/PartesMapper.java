package com.negocio.automoviles.mappers;

import org.springframework.jdbc.core.RowMapper;
import com.negocio.automoviles.models.Parte;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase para mappear partes
 */
public class PartesMapper implements RowMapper<Parte> {
    @Override
    public Parte mapRow(ResultSet rs, int rowNum) throws SQLException {
        Parte parte = new Parte();
        parte.setParteID(rs.getInt("id"));
        parte.setNombre(rs.getString("nombre"));
        parte.setFabricante(rs.getString("fabricante"));
        parte.setMarca(rs.getString("marca"));
        return parte;
    }
}

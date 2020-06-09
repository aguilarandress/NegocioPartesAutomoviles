package com.negocio.automoviles.mappers;

import com.negocio.automoviles.models.Detalle;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AfiliacionMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Detalle detalle = new Detalle();
        detalle.setParteID(rs.getInt("parte_id"));
        detalle.setProveedorID(rs.getInt("provedor_id"));
        detalle.setPrecio(rs.getDouble("precio"));
        detalle.setPorcentaje_ganancia(rs.getDouble("porcentaje_ganancia"));
        detalle.setNombre_parte(rs.getString("parte_nombre"));
        detalle.setNombre_provedor(rs.getString("provedor_nombre"));
        return detalle;
    }
}

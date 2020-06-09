package com.negocio.automoviles.mappers;

import com.negocio.automoviles.models.Detalle;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetallesMapper implements RowMapper<Detalle> {
    @Override
    public Detalle mapRow(ResultSet rs, int rowNum) throws SQLException {
        Detalle detalle= new Detalle();
        detalle.setParteID(rs.getInt("parte_id"));
        detalle.setProveedorID(rs.getInt("provedor_id"));
        detalle.setCantidad(rs.getInt("cantidad"));
        detalle.setConsecutivoOrden(rs.getInt("consecutivo_orden"));
        detalle.setPrecio(rs.getDouble("precio"));
        return detalle;
    }
}

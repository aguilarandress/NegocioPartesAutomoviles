package com.negocio.automoviles.mappers;

import com.negocio.automoviles.models.Orden;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdenMapper implements RowMapper<Orden> {
    @Override
    public Orden mapRow(ResultSet rs, int rowNum) throws SQLException {
        Orden orden= new Orden();
        orden.setNombreCliente(rs.getString("nombre"));
        orden.setConsecutivo(rs.getInt("consecutivo"));
        orden.setIdCliente(rs.getInt("cliente_id"));
        orden.setFecha(rs.getString("fecha"));
        orden.setTotal(rs.getDouble("monto_total"));
        return orden;
    }
}

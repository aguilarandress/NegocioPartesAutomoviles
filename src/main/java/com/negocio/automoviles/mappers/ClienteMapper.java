package com.negocio.automoviles.mappers;

import com.negocio.automoviles.models.Cliente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase para mappear objetos de tipo cliente
 */
public class ClienteMapper implements RowMapper<Cliente> {
    @Override
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setEstado(rs.getString("estado"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setCiudad(rs.getString("ciudad"));
        return cliente;
    }
}

package com.negocio.automoviles.mappers;

import com.negocio.automoviles.models.Organizacion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizacionMapper implements RowMapper<Organizacion> {
    @Override
    public Organizacion mapRow(ResultSet rs, int rowNum) throws SQLException {
        Organizacion organizacion= new Organizacion();
        organizacion.setCedula(rs.getLong("cedula_juridica"));
        organizacion.setId(rs.getInt("id"));
        organizacion.setNombre(rs.getString("nombre"));
        organizacion.setDireccion(rs.getString("direccion"));
        organizacion.setEstado(rs.getString("estado"));
        organizacion.setCiudad(rs.getString("ciudad"));
        organizacion.setE_cargo(rs.getString("encargado_cargo"));
        organizacion.setE_nombre(rs.getString("encargado_nombre"));
        organizacion.setE_telefono(rs.getString("encargado_telefono"));

        return organizacion;
    }
}

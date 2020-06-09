package com.negocio.automoviles.mappers;

import com.negocio.automoviles.models.Persona;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaMapper implements RowMapper<Persona> {
    @Override
    public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {
        Persona persona = new Persona();
        persona.setCedula(rs.getInt("cedula"));
        persona.setId(rs.getInt("id"));
        persona.setNombre(rs.getString("nombre"));
        persona.setDireccion(rs.getString("direccion"));
        persona.setEstado(rs.getString("estado"));
        persona.setCiudad(rs.getString("ciudad"));

        return persona;
    }
}

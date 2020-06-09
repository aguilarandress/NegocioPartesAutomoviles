package com.negocio.automoviles.mappers;

import com.negocio.automoviles.models.Provedor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProvedoresMapper implements RowMapper<Provedor> {
    @Override
    public Provedor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Provedor provedor = new Provedor();

        provedor.setNombre(rs.getString("nombre"));
        provedor.setCiudad(rs.getString("ciudad"));
        provedor.setDirección(rs.getString("direccion"));

        //TODO: mappeo telefono
        //provedor.setTelefonos();
        provedor.setNombre_contacto(rs.getString("nombre_contacto"));
        return provedor;
    }
}

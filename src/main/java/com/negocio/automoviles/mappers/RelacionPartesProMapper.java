package com.negocio.automoviles.mappers;

import com.negocio.automoviles.models.ParteProvedor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class RelacionPartesProMapper implements RowMapper<ParteProvedor> {


    @Override
    public ParteProvedor mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParteProvedor relacion= new ParteProvedor();
        relacion.setParteID(rs.getInt("parte_id"));
        relacion.setProvedorID(rs.getInt("provedor_id"));
        relacion.setPrecio(rs.getFloat("precio"));
        relacion.setPorcentaje_ganancia(rs.getFloat("porcentaje_ganancia"));


        return relacion;
    }
}

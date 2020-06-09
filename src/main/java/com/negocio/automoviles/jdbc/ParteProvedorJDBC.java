package com.negocio.automoviles.jdbc;

import com.negocio.automoviles.daos.ParteProvedorDAO;
import com.negocio.automoviles.mappers.ParteProvedorIDMapper;
import com.negocio.automoviles.mappers.RelacionPartesProMapper;
import com.negocio.automoviles.models.HolderPartProvedor;
import com.negocio.automoviles.models.ParteProvedor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.ws.Holder;
import java.util.List;

public class ParteProvedorJDBC implements ParteProvedorDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<HolderPartProvedor> getRelaciones() {
        String query=" SELECT proveido_por.parte_id , proveido_por.provedor_id ,precio,porcentaje_ganancia FROM proveido_por";
        List<HolderPartProvedor> relaciones= jdbcTemplateObject.query(query,new ParteProvedorIDMapper());
        return relaciones;
    }

    @Override
    public boolean existeRelacion(int parteID, int provedorID) {
        String query = "SELECT proveido_por.parte_id , proveido_por.provedor_id , proveido_por.precio, proveido_por.porcentaje_ganancia  FROM proveido_por " +
                " WHERE proveido_por.parte_id = ? AND proveido_por.provedor_id = ?";
        List<ParteProvedor> relaciones=jdbcTemplateObject.query(query,new Object[]{parteID,provedorID},new RelacionPartesProMapper());
        return relaciones.size()>0 ;
    }

    @Override
    public void modificarRelacion(int IDParte, int IDProve, float precio, float porcentaje_ganancia) {

        String query = "UPDATE proveido_por SET precio = ?, porcentaje_ganancia = ? WHERE parte_id = ? AND provedor_id = ?";
        jdbcTemplateObject.update(query, precio,porcentaje_ganancia, IDParte, IDProve);


    }
}

package com.negocio.automoviles.jdbc;

import com.negocio.automoviles.daos.ProvedoresDAO;
import com.negocio.automoviles.mappers.MarcPIDMapper;
import com.negocio.automoviles.mappers.ProvedoresMapper;
import com.negocio.automoviles.models.Provedor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class ProvedoresJDBC implements ProvedoresDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(ds);
    }

    /**
     * Retorna un proveedor según el id brindado
     * @param ID
     * @return
     */
    @Override
    public Provedor getProvedor(int ID) {

        String query="SELECT nombre,ciudad,direccion,telefono,nombre_contacto,id FROM provedores" +
                " WHERE id = ? ";
        Provedor provedor=jdbcTemplateObject.queryForObject(query,new Object[]{ID},new ProvedoresMapper());

        return provedor;
    }

    /**
     * Retorna una lista de todos los proveedores existentes
     * @return
     */
    @Override
    public List<Provedor> getProvedores() {
        String query = "SELECT nombre,ciudad,direccion,telefono,nombre_contacto FROM provedores";
        List<Provedor> provedores= jdbcTemplateObject.query(query,new ProvedoresMapper());
        return provedores;
    }

    /**
     * Devuelve el ID de un provedor
     * @param nombre
     * @return
     */
    @Override
    public int getIDProvedor(String nombre) {
        String query = "SELECT provedores.id FROM provedores " +
                " WHERE provedores.nombre = ?";
        int ID=jdbcTemplateObject.queryForObject(query,new Object[] {nombre},new MarcPIDMapper());
        return  ID;
    }

}

package com.negocio.automoviles.jdbc;

import com.negocio.automoviles.daos.OrganizacionDAO;
import com.negocio.automoviles.mappers.OrganizacionMapper;
import com.negocio.automoviles.models.Organizacion;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class OrganizacionJDBC implements OrganizacionDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    /**
     * Configura el datasource del programa
     * @param ds
     */
    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    /**
     * Devuelve una lista con las organizaciones de la base de datos
     * @return
     */
    @Override
    public List<Organizacion> getOrganizaciones() {
        String query = "SELECT clientes.nombre, organizaciones.cedula_juridica, clientes.estado, clientes.id, clientes.direccion, clientes.ciudad " +
                ",organizaciones.encargado_nombre,organizaciones.encargado_telefono,organizaciones.encargado_cargo "
                +
                "FROM clientes INNER JOIN organizaciones ON clientes.id = organizaciones.id_cliente";
        List<Organizacion> organizaciones = jdbcTemplateObject.query(query, new OrganizacionMapper());
        return organizaciones;
    }

    /**
     * Devuelve una la organizacion correspondiente a una cédula
     * @param cedula
     * @return
     */
    @Override
    public Organizacion getOrganizacion(Long cedula) {
        String query = "SELECT clientes.nombre, organizaciones.cedula_juridica, clientes.estado, clientes.id, clientes.direccion, clientes.ciudad , organizaciones.encargado_nombre," +
                " organizaciones.encargado_telefono,organizaciones.encargado_cargo " +
                "FROM clientes INNER JOIN organizaciones ON clientes.id = organizaciones.id_cliente WHERE cedula_juridica = ?";
        Organizacion organizacion = jdbcTemplateObject.queryForObject(query, new Object[]{cedula}, new OrganizacionMapper());
        return organizacion;
    }

    /**
     * Retorna un booleano indicando si existe alguna organización con ese número de cédula
     * @param cedula
     * @return
     */
    @Override
    public boolean existeCedula(Long cedula) {
        String query = "SELECT clientes.nombre, organizaciones.cedula_juridica, clientes.estado, clientes.id, clientes.direccion, clientes.ciudad ," +
                "organizaciones.encargado_nombre,organizaciones.encargado_telefono,organizaciones.encargado_cargo " +
                "FROM clientes INNER JOIN organizaciones ON clientes.id = organizaciones.id_cliente WHERE cedula_juridica = ?";
        List<Organizacion> organizaciones = jdbcTemplateObject.query(query, new Object[]{cedula}, new OrganizacionMapper());
        return organizaciones.size() > 0;
    }

    @Override
    public void agregarOrganizacion(Organizacion organizacion) {
        ClientJDBC clientJDBC = new ClientJDBC();
        clientJDBC.setDataSource(this.dataSource);
        // Insertar en tabla de clientes
        int id_cliente = clientJDBC.agregarCliente(organizacion.getNombre(), organizacion.getDireccion(), organizacion.getCiudad(), "ACTIVO");
        String query = "INSERT INTO organizaciones (cedula_juridica,id_cliente,encargado_nombre,encargado_cargo,encargado_telefono) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplateObject.update(query, organizacion.getCedula(), id_cliente,organizacion.getE_nombre(),organizacion.getE_cargo(),organizacion.getE_telefono());
    }
    public  void modificarOrganizacion(int id,String E_nombre,String E_cargo,String E_telefono)
    {
        String query = "UPDATE organizaciones SET encargado_nombre = ?,encargado_cargo = ?, encargado_telefono = ? WHERE id_cliente = ?";
        jdbcTemplateObject.update(query, E_nombre, E_cargo, E_telefono, id);
    }
}

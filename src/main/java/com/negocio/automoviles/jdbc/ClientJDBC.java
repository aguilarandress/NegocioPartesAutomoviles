package com.negocio.automoviles.jdbc;

import com.negocio.automoviles.daos.ClienteDAO;
import com.negocio.automoviles.mappers.ClienteMapper;
import com.negocio.automoviles.mappers.EstadoMapper;
import com.negocio.automoviles.mappers.OrganizacionMapper;
import com.negocio.automoviles.mappers.PersonaMapper;
import com.negocio.automoviles.models.Cliente;
import com.negocio.automoviles.models.Persona;
import org.springframework.jdbc.core.JdbcTemplate;
import com.negocio.automoviles.models.Organizacion;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientJDBC implements ClienteDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    private SimpleJdbcInsert simpleJdbcInsert;

    /**
     * Configura el data source de la app
     * @param dataSource El data source de la app
     */
    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("clientes").usingGeneratedKeyColumns("id");
    }

    /**
     * Agregar informacion a la tabla cliente
     * @param nombre
     * @param direccion
     * @param ciudad
     * @param estado
     * @return La llave de la fila
     */
    @Override
    public int agregarCliente(String nombre, String direccion, String ciudad, String estado) {
        // Agregar parametros
        Map<String, Object> parameters = new HashMap<>(4);
        parameters.put("nombre", nombre);
        parameters.put("direccion", direccion);
        parameters.put("ciudad", ciudad);
        parameters.put("estado", estado);
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return newId.intValue();
    }

    /**
     * Modifica la informacion de un cliente
     * @param id
     * @param nombre
     * @param direccion
     * @param ciudad
     * @param estado
     */
    @Override
    public void modificarCliente(int id, String nombre, String direccion, String ciudad, String estado) {
        String query = "UPDATE clientes SET nombre = ?, direccion = ?, ciudad = ?, estado = ? WHERE id = ?";
        jdbcTemplateObject.update(query, nombre, direccion, ciudad, estado, id);
    }

    /**
     * Suspende un cliente
     * @param id
     */
    @Override
    public void suspenderCliente(int id) {
        String query = "UPDATE clientes SET estado = 'SUSPENDIDO' WHERE id = ?";
        jdbcTemplateObject.update(query, id);
    }

    /**
     * Obtiene todos los estados
     * @return Los estados disponibles para un cliente
     */
    @Override
    public List<String> getEstados() {
        String query = "SELECT estado FROM estados";
        List<String> estados = jdbcTemplateObject.query(query, new EstadoMapper());
        return estados;
    }

    /**
     * Activar un cliente luego de realizar una orden
     * @param id El id del cliente
     */
    @Override
    public void activarCliente(int id) {
        String query = "UPDATE clientes SET estado = 'ACTIVO' WHERE id = ?";
        jdbcTemplateObject.update(query, id);
    }

    /**
     * Verifica si el cliente esta suspendido
     * @param id El ID del cliente
     * @return Si el cliente esta suspendido o no
     */
    @Override
    public boolean verificarClienteSuspendido(int id) {
        String query = "SELECT clientes.id, clientes.estado, clientes.nombre, clientes.direccion, clientes.ciudad " +
                        "FROM clientes WHERE clientes.estado = 'SUSPENDIDO' AND id = ?";
        return jdbcTemplateObject.query(query, new Object[] {id}, new ClienteMapper()).size() > 0;
    }
}

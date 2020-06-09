package com.negocio.automoviles.jdbc;

import com.negocio.automoviles.mappers.TelefonoMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import com.negocio.automoviles.daos.PersonaDAO;
import com.negocio.automoviles.mappers.PersonaMapper;
import com.negocio.automoviles.models.Persona;

public class PersonaJDBC implements PersonaDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;


    /**
     * Configura el data source de la app
     * @param dataSource El data source de la app
     */
    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    /**
     * Obtiene todas las personas
     * @return Las personas
     */
    @Override
    public List<Persona> getPersonas() {
        String query = "SELECT clientes.nombre, personas.cedula, clientes.estado, clientes.id, clientes.direccion, clientes.ciudad " +
                "FROM clientes INNER JOIN personas ON clientes.id = personas.id_cliente";
        List<Persona> personas = jdbcTemplateObject.query(query, new PersonaMapper());
        return personas;
    }

    /**
     * Verifica si existe la cedula
     * @param cedula
     * @return true si existe la cedula, false de lo contrario
     */
    @Override
    public boolean existeCedula(int cedula) {
        String query = "SELECT clientes.nombre, personas.cedula, clientes.estado, clientes.id, clientes.direccion, clientes.ciudad " +
                "FROM clientes INNER JOIN personas ON clientes.id = personas.id_cliente WHERE cedula = ?";
        List<Persona> personas = jdbcTemplateObject.query(query, new Object[]{cedula}, new PersonaMapper());
        return personas.size() > 0;
    }

    /**
     * Agrega una persona a la base de datos
     * @param persona
     */
    @Override
    public void agregarPersona(Persona persona) {
        ClientJDBC clientJDBC = new ClientJDBC();
        clientJDBC.setDataSource(this.dataSource);
        // Insertar en tabla de clientes
        int surrogateKey = clientJDBC.agregarCliente(persona.getNombre(), persona.getDireccion(), persona.getCiudad(), "ACTIVO");
        String query = "INSERT INTO personas (cedula, id_cliente) VALUES (?, ?)";
        jdbcTemplateObject.update(query, persona.getCedula(), surrogateKey);
    }

    @Override
    public Persona getPersona(int cedula) {
        String query = "SELECT clientes.nombre, personas.cedula, clientes.estado, clientes.id, clientes.direccion, clientes.ciudad " +
                "FROM clientes INNER JOIN personas ON clientes.id = personas.id_cliente WHERE cedula = ?";
        Persona persona = jdbcTemplateObject.queryForObject(query, new Object[]{cedula}, new PersonaMapper());
        persona.setTelefonos(this.getTelefonos(cedula));
        return persona;
    }

    @Override
    public boolean existeTelefono(String telefono, int cedula) {
        String query = "SELECT telefono FROM telefonos_persona WHERE telefono = ? AND cedula = ?";
        List<String> telefonos = jdbcTemplateObject.query(query, new Object[] {telefono, cedula}, new TelefonoMapper());
        System.out.println(telefonos.size());
        return telefonos.size() > 0;
    }

    @Override
    public List<String> getTelefonos(int cedula) {
        String query = "SELECT telefono FROM telefonos_persona WHERE cedula = ?";
        List<String> telefonos = jdbcTemplateObject.query(query, new Object[] {cedula}, new TelefonoMapper());
        return telefonos;
    }

    @Override
    public void agregarTelefono(int cedula, String telefono) {
        String query = "INSERT INTO telefonos_persona (telefono, cedula) VALUES(?, ?)";
        jdbcTemplateObject.update(query, telefono, cedula);
    }

    @Override
    public void borrarTelefono(int cedula, String telefono) {
        String query = "DELETE FROM telefonos_persona WHERE cedula = ? AND telefono = ?";
        jdbcTemplateObject.update(query, cedula, telefono);
    }
}

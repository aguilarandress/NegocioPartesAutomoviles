package com.negocio.automoviles.jdbc;

import com.negocio.automoviles.daos.AutomovilesDAO;
import com.negocio.automoviles.mappers.AnioMapper;
import com.negocio.automoviles.mappers.ModeloMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Clase para acceder a las entidades de automoviles
 */
public class AutomovilesJDBC implements AutomovilesDAO {
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
     * Obtiene todos los modelos disponibles
     * @return Los modelos de la base de datos
     */
    @Override
    public List<String> getModelosDisponibles() {
        String query = "SELECT DISTINCT modelo FROM automoviles";
        List<String> modelos = jdbcTemplateObject.query(query, new ModeloMapper());
        return modelos;
    }

    /**
     * Obtiene todos los anios dispobles
     * @return Los anios disponibles
     */
    @Override
    public List<Integer> getAniosDisponinles() {
        String query = "SELECT DISTINCT anio FROM automoviles";
        List<Integer> anios = jdbcTemplateObject.query(query, new AnioMapper());
        return anios;
    }

    /**
     * Verifica si existe el automovil
     * @param modelo El modelo del automovil
     * @param anio El anio del automovil
     * @return Si existe el autmovil o no
     */
    @Override
    public boolean existeAutomovil(String modelo, int anio) {
        String query = "SELECT modelo FROM automoviles WHERE modelo = ? AND anio = ?";
        int numeroFilas = jdbcTemplateObject.query(query, new Object[]{modelo, anio}, new ModeloMapper()).size();
        return numeroFilas > 0;
    }

    /**
     * Verifica si ya existe una asociacion de automovil con parte
     * @param parteId El id de la parte
     * @param autoModelo El modelo del automovil
     * @param autoAnio El anio del automovil
     * @return Si existe la asociacion o no
     */
    @Override
    public boolean existeAsociacion(int parteId, String autoModelo, int autoAnio) {
        String query = "SELECT auto_modelo AS modelo FROM es_parte_de WHERE auto_modelo = ? AND auto_anio = ? AND parte_id = ?";
        int numeroFilas = jdbcTemplateObject.query(query, new Object[]{autoModelo, autoAnio, parteId}, new ModeloMapper()).size();
        return numeroFilas > 0;
    }

    /**
     * Crea una nueva asociacion de parte con automovil
     * @param parteId El id de la parte
     * @param autoModelo El modelo del auto
     * @param autoAnio El anio del auto
     */
    @Override
    public void asociarAutomovil(int parteId, String autoModelo, int autoAnio) {
        String query = "INSERT INTO es_parte_de (parte_id, auto_modelo, auto_anio) VALUES(?, ?, ?)";
        jdbcTemplateObject.update(query, new Object[]{parteId, autoModelo, autoAnio});
    }
}

package com.negocio.automoviles.daos;

import javax.sql.DataSource;
import java.util.List;

public interface AutomovilesDAO {
    public void setDataSource(DataSource ds);
    public List<String> getModelosDisponibles();
    public List<Integer> getAniosDisponinles();
    public boolean existeAutomovil(String modelo, int anio);
    public boolean existeAsociacion(int parteId, String autoModelo, int autoAnio);
    public void asociarAutomovil(int parteId, String autoModelo, int autoAnio);
}

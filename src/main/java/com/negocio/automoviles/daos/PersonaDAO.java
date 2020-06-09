package com.negocio.automoviles.daos;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import com.negocio.automoviles.models.Persona;

public interface PersonaDAO {
    public void setDataSource(DataSource ds);
    public Persona getPersona(int cedula);
    public List<Persona> getPersonas();
    public boolean existeCedula(int cedula);
    public void agregarPersona(Persona persona);
    public boolean existeTelefono(String telefono, int cedula);
    public List<String> getTelefonos(int cedula);
    public void agregarTelefono(int cedula, String telefono);
    public void borrarTelefono(int cedula, String telefono);
}

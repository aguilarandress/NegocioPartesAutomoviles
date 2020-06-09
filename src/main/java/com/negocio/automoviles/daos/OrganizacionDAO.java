package com.negocio.automoviles.daos;

import com.negocio.automoviles.models.Organizacion;
import com.negocio.automoviles.models.Persona;

import javax.sql.DataSource;
import java.util.List;

public interface OrganizacionDAO {
    public void setDataSource(DataSource ds);
    public Organizacion getOrganizacion(Long cedula);
    public List<Organizacion> getOrganizaciones();
    public boolean existeCedula(Long cedula);
    public void agregarOrganizacion(Organizacion organizacion);
}

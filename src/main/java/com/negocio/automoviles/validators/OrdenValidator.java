package com.negocio.automoviles.validators;

import com.negocio.automoviles.database.DatabaseSource;
import com.negocio.automoviles.jdbc.OrganizacionJDBC;
import com.negocio.automoviles.jdbc.PersonaJDBC;
import com.negocio.automoviles.models.Persona;
import com.negocio.automoviles.models.Organizacion;

import java.util.List;

/**
 * Clase para validar ordenes
 */
public class OrdenValidator {
    /**
     * Verifica si la cedula del cliente es valida
     * @param cedula La cedula que se desea validar
     * @return El id del cliente
     */
    public static int validarClienteCedula(long cedula) {
        // Obtener personas
        PersonaJDBC personaJDBC = new PersonaJDBC();
        personaJDBC.setDataSource(DatabaseSource.getDataSource());
        List<Persona> personas = personaJDBC.getPersonas();
        // Verficar si la hay alguna cedula que coincida
        for (Persona persona : personas) {
            if (persona.getCedula() == cedula) {
                return persona.getId();
            }
        }
        // Obtener organizaciones
        OrganizacionJDBC organizacionJDBC = new OrganizacionJDBC();
        organizacionJDBC.setDataSource(DatabaseSource.getDataSource());
        List<Organizacion> organizacions = organizacionJDBC.getOrganizaciones();
        // Verificar si hay alguna organizacion que coincida
        for (Organizacion organizacion : organizacions) {
            if (organizacion.getCedula() == cedula) {
                return organizacion.getId();
            }
        }
        return -1;
    }
}

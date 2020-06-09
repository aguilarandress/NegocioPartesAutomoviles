package com.negocio.automoviles.validators;

import com.negocio.automoviles.database.DatabaseSource;
import com.negocio.automoviles.jdbc.OrganizacionJDBC;
import com.negocio.automoviles.jdbc.PersonaJDBC;
import com.negocio.automoviles.models.Organizacion;

import java.util.ArrayList;

/**
 * Clase para validad una organizacion
 */
public class OrganizacionValidator {
    public static ArrayList<String> validarOrganizacion(Organizacion organizacion,boolean esClienteNuevo)
    {
        ArrayList<String> errores = new ArrayList<String>();
        // Crear acceso a las organizaciones
        OrganizacionJDBC organizaJDBC = new OrganizacionJDBC();
        organizaJDBC.setDataSource(DatabaseSource.getDataSource());
        // Revisar cedula
        if (esClienteNuevo && (organizacion.getCedula() < 999999999 || organizacion.getCedula() > 10000000000L || organizaJDBC.existeCedula(organizacion.getCedula()))) {
            errores.add("Cedula invalida");
        }
        // Revisar nombre
        if (organizacion.getNombre().equals("") || organizacion.getNombre().length() > 30) {
            errores.add("Nombre invalido");
        }
        // Revisar direccion
        if (organizacion.getDireccion().equals("") || organizacion.getDireccion().length() > 30) {
            errores.add("Direccion invalida");
        }
        // Revisar ciudad
        if (organizacion.getCiudad().equals("") || organizacion.getCiudad().length() > 15) {
            errores.add("Ciudad invalida");
        }
        // Revisar nombre encargado
        if(organizacion.getE_nombre().equals("")|| organizacion.getE_nombre().length()>20) {
            errores.add("Nombre encargado inválido");
        }
        // Revisar cargo encargado
        if(organizacion.getE_cargo().equals("")|| organizacion.getE_cargo().length()>10) {
            errores.add("Cargo encargado invalido");
        }
        //Revisar telefono encargado
        if(organizacion.getE_telefono().equals("")|| organizacion.getE_telefono().length()>9)
        {
            errores.add("Telefono encargado inválido");
        }
        return errores;
    };
}


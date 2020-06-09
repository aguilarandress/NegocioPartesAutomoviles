package com.negocio.automoviles.validators;

import com.negocio.automoviles.database.DatabaseSource;
import com.negocio.automoviles.jdbc.PersonaJDBC;
import com.negocio.automoviles.models.Persona;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Clase para validar una persona
 */
public class PersonaValidator {
    /**
     * Valida una persona
     * @param persona La persona que se desea validar
     * @return Un ArrayList con los errores
     */
    public static ArrayList<String> validarPersona(Persona persona, boolean esClienteNuevo) {
        ArrayList<String> errores = new ArrayList<String>();
        // Crear acceso a las personas
        PersonaJDBC personaJDBC = new PersonaJDBC();
        personaJDBC.setDataSource(DatabaseSource.getDataSource());
        // Revisar cedula
        if (esClienteNuevo && (persona.getCedula() < 99999999 || persona.getCedula() > 1000000000
                || personaJDBC.existeCedula(persona.getCedula()))) {
            errores.add("Cedula invalida");
        }
        // Revisar nombre
        if (persona.getNombre().equals("") || persona.getNombre().length() > 30) {
            errores.add("Nombre invalido");
        }
        // Revisar direccion
        if (persona.getDireccion().equals("") || persona.getDireccion().length() > 30) {
            errores.add("Direccion invalida");
        }
        // Revisar ciudad
        if (persona.getCiudad().equals("") || persona.getCiudad().length() > 15) {
            errores.add("Ciudad invalida");
        }
        return errores;
    }

    /**
     * Valida el telefono de una persona
     * @param telefono El telefono que se desea validar
     * @return Si el telefono es valido o no
     */
    public static boolean validarTelefono(String telefono) {
        String patternStr = "\\d{4}(-?)\\d{4}";
        Pattern pattern = Pattern.compile(patternStr);
        return pattern.matcher(telefono).matches();
    }

}

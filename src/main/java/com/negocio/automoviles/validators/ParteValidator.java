package com.negocio.automoviles.validators;

import com.negocio.automoviles.database.DatabaseSource;
import com.negocio.automoviles.jdbc.PartesJDBC;
import com.negocio.automoviles.models.HolderPartProvedor;

import java.util.ArrayList;

public class ParteValidator {
    public static ArrayList<String> validarParte(String nombre){
        ArrayList<String> errores = new ArrayList<String>();
        PartesJDBC partesJDBC=new PartesJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        if(partesJDBC.existeParte(nombre))
        {
            errores.add("Esa parte ya existe en el sistema");
        }
        if(nombre.length()<1)
        {
            errores.add("Nombre inválido");
        }
        return  errores;
    }
    public static ArrayList<String> validarRelacion (HolderPartProvedor holder){
        ArrayList<String> errores = new ArrayList<String>();
        if(holder.getPrecio() <=0f || holder.getPrecio() >2147483647f)
        {
            errores.add("Precio inválido");
        }
        if(holder.getPorcentaje_ganancia() <=0f || holder.getPorcentaje_ganancia() >999.99f)
        {
            errores.add("Porcentaje de ganancia inválido");
        }


        return errores;
    }
}

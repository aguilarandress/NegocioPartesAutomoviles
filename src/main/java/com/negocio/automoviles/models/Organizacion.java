package com.negocio.automoviles.models;

import com.sun.org.apache.xpath.internal.operations.Or;

public class Organizacion extends Cliente {
    private long cedula;
    private String E_nombre;
    private String E_cargo;
    private String E_telefono;
    public Organizacion(){

    }
    public long getCedula()
    {
        return cedula;
    }

    public void setCedula(long cedula)
    {
        this.cedula=cedula;
    }

    public String getE_nombre() { return E_nombre; }

    public void setE_nombre(String e_nombre) { E_nombre = e_nombre; }

    public String getE_cargo() { return E_cargo; }

    public void setE_cargo(String e_cargo) { E_cargo = e_cargo; }

    public String getE_telefono() { return E_telefono; }

    public void setE_telefono(String e_telefono) { E_telefono = e_telefono; }
}

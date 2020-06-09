package com.negocio.automoviles.models;

import java.util.ArrayList;
import java.util.List;

public class Persona extends Cliente {
    private int cedula;
    private List<String> telefonos;

    public Persona() {

    }

    public int getCedula() {
        return cedula;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }
}

package com.negocio.automoviles.models;

public class ParteProvedor {
    private int parteID;
    private int provedorID;
    private float precio;
    private float porcentaje_ganancia;

    public int getParteID() {
        return parteID;
    }

    public void setParteID(int parteID) {
        this.parteID = parteID;
    }

    public int getProvedorID() {
        return provedorID;
    }

    public void setProvedorID(int provedorID) {
        this.provedorID = provedorID;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPorcentaje_ganancia() {
        return porcentaje_ganancia;
    }

    public void setPorcentaje_ganancia(float porcentaje_ganancia) {
        this.porcentaje_ganancia = porcentaje_ganancia;
    }
}

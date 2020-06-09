package com.negocio.automoviles.models;

/**
 * Modelo de Cliente
 * @author Andres Aguilar
 * @author Carlos Varela
 */
public class Cliente {

    protected int id;
    protected String nombre;
    protected String direccion;
    protected String ciudad;
    protected String estado;

    public Cliente () {

    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

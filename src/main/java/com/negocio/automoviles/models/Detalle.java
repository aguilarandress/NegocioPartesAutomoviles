package com.negocio.automoviles.models;

public class Detalle {
    private int cantidad;
    private int ProveedorID;
    private int ParteID;
    private int ConsecutivoOrden;
    private double precio;
    private double porcentaje_ganancia;
    private String nombre_parte;
    private String nombre_provedor;

    public String getNombre_provedor() { return nombre_provedor; }

    public void setNombre_provedor(String nombre_provedor) { this.nombre_provedor = nombre_provedor; }

    public String getNombre_parte() { return nombre_parte; }

    public void setNombre_parte(String nombre_parte) { this.nombre_parte = nombre_parte; }

    public double getPorcentaje_ganancia() { return porcentaje_ganancia; }

    public void setPorcentaje_ganancia(double porcentaje_ganancia) { this.porcentaje_ganancia = porcentaje_ganancia; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getProveedorID() {
        return ProveedorID;
    }

    public void setProveedorID(int proveedorID) {
        ProveedorID = proveedorID;
    }

    public int getParteID() {
        return ParteID;
    }

    public void setParteID(int parteID) {
        ParteID = parteID;
    }

    public int getConsecutivoOrden() {
        return ConsecutivoOrden;
    }

    public void setConsecutivoOrden(int consecutivoOrden) {
        ConsecutivoOrden = consecutivoOrden;
    }
}

package com.negocio.automoviles.models;

import com.negocio.automoviles.database.DatabaseSource;
import com.negocio.automoviles.jdbc.PartesJDBC;
import com.negocio.automoviles.jdbc.ProvedoresJDBC;

import java.util.List;

public class HolderPartProvedor {
    private String parte;
    private String proveedor;
    private float precio;
    private float porcentaje_ganancia;
    private int parteID;
    private int provedorID;
    private String relacion;



    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

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

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public static List<HolderPartProvedor> FindMissingInfo(List<HolderPartProvedor> relaciones)
    {
        PartesJDBC partesJDBC=new PartesJDBC();
        ProvedoresJDBC provedoresJDBC=new ProvedoresJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        provedoresJDBC.setDataSource(DatabaseSource.getDataSource());
        for (HolderPartProvedor holder:relaciones
             ) {
            Parte parte=partesJDBC.getParte(holder.getParteID());
            Provedor provedor=provedoresJDBC.getProvedor(holder.getProvedorID());
            holder.setParte(parte.getNombre());
            holder.setProveedor(provedor.getNombre());
        }


        return relaciones;
    }


}

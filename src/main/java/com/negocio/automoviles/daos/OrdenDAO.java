package com.negocio.automoviles.daos;

import com.negocio.automoviles.models.Detalle;
import com.negocio.automoviles.models.Orden;

import javax.sql.DataSource;
import java.util.List;

public interface OrdenDAO {

    public void setDataSource(DataSource ds);
    public boolean Parte_en_orden (int id);
    public List<Orden> getOrdenes();
    public Orden getOrden(int consecutivo);
    public List<Detalle> getDetalles(int consecutivo);
    public void crearOrdenNueva(int idCliente, String fecha);
    public boolean existeDetalle(int parteid,int provedorid, int consecutivo);
    public void addCantidad(int parteid,int provedorid, int cantidad,  int consecutivo);
    public void agregarDetalle(Detalle detalle,int consecutivo);
    public void updateTotal(int consecutivo, double total);

}

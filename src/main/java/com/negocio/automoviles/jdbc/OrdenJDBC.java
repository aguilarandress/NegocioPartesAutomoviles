package com.negocio.automoviles.jdbc;

import com.negocio.automoviles.daos.OrdenDAO;
import com.negocio.automoviles.mappers.DetallesMapper;
import com.negocio.automoviles.mappers.DetallesMapperParte;
import com.negocio.automoviles.mappers.DetallesMapperSimple;
import com.negocio.automoviles.mappers.OrdenMapper;
import com.negocio.automoviles.models.Detalle;
import com.negocio.automoviles.models.Orden;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class OrdenJDBC implements OrdenDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource=ds;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean Parte_en_orden(int id) {
        String query="SELECT parte_id, provedor_id FROM detalles" +
                " WHERE parte_id = ? ";

        List<Detalle> detalles=jdbcTemplateObject.query(query,new Object[]{id},new DetallesMapperSimple());
        return detalles.size()>0;
    }

    @Override
    public List<Orden> getOrdenes() {
        String query="SELECT ordenes.consecutivo , ordenes.fecha, ordenes.monto_total ,ordenes.cliente_id ,clientes.nombre " +
                "FROM ordenes INNER JOIN clientes" +
                " On ordenes.cliente_id = clientes.id";
        List<Orden> ordenes= jdbcTemplateObject.query(query,new OrdenMapper());

        return ordenes;
    }

    @Override
    public Orden getOrden(int consecutivo) {
        String query="SELECT ordenes.consecutivo , ordenes.fecha, ordenes.monto_total ,ordenes.cliente_id ,clientes.nombre " +
                "FROM ordenes INNER JOIN clientes" +
                " On ordenes.cliente_id = clientes.id" +
                " WHERE ordenes.consecutivo = ?";
        Orden orden= jdbcTemplateObject.queryForObject(query,new Object[]{consecutivo},new OrdenMapper());

        return orden;

    }

    @Override
    public List<Detalle> getDetalles(int consecutivo) {
        String query="SELECT detalles.parte_id,proveido_por.parte_id,detalles.provedor_id,cantidad,detalles.consecutivo_orden, " +
                "proveido_por.precio, proveido_por.porcentaje_ganancia, provedores.id, " +
                "partes.nombre AS pnombre , partes.id,provedores.nombre AS prnombre FROM detalles " +
                "INNER JOIN proveido_por on proveido_por.parte_id = detalles.parte_id AND proveido_por.provedor_id = detalles.provedor_id " +
                "INNER JOIN partes on detalles.parte_id = partes.id " +
                "INNER JOIN provedores on detalles.provedor_id = provedores.id " +
                "WHERE detalles.consecutivo_orden = ? ";
        List<Detalle> detalles=jdbcTemplateObject.query(query,new Object[]{consecutivo},new DetallesMapperParte());
        return detalles;
    }

    @Override
    public void crearOrdenNueva(int idCliente, String fecha) {
        String query = "INSERT INTO ordenes(cliente_id, monto_total, fecha) VALUES(?, ?, ?)";
        jdbcTemplateObject.update(query, new Object[]{idCliente, 0, fecha});
    }
    public boolean  existeDetalle(int parteid,int provedorid,int consecutivo)
    {
        String query ="SELECT detalles.parte_id , detalles.provedor_id FROM detalles"+
                " WHERE detalles.parte_id = ? AND detalles.provedor_id = ? AND detalles.consecutivo_orden = ? ";
        List<Detalle> detalles=jdbcTemplateObject.query(query,new Object[]{parteid,provedorid,consecutivo},new DetallesMapperSimple());
        return detalles.size()>0;
    }


    @Override
    public void addCantidad(int parteid, int provedorid, int cantidad,  int consecutivo) {
        String query="SELECT detalles.parte_id,proveido_por.parte_id,detalles.provedor_id,cantidad,detalles.consecutivo_orden, " +
                "proveido_por.precio, proveido_por.porcentaje_ganancia, provedores.id, " +
                "partes.nombre AS pnombre , partes.id,provedores.nombre AS prnombre FROM detalles " +
                "INNER JOIN proveido_por on proveido_por.parte_id = detalles.parte_id AND proveido_por.provedor_id = detalles.provedor_id  " +
                "INNER JOIN partes on detalles.parte_id = partes.id " +
                "INNER JOIN provedores on detalles.provedor_id = provedores.id " +
                "WHERE detalles.parte_id = ? AND detalles.provedor_id = ? AND detalles.consecutivo_orden = ? ";

        Detalle detalle=jdbcTemplateObject.queryForObject(query,new Object[]{parteid,provedorid, consecutivo},new DetallesMapperParte());

        detalle.setCantidad(detalle.getCantidad()+cantidad);
        query= "UPDATE detalles SET cantidad = ? WHERE detalles.parte_id = ? AND detalles.provedor_id = ? AND detalles.consecutivo_orden = ?";
        jdbcTemplateObject.update(query,new Object[]{detalle.getCantidad(),detalle.getParteID(),detalle.getProveedorID(),consecutivo});

    }

    @Override
    public void agregarDetalle(Detalle detalle,int consecutivo) {
        List<Detalle> detalles= this.getDetalles(consecutivo);
        String query= "INSERT INTO detalles(detalles.consecutivo_orden,detalles.numero_linea,detalles.cantidad,detalles.parte_id,detalles.provedor_id)" +
                " VALUES(?, ?, ?, ?, ?) ";
        jdbcTemplateObject.update(query,new Object[]{consecutivo,detalles.size()+1,detalle.getCantidad(),detalle.getParteID(),detalle.getProveedorID()});


    }

    @Override
    public void updateTotal(int consecutivo, double total) {
        String query= "UPDATE ordenes SET monto_total = ? WHERE ordenes.consecutivo = ?";
        jdbcTemplateObject.update(query,new Object[]{total+total*0.13f, consecutivo});
    }


}

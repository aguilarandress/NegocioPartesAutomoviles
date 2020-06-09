package com.negocio.automoviles.daos;

import javax.sql.DataSource;
import java.util.List;
import com.negocio.automoviles.models.Cliente;

public interface ClienteDAO {
    public void setDataSource(DataSource ds);
    public int agregarCliente(String nombre, String direccion, String ciudad, String estado);
    public void modificarCliente(int id, String nombre, String direccion, String ciudad, String estado);
    public void suspenderCliente(int id);
    public void activarCliente(int id);
//    public List<Cliente> getClientesActivos();
    public List<String> getEstados();
    public boolean verificarClienteSuspendido(int id);
}


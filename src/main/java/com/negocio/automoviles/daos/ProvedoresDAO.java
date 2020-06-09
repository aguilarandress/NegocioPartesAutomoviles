package com.negocio.automoviles.daos;

import com.negocio.automoviles.models.Provedor;

import javax.sql.DataSource;
import java.util.List;

public interface ProvedoresDAO {
    public void setDataSource(DataSource ds);
    public Provedor getProvedor(int ID);
    public List<Provedor> getProvedores();
    public int getIDProvedor(String nombre );
}

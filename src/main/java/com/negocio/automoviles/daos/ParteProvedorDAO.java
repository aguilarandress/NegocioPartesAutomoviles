package com.negocio.automoviles.daos;

import com.negocio.automoviles.models.HolderPartProvedor;

import javax.sql.DataSource;
import javax.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;

public interface ParteProvedorDAO {
    public void setDataSource(DataSource ds);
    public List<HolderPartProvedor> getRelaciones();
    public boolean existeRelacion(int parteID,int provedorID);
    public void modificarRelacion(int IDParte,int IDProve,float precio,float porcentaje_ganancia);
}

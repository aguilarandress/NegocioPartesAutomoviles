package com.negocio.automoviles.daos;

import com.negocio.automoviles.models.Detalle;
import com.negocio.automoviles.models.HolderPartProvedor;
import com.negocio.automoviles.models.Parte;
import javax.sql.DataSource;
import java.util.List;

/**
 * Partes Data Access Object
 */
public interface PartesDAO {
    public void setDataSource(DataSource ds);
    public List<Parte> getPartes();
    public Parte getParte(int id);
    public List<Parte> getPartesByModeloAnio(String modelo, int anio);
    public void agregarParte(Parte parte,int Marcaid,int Fabricanteid);
    public List<String> getMarcasP();
    public List<String> getFabricantresP();
    public int getIDMarcasP(String nombre);
    public int getIDFabricantesP(String nombre);
    public boolean existeParte(String nombre);
    public void relacionParteProvedor(HolderPartProvedor info,int parteID,int provedorID);
    public int getIDParte(String nombreP);
    public void deleteParte(int id);
    public List<Detalle> getPartesAsociadasPorNombre(String nombre);
}

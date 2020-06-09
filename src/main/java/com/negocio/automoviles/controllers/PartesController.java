package com.negocio.automoviles.controllers;

import com.negocio.automoviles.database.DatabaseSource;
import com.negocio.automoviles.jdbc.OrdenJDBC;
import com.negocio.automoviles.jdbc.ParteProvedorJDBC;
import com.negocio.automoviles.jdbc.AutomovilesJDBC;
import com.negocio.automoviles.jdbc.PartesJDBC;
import com.negocio.automoviles.jdbc.ProvedoresJDBC;
import com.negocio.automoviles.models.*;
import com.negocio.automoviles.validators.ParteValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para partes
 *
 */
@Controller
public class PartesController
{

    /**
     * Carga la pagina principal para partes
     * @param model El modelo para cargar datos
     * @return La pagina principal de partes
     */
    @RequestMapping(value = "/partes", method = RequestMethod.GET)
    public String partes(Model model,
                         @RequestParam(value = "modelo", required = false) String modelo,
                         @RequestParam(value = "anio", required = false) Integer anio)
    {
        PartesJDBC partesJDBC = new PartesJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        // Revisar si se esta buscando por modelo y anio
        List<Parte> partes;
        if (modelo != null && anio != null) {
            partes = partesJDBC.getPartesByModeloAnio(modelo, anio);
        } else {
            partes = partesJDBC.getPartes();
        }
        // Obtener anios disponibles
        AutomovilesJDBC automovilesJDBC = new AutomovilesJDBC();
        automovilesJDBC.setDataSource(DatabaseSource.getDataSource());
        model.addAttribute("modelos", automovilesJDBC.getModelosDisponibles());
        model.addAttribute("anios", automovilesJDBC.getAniosDisponinles());
        model.addAttribute("partes", partes);
        return "partes";
    }

    /**
     * Carga la pagina para los detalles de una parte
     * @param model El modelo para cargar datos
     * @param id El id de la parte
     * @return La pagina de detalles para la parte
     */
    @RequestMapping(value = "/partes/{id}", method = RequestMethod.GET)
    public String detallesParte(Model model, @PathVariable(value = "id") int id) {
        // Acceso a las partes
        PartesJDBC partesJDBC = new PartesJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        // Acceso a los automoviles
        AutomovilesJDBC automovilesJDBC = new AutomovilesJDBC();
        automovilesJDBC.setDataSource(DatabaseSource.getDataSource());
        model.addAttribute("modelos", automovilesJDBC.getModelosDisponibles());
        model.addAttribute("anios", automovilesJDBC.getAniosDisponinles());
        model.addAttribute("parte", partesJDBC.getParte(id));
        return "detallesparte";
    }


    /**
     * Asocia un automovil con la parte
     * @param id El id de la parte
     * @param modelo Modelo del automovil
     * @param anio Anio del automovil
     * @param redirectAttributes Atributos para redirigir la pagina
     * @return Redirigir a la pagina de la parte
     */
    @RequestMapping(value = "/partes/{id}/asociar/automoviles", method = RequestMethod.POST)
    public String asociarAutomovil(@PathVariable(value = "id") int id,
                                   @RequestParam String modelo,
                                   @RequestParam int anio,
                                   RedirectAttributes redirectAttributes)
    {
        // Acceso a los automoviles
        AutomovilesJDBC automovilesJDBC = new AutomovilesJDBC();
        automovilesJDBC.setDataSource(DatabaseSource.getDataSource());
        // Verificar si existe el automovil
        if (!automovilesJDBC.existeAutomovil(modelo, anio)) {
            redirectAttributes.addFlashAttribute("errors", new String[]{"No existe dicho automovil"});
            return "redirect:/partes/" + id;
        }
        // Verificar si ya existe la asociacion
        if (automovilesJDBC.existeAsociacion(id, modelo, anio)) {
            redirectAttributes.addFlashAttribute("errors", new String[]{"Esta asociacion ya existe"});
            return "redirect:/partes/" + id;
        }
        // Asociar el automovil
        automovilesJDBC.asociarAutomovil(id, modelo, anio);
        redirectAttributes.addFlashAttribute("success_msg", "Automovil asociado");
        return "redirect:/partes/" + id;
    }
 

    /**
     * Carga la plantilla para añadir partes
     * @param model
     * @return
     */
    @RequestMapping(value = "/partes/add", method = RequestMethod.GET)
    public String AddParte(Model model)
    {
        if (!model.containsAttribute("parte")) {
        model.addAttribute("parte", new Parte());
    }
        PartesJDBC partesJDBC= new PartesJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        List<String> marcas=partesJDBC.getMarcasP();
        List<String> fabricantes=partesJDBC.getFabricantresP();
        model.addAttribute("marcas",marcas);
        model.addAttribute("fabricantes",fabricantes);
        return "addparte";
    }

    /**
     * Procesa la adición de la parte
     * @param parte
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/partes/add",method = RequestMethod.POST)
    public String AddParteConfirmar(@ModelAttribute Parte parte, RedirectAttributes redirectAttributes)
    {
        ArrayList<String>errores= ParteValidator.validarParte(parte.getNombre());
        if(errores.size()>0)
        {
            redirectAttributes.addFlashAttribute("errors", errores);
            redirectAttributes.addFlashAttribute("parte", parte);
            return "redirect:/partes/add";
        }
        PartesJDBC partesJDBC = new PartesJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        int idM=partesJDBC.getIDMarcasP(parte.getMarca());
        int idF=partesJDBC.getIDFabricantesP(parte.getFabricante());
        partesJDBC.agregarParte(parte,idM,idF);
        redirectAttributes.addFlashAttribute("success_msg", "Parte agregada");
        return "redirect:/partes";

    }

    /**
     * Carga la plantilla para crear relaciones entre partes y provedores
     * @param model
     * @return
     */
    @RequestMapping(value = "/partes/join",method = RequestMethod.GET)
    public String JoinParteProv(Model model)
    {
        if (!model.containsAttribute("holder")) {
            model.addAttribute("holder", new HolderPartProvedor());
        }
        PartesJDBC partesJDBC=new PartesJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        ProvedoresJDBC provedoresJDBC= new ProvedoresJDBC();
        provedoresJDBC.setDataSource(DatabaseSource.getDataSource());
        List<Parte> partes= partesJDBC.getPartes();
        List<Provedor> provedores= provedoresJDBC.getProvedores();
        model.addAttribute("partes",Parte.getNombres(partes));
        model.addAttribute("provedores",Provedor.getNombres(provedores));
        return "joinParte";
    }

    /**
     * Procesa la solicitud de relacion parte-provedor
     * @param holder
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="/partes/join",method = RequestMethod.POST)
    public String ProcesarJoinParteProv(@ModelAttribute HolderPartProvedor holder, RedirectAttributes redirectAttributes)
    {
        ArrayList<String>errores= ParteValidator.validarRelacion(holder);
        if(errores.size()>0)
        {
            redirectAttributes.addFlashAttribute("errors", errores);
            redirectAttributes.addFlashAttribute("holder", holder);
            return "redirect:/partes/join";
        }
        PartesJDBC partesJDBC=new PartesJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        ProvedoresJDBC provedoresJDBC= new ProvedoresJDBC();
        provedoresJDBC.setDataSource(DatabaseSource.getDataSource());
        ParteProvedorJDBC parteProvedorJDBC= new ParteProvedorJDBC();
        parteProvedorJDBC.setDataSource(DatabaseSource.getDataSource());
        int idParte= partesJDBC.getIDParte(holder.getParte());
        int idProvedor= provedoresJDBC.getIDProvedor(holder.getProveedor());
        if(parteProvedorJDBC.existeRelacion(idParte,idProvedor))
        {
            errores.add("Esa relación ya existe");
            redirectAttributes.addFlashAttribute("errors", errores);
            redirectAttributes.addFlashAttribute("holder", holder);
            return "redirect:/partes/join";

        }
        partesJDBC.relacionParteProvedor(holder,idParte,idProvedor);
        redirectAttributes.addFlashAttribute("success_msg", "Afiliación creada");

        return "redirect:/partes";
    }

    /**
     * Carga la plantilla para editar una relación
     * @param model
     * @return
     */
    @RequestMapping(value = "/partes/joinEdit",method = RequestMethod.GET)
    public String modificarRelacion(Model model)
    {
        if (!model.containsAttribute("holder")) {
            model.addAttribute("holder", new HolderPartProvedor());
        }
        ParteProvedorJDBC parteProvedorJDBC= new ParteProvedorJDBC();
        parteProvedorJDBC.setDataSource(DatabaseSource.getDataSource());
        List<HolderPartProvedor> relaciones =parteProvedorJDBC.getRelaciones();
        HolderPartProvedor.FindMissingInfo(relaciones);
        model.addAttribute("relaciones",relaciones);
        return "editRelacion";
    }
    @RequestMapping(value = "/partes/joinEdit",method = RequestMethod.POST)
    public String procesarModificarRelacion(@ModelAttribute HolderPartProvedor holder, RedirectAttributes redirectAttributes)
    {
        ArrayList<String>errores= ParteValidator.validarRelacion(holder);
        if(errores.size()>0)
        {
            redirectAttributes.addFlashAttribute("errors", errores);
            redirectAttributes.addFlashAttribute("holder", holder);
            return "redirect:/partes/joinEdit";
        }
        String [] parte_provedor= holder.getRelacion().split("-");
        ParteProvedorJDBC parteProvedorJDBC= new ParteProvedorJDBC();
        parteProvedorJDBC.setDataSource(DatabaseSource.getDataSource());
        List<HolderPartProvedor> relaciones =parteProvedorJDBC.getRelaciones();
        HolderPartProvedor.FindMissingInfo(relaciones);
        for (HolderPartProvedor relacion:relaciones
             ) {

            if(relacion.getParte().equals(parte_provedor[0])&& relacion.getProveedor().equals(parte_provedor[1]))
            {

                parteProvedorJDBC.modificarRelacion(relacion.getParteID(), relacion.getProvedorID(), holder.getPrecio(), holder.getPorcentaje_ganancia());
            }

        }
        redirectAttributes.addFlashAttribute("success_msg", "Afiliación modificada");
        return "redirect:/partes";
    }
    @RequestMapping(value = "/partes/eliminar",method = RequestMethod.POST)
    public String eliminarParte(@ModelAttribute Parte parte, RedirectAttributes redirectAttributes)
    {
        OrdenJDBC ordenJDBC= new OrdenJDBC();
        ordenJDBC.setDataSource(DatabaseSource.getDataSource());
        PartesJDBC partesJDBC = new PartesJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        int id= partesJDBC.getIDParte(parte.getNombre());
        //Se comprueba que la parte no exista en una orden
        if(ordenJDBC.Parte_en_orden(id))
        {
            redirectAttributes.addFlashAttribute("errors", new String[] {"Esta parte ya se encuentra listada en una orden"});
            return  "redirect:/partes/"+id;
        }
        partesJDBC.deleteParte(id);
        redirectAttributes.addFlashAttribute("success_msg", "Parte eliminada");
        return "redirect:/partes";
    }
}

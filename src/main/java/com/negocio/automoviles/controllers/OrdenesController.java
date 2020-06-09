package com.negocio.automoviles.controllers;

import com.negocio.automoviles.database.DatabaseSource;
import com.negocio.automoviles.jdbc.ClientJDBC;
import com.negocio.automoviles.jdbc.OrdenJDBC;
import com.negocio.automoviles.models.Detalle;
import com.negocio.automoviles.models.Orden;
import com.negocio.automoviles.models.Organizacion;
import com.negocio.automoviles.validators.OrdenValidator;
import com.negocio.automoviles.jdbc.PartesJDBC;
import com.negocio.automoviles.models.Parte;
import com.negocio.automoviles.validators.OrdenValidator;
import com.negocio.automoviles.models.Orden;
import com.negocio.automoviles.models.Detalle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para ordenes
 */
@Controller
public class OrdenesController {

    /**
     * Carga la página de ordenes
     * @param model
     * @return
     */
    @RequestMapping(value = "/ordenes", method = RequestMethod.GET)
    public String ordenes(Model model)
    {
        OrdenJDBC ordenJDBC= new OrdenJDBC();
        ordenJDBC.setDataSource(DatabaseSource.getDataSource());
        List<Orden> ordenes= ordenJDBC.getOrdenes();
        model.addAttribute("ordenes",ordenes);
        return "ordenes";
    }

    /**
     * Agregar una orde
     * @return Carga el formulario para agregar una orden
     */
    @RequestMapping(value = "/ordenes/add", method = RequestMethod.GET)
    public String addOrden() {
        return "addorden";
    }

    /**
     * Procesa una orden nueva
     * @param cedula La cedula del cliente
     * @param fecha La fecha de la orden
     * @param redirectAttributes Los atributos para redirigir
     * @return La pagina de ordenes
     */
    @RequestMapping(value="/ordenes/add", method = RequestMethod.POST)
    public String procesarAddOrden(@RequestParam int cedula, @RequestParam String fecha, RedirectAttributes redirectAttributes) {
        ArrayList<String> errors = new ArrayList<String>();
        // Validar la cedula
        int idCliente = OrdenValidator.validarClienteCedula(cedula);
        ClientJDBC clientJDBC = new ClientJDBC();
        clientJDBC.setDataSource(DatabaseSource.getDataSource());
        if (idCliente == -1) {
            errors.add("Ningun cliente posee esta cedula");
        }
        if (fecha.equals("")) {
            errors.add("Por favor ingrese una fecha");
        }
        // Revisar si hay errores
        if (errors.size() > 0) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/ordenes/add";
        }
        // Verificar si el cliente esta suspendido
        if (clientJDBC.verificarClienteSuspendido(idCliente)) {
            errors.add("Este cliente se encuentra suspendido");
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/ordenes/add";
        }
        // Procesar la orden
        OrdenJDBC ordenJDBC = new OrdenJDBC();
        ordenJDBC.setDataSource(DatabaseSource.getDataSource());
        ordenJDBC.crearOrdenNueva(idCliente, fecha);
        // Activar cliente
        clientJDBC.activarCliente(idCliente);
        redirectAttributes.addFlashAttribute("success_msg", "Orden creada");
        // TODO: Redirigir a la pagina principal de ordenes
        return "redirect:/ordenes";

    }

    @RequestMapping(value= "/ordenes/{consecutivo}",method = RequestMethod.GET)
    public String detallesOrden(@PathVariable(value = "consecutivo") int consecutivo,
                                @RequestParam(value = "nombre_parte", required = false) String nombreParte,
                                Model model) {
        PartesJDBC partesJDBC = new PartesJDBC();
        partesJDBC.setDataSource(DatabaseSource.getDataSource());
        // Revisar si se estan buscando proveedores
        List<Detalle> afiliaciones = new ArrayList<>();
        if (nombreParte != null) {
            afiliaciones = partesJDBC.getPartesAsociadasPorNombre(nombreParte);
        }
        OrdenJDBC ordenJDBC = new OrdenJDBC();
        ordenJDBC.setDataSource(DatabaseSource.getDataSource());
        Orden orden = ordenJDBC.getOrden(consecutivo);
        List<Detalle> detalles = ordenJDBC.getDetalles(consecutivo);
        double total = 0;
        for (Detalle detalle: detalles
             ) {
            total= total + (detalle.getPrecio()+(detalle.getPrecio()*(detalle.getPorcentaje_ganancia()/100)))*detalle.getCantidad();

        }
        if(total>0)
        {
            ordenJDBC.updateTotal(consecutivo,total);
        }
        model.addAttribute("total",total);
        model.addAttribute("orden", orden);
        model.addAttribute("detalles", detalles);
      if (!model.containsAttribute("afiliaciones")) {
            model.addAttribute("afiliaciones", afiliaciones);
        }
        return "detallesOrden";
    }

    @RequestMapping(value= "ordenes/{consecutivo}/detalles/add",method = RequestMethod.POST)
    public String procesarDetalle(@PathVariable(value = "consecutivo")int consecutivo ,@RequestParam(value="cantidad") int cantidad,@RequestParam(value="parte_id") int parteid,@RequestParam(value="provedor_id") int provedorid,RedirectAttributes redirectAttributes)
    {
        OrdenJDBC ordenJDBC = new OrdenJDBC();
        ordenJDBC.setDataSource(DatabaseSource.getDataSource());
        Detalle detalle= new Detalle();
        detalle.setCantidad(cantidad);
        detalle.setParteID(parteid);
        detalle.setProveedorID(provedorid);
        if(ordenJDBC.existeDetalle(detalle.getParteID(),detalle.getProveedorID(),consecutivo))
        {
            ordenJDBC.addCantidad(detalle.getParteID(),detalle.getProveedorID(),detalle.getCantidad(), consecutivo);
        }
        else
        {
            ordenJDBC.agregarDetalle(detalle, consecutivo);
        }
        redirectAttributes.addFlashAttribute("success_msg", "Detalle agregado");

        return "redirect:/ordenes/"+consecutivo;
    }
}

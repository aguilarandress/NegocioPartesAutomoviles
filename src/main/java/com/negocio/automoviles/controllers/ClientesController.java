package com.negocio.automoviles.controllers;

import com.negocio.automoviles.database.DatabaseSource;
import com.negocio.automoviles.jdbc.ClientJDBC;
import com.negocio.automoviles.jdbc.OrganizacionJDBC;
import com.negocio.automoviles.jdbc.PersonaJDBC;
import com.negocio.automoviles.models.Cliente;
import com.negocio.automoviles.models.Organizacion;
import com.negocio.automoviles.validators.OrganizacionValidator;
import com.negocio.automoviles.validators.PersonaValidator;
import com.negocio.automoviles.models.Persona;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para clientes
 */
@Controller
public class ClientesController {

    /**
     * Pagina principal de clientes
     * @param model
     * @return
     */
    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public String clientes(Model model) {
        PersonaJDBC personaJDBC = new PersonaJDBC();
        personaJDBC.setDataSource(DatabaseSource.getDataSource());
        List<Persona> personas = personaJDBC.getPersonas();
        model.addAttribute("personas", personas);

        OrganizacionJDBC organizacionJDBC= new OrganizacionJDBC();
        organizacionJDBC.setDataSource(DatabaseSource.getDataSource());
        List<Organizacion> organizaciones = organizacionJDBC.getOrganizaciones();
        model.addAttribute("organizaciones",organizaciones);
        return "clientes";
    }

    /**
     * Cargar formulario para agregar clientes
     * @param model
     * @return
     */
    @RequestMapping(value="/clientes/personas/add", method = RequestMethod.GET)
    public String agregarPersona(Model model) {
        if (!model.containsAttribute("persona")) {
            model.addAttribute("persona", new Persona());
        }
        return "addpersona";
    }

    /**
     * Procesar el cliente nuevo
     * @param persona
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/clientes/personas/add", method = RequestMethod.POST)
    public String procesarAgregarPersona(@ModelAttribute Persona persona, RedirectAttributes redirectAttributes) {
        ArrayList<String> errores = PersonaValidator.validarPersona(persona, true);
        // Hay errores
        if (errores.size() != 0) {
            redirectAttributes.addFlashAttribute("errors", errores);
            redirectAttributes.addFlashAttribute("persona", persona);
            return "redirect:/clientes/personas/add";
        }
        // Insertar en la tabla de clientes
        PersonaJDBC personaJDBC = new PersonaJDBC();
        personaJDBC.setDataSource(DatabaseSource.getDataSource());
        personaJDBC.agregarPersona(persona);
        redirectAttributes.addFlashAttribute("success_msg", "Persona agregada");
        return "redirect:/clientes";
    }

    /**
     * Cargar los detalles de una sola persona
     * @param cedula
     * @param model
     * @return
     */
    @RequestMapping(value = "/clientes/personas/{cedula}", method = RequestMethod.GET)
    public String getPersona(@PathVariable(value = "cedula") int cedula, Model model) {
        PersonaJDBC personaJDBC = new PersonaJDBC();
        personaJDBC.setDataSource(DatabaseSource.getDataSource());
        Persona persona = personaJDBC.getPersona(cedula);
        model.addAttribute("persona", persona);
        return "detallespersona";
    }

    /**
     * Cargar formulario para modificar una persona
     * @param cedula
     * @param model
     * @return
     */
    @RequestMapping(value="/clientes/personas/{cedula}/modificar", method = RequestMethod.GET)
    public String modificarPersona(@PathVariable(value = "cedula") int cedula, Model model) {
        // Obtener persona
        if (!model.containsAttribute("persona")) {
            // Obtener la persona
            PersonaJDBC personaJDBC = new PersonaJDBC();
            personaJDBC.setDataSource(DatabaseSource.getDataSource());
            Persona persona = personaJDBC.getPersona(cedula);
            model.addAttribute("persona", persona);
        }
        // Obtener estados de los clientes
        ClientJDBC clientJDBC = new ClientJDBC();
        clientJDBC.setDataSource(DatabaseSource.getDataSource());
        List<String> estados =  clientJDBC.getEstados();
        model.addAttribute("estados", estados);
        return "modificarpersona";
    }

    /**
     * Procesar la modificacion de una persona
     * @param persona
     * @param cedula
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="/clientes/personas/{cedula}/modificar", method = RequestMethod.POST)
    public String procesarModificarPersona(@ModelAttribute Persona persona, @PathVariable(value = "cedula") int cedula, RedirectAttributes redirectAttributes) {
        ArrayList<String> errores = PersonaValidator.validarPersona(persona, false);
        // Hay errores
        if (errores.size() != 0) {
            redirectAttributes.addFlashAttribute("errors", errores);
            redirectAttributes.addFlashAttribute("persona", persona);
            System.out.println(errores);
            return "redirect:/clientes/personas/" + persona.getCedula() + "/modificar";
        }
        // Obtener id
        PersonaJDBC personaJDBC = new PersonaJDBC();
        personaJDBC.setDataSource(DatabaseSource.getDataSource());
        int idPersona = personaJDBC.getPersona(persona.getCedula()).getId();
        // Modificar persona
        ClientJDBC clientJDBC = new ClientJDBC();
        clientJDBC.setDataSource(DatabaseSource.getDataSource());
        clientJDBC.modificarCliente(idPersona, persona.getNombre(), persona.getDireccion(), persona.getCiudad(), persona.getEstado());
        redirectAttributes.addFlashAttribute("success_msg", "Persona modificada");
        return "redirect:/clientes/personas/" + persona.getCedula();
    }

    /**
     * Asigna el estado de un cliente como suspendido
     * @param persona
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/clientes/suspender", method = RequestMethod.POST)
    public String suspenderCliente(@ModelAttribute Persona persona, RedirectAttributes redirectAttributes) {
        ClientJDBC clientJDBC = new ClientJDBC();
        clientJDBC.setDataSource(DatabaseSource.getDataSource());
        clientJDBC.suspenderCliente(persona.getId());
        return "redirect:/clientes";
    }

    /**
     * Carga formulario para agregar nuevas organizaciones
     * @param model
     * @return
     */
    @RequestMapping(value = "clientes/organizaciones/add", method = RequestMethod.GET)
    public String addOrganizacion(Model model)
    {
        if (!model.containsAttribute("organizacion")) {
            model.addAttribute("organizacion", new Organizacion());
        }
        return "addorganizacion";
    }
    @RequestMapping(value = "clientes/organizaciones/add", method = RequestMethod.POST)
    public String procesarAddOrganizacion(@ModelAttribute Organizacion organizacion,RedirectAttributes redirectAttributes)
    {
        ArrayList<String> errores = OrganizacionValidator.validarOrganizacion(organizacion, true);
        // Hay errores
        if (errores.size() != 0) {
            redirectAttributes.addFlashAttribute("errors", errores);
            redirectAttributes.addFlashAttribute("organizacion", organizacion);
            return "redirect:/clientes/organizaciones/add";
        }
        // Insertar en la tabla de clientes
        OrganizacionJDBC organizacionJDBC = new OrganizacionJDBC();
        organizacionJDBC.setDataSource(DatabaseSource.getDataSource());
        organizacionJDBC.agregarOrganizacion(organizacion);
        redirectAttributes.addFlashAttribute("success_msg", "Organizacion agregada");
        return "redirect:/clientes";
    }
    /**
     * Cargar los detalles de una sola organizacion
     * @param cedula
     * @param model
     * @return
     */
    @RequestMapping(value = "/clientes/organizaciones/{cedula}", method = RequestMethod.GET)
    public String getOrganizacion(@PathVariable(value = "cedula") long cedula, Model model) {
        OrganizacionJDBC organizacionJDBC = new OrganizacionJDBC();
        organizacionJDBC.setDataSource(DatabaseSource.getDataSource());
        Organizacion organizacion = organizacionJDBC.getOrganizacion(cedula);
        model.addAttribute("organizacion", organizacion);
        return "detallesOrg";
    }

    /**
     * Redirige a la página de modificacion de organizacion
     * @param cedula
     * @param model
     * @return
     */
    @RequestMapping(value= "/clientes/organizaciones/{cedula}/modificar",method= RequestMethod.GET)
    public String modificarOrganizacion(@PathVariable(value = "cedula") Long cedula, Model model)
    {
        if (!model.containsAttribute("organizacion")) {
            OrganizacionJDBC organizacionJDBC = new OrganizacionJDBC();
            organizacionJDBC.setDataSource(DatabaseSource.getDataSource());
            Organizacion organizacion = organizacionJDBC.getOrganizacion(cedula);
            model.addAttribute("organizacion", organizacion);
        }
        //Obtener estados del cliente
        ClientJDBC clientJDBC = new ClientJDBC();
        clientJDBC.setDataSource(DatabaseSource.getDataSource());
        List<String> estados =  clientJDBC.getEstados();
        model.addAttribute("estados", estados);
        return "modificarOrg";
    }


    /**
     * Procesar la modificacion de una organizacion
     * @param organizacion
     * @param cedula
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="/clientes/organizaciones/{cedula}/modificar", method = RequestMethod.POST)
    public String procesarModificarOrganizacion(@ModelAttribute Organizacion organizacion, @PathVariable(value = "cedula") long cedula, RedirectAttributes redirectAttributes) {
        ArrayList<String> errores = OrganizacionValidator.validarOrganizacion(organizacion, false);
        // Hay errores
        if (errores.size() != 0) {
            redirectAttributes.addFlashAttribute("errors", errores);
            redirectAttributes.addFlashAttribute("organizacion", organizacion);
            return "redirect:/clientes/organizaciones/" + organizacion.getCedula() + "/modificar";
        }
        // Obtener id
        OrganizacionJDBC organizacionJDBC = new OrganizacionJDBC();
        organizacionJDBC.setDataSource(DatabaseSource.getDataSource());
        int idOrg = organizacionJDBC.getOrganizacion(organizacion.getCedula()).getId();

        // Modificar persona
        ClientJDBC clientJDBC = new ClientJDBC();
        clientJDBC.setDataSource(DatabaseSource.getDataSource());
        clientJDBC.modificarCliente(idOrg, organizacion.getNombre(), organizacion.getDireccion(), organizacion.getCiudad(), organizacion.getEstado());
        organizacionJDBC.modificarOrganizacion(idOrg,organizacion.getE_nombre(),organizacion.getE_cargo(),organizacion.getE_telefono());
        redirectAttributes.addFlashAttribute("success_msg", "Persona modificada");
        return "redirect:/clientes/organizaciones/" + organizacion.getCedula() + "?id=" + organizacion.getId();
    }

    @RequestMapping(value = "/organizaciones/suspender", method = RequestMethod.POST)
    public String suspenderCliente(@ModelAttribute Organizacion organizacion, RedirectAttributes redirectAttributes) {
        ClientJDBC clientJDBC = new ClientJDBC();
        clientJDBC.setDataSource(DatabaseSource.getDataSource());
        clientJDBC.suspenderCliente(organizacion.getId());
        return "redirect:/clientes";
    }

    @RequestMapping(value = "/clientes/personas/{cedula}/telefonos/add", method = RequestMethod.POST)
    public String agregarTelefono(Model model, @PathVariable(value = "cedula") int cedula, @RequestParam String telefonoNuevo,
                                  RedirectAttributes redirectAttributes) {
        // Validar el telefono
        if (!PersonaValidator.validarTelefono(telefonoNuevo)) {
            redirectAttributes.addFlashAttribute("errors", new String[] {"Telefono invalido"});
            return "redirect:/clientes/personas/" + cedula;
        }
        // Verificar si ya existe el telefono
        PersonaJDBC personaJDBC = new PersonaJDBC();
        personaJDBC.setDataSource(DatabaseSource.getDataSource());
        if (personaJDBC.existeTelefono(telefonoNuevo, cedula)) {
            redirectAttributes.addFlashAttribute("errors", new String[] {"Este telefono ya existe"});
            return "redirect:/clientes/personas/" + cedula;
        }
        // Agregar el telefono
        personaJDBC.agregarTelefono(cedula, telefonoNuevo);
        redirectAttributes.addFlashAttribute("success_msg", "Telefono agregado");
        return "redirect:/clientes/personas/" + cedula;
    }

    @RequestMapping(value = "/clientes/personas/{cedula}/telefonos/borrar", method = RequestMethod.POST)
    public String borrarTelefono(@PathVariable(value = "cedula") int cedula, @RequestParam String telefono,
                                 RedirectAttributes redirectAttributes) {
        // Borrar telefono
        PersonaJDBC personaJDBC = new PersonaJDBC();
        personaJDBC.setDataSource(DatabaseSource.getDataSource());
        personaJDBC.borrarTelefono(cedula, telefono);
        redirectAttributes.addFlashAttribute("success_msg", "Telefono eliminado");
        return "redirect:/clientes/personas/" + cedula;
    }
}

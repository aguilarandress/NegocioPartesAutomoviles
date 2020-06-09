package com.negocio.automoviles.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controlador para paginas principales
 */
@Controller
public class PagesController {
    /**
     * Carga la pagina de inicio
     * @return El template que se desea cargar
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}

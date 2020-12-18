package com.unisa.cinehub.control;

import org.atmosphere.config.service.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtenteControl {

    @GetMapping("/ciao")
    public String ciao() {
        return "ciao";
    }
}

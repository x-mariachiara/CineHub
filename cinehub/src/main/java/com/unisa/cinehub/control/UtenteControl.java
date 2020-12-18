package com.unisa.cinehub.control;

import com.unisa.cinehub.model.RecensoreService;
import org.atmosphere.config.service.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtenteControl {

    @Autowired
    private RecensoreService recensoreService;

    public UtenteControl(RecensoreService recensoreService) {
        this.recensoreService = recensoreService;
    }

    @GetMapping("/ciao")
    public String ciao() {
        recensoreService.save();

        return "ciao " + recensoreService.findAll();
    }
}

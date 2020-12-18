package com.unisa.cinehub.control;

import java.util.*;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.model.service.RecensoreService;
import com.unisa.cinehub.model.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("utentecontrol")
public class UtenteControl {

    /* Altri Service */
    @Autowired
    private RecensoreService recensoreService;
    @Autowired
    private UtenteService utenteService;

    public UtenteControl(RecensoreService recensoreService) {
        this.recensoreService = recensoreService;
    }

    @GetMapping("/getAllRecensori")
    public List<Recensore> getAllRecensori() {
        return recensoreService.findAll();
    }

    @PostMapping("/signup")
    public void registrazioneUtente(@RequestBody Utente utente) {
        utenteService.signup(utente);
    }

    /* altra logica di business*/
}

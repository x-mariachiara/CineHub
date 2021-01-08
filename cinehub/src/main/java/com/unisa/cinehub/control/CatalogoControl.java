package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.model.service.RecensioneService;
import com.unisa.cinehub.model.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/catalogo")
public class CatalogoControl {

    @Autowired
    RecensioneService recensioneService;

    @Autowired
    UtenteService utenteService;

    public CatalogoControl(RecensioneService recensioneService, UtenteService utenteService) {
        this.recensioneService = recensioneService;
        this.utenteService = utenteService;
    }

    @PostMapping("add/recensione")
    public void addRecensione(@RequestBody Recensione recensione){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try {
                Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                Recensore recensore = extractedRecensore(p);
                recensioneService.addRecensione(recensione, recensore);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    private Recensore extractedRecensore(Object p) {
        if(p instanceof UserDetails) {
            Recensore recensore = (Recensore) utenteService.findByEmail(((UserDetails) p).getUsername());
            return recensore;
        } else {
            Recensore recensore = (Recensore) utenteService.findByEmail(p.toString());
            return recensore;
        }
    }
}

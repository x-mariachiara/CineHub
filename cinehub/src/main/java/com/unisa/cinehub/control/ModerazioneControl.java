package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Segnalazione;
import com.unisa.cinehub.model.service.SegnalazioneService;
import com.unisa.cinehub.model.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/moderazione")
public class ModerazioneControl {

    @Autowired
    SegnalazioneService segnalazioneService;
    @Autowired
    UtenteService utenteService;

    public ModerazioneControl(SegnalazioneService segnalazioneService, UtenteService utenteService) {
        this.segnalazioneService = segnalazioneService;
        this.utenteService = utenteService;
    }

    @PostMapping("add/segnalazione")
    public void addSegnalazione(@RequestBody Recensione recensione) {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try {
                Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                if(p instanceof UserDetails) {
                    Recensore segnalatore = (Recensore) utenteService.findByEmail(((UserDetails) p).getUsername());
                    segnalazioneService.addSegnalazione(recensione, segnalatore);
                } else {
                    Recensore segnalatore = (Recensore) utenteService.findByEmail(p.toString());
                    segnalazioneService.addSegnalazione(recensione, segnalatore);
                }

            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("bannaccount")
    public void bannaRecensore(@RequestParam String email) {
        utenteService.bannaRecensore(email);
    }

    @GetMapping("request/all/segnalazione")
    public List<Segnalazione> findAllSegnalazioni() { return segnalazioneService.retrieveAll(); }

    @PostMapping
    public boolean isSegnalated(@RequestBody Recensione recensione) {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try {
                Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                if(p instanceof UserDetails) {
                    Recensore segnalatore = (Recensore) utenteService.findByEmail(((UserDetails) p).getUsername());
                    return segnalazioneService.exist(recensione, segnalatore);
                } else {
                    Recensore segnalatore = (Recensore) utenteService.findByEmail(p.toString());
                    return segnalazioneService.exist(recensione, segnalatore);
                }

            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

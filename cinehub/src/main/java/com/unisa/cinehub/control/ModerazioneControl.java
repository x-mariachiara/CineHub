package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Segnalazione;
import com.unisa.cinehub.model.service.SegnalazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/moderazione")
public class ModerazioneControl {

    @Autowired
    SegnalazioneService segnalazioneService;

    public ModerazioneControl(SegnalazioneService segnalazioneService) {
        this.segnalazioneService = segnalazioneService;
    }

    @PostMapping("add/segnalazione")
    public void addSegnalazione(@RequestBody Recensione recensione) {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try {
                Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                segnalazioneService.addSegnalazione(recensione);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("request/all/segnalazione")
    public List<Segnalazione> findAllSegnalazioni() { return segnalazioneService.retrieveAll(); }
}

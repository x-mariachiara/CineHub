package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.service.RecensioneService;
import com.unisa.cinehub.model.service.SegnalazioneService;
import com.unisa.cinehub.model.service.UtenteService;
import com.unisa.cinehub.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/moderazione")
public class ModerazioneControl {

    @Autowired
    private SegnalazioneService segnalazioneService;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private RecensioneService recensioneService;

    public ModerazioneControl(SegnalazioneService segnalazioneService, UtenteService utenteService, RecensioneService recensioneService) {
        this.segnalazioneService = segnalazioneService;
        this.utenteService = utenteService;
        this.recensioneService = recensioneService;
    }

    @PostMapping("add/segnalazione")
    public void addSegnalazione(@RequestBody Recensione recensione) throws NotAuthorizedException, InvalidBeanException {
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

    @GetMapping("moderazione/bannaccount")
    public void bannaRecensore(@RequestParam String email) throws NotAuthorizedException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof Moderatore && ((Moderatore) utente).getTipo().equals(Moderatore.Tipo.MODACCOUNT)) {
            utenteService.bannaRecensore(email);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @GetMapping("request/all/segnalazione")
    public List<Segnalazione> findAllSegnalazioni() { return segnalazioneService.retrieveAll(); }

    @PostMapping("request/isSegnalated/recensione")
    public boolean isSegnalated(@RequestBody Recensione recensione) throws NotAuthorizedException {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try {
                Recensore segnalatore = (Recensore) SecurityUtils.getLoggedIn();
                return segnalazioneService.puoSegnalare(recensione, segnalatore);

            } catch (ClassCastException | InvalidBeanException e) {
               throw new NotAuthorizedException();
            }
        }
        return false;
    }

    @PostMapping("moderazione/remove/recensione")
    public void deleteRecensione (@RequestBody Recensione recensione) throws NotAuthorizedException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof Moderatore && ((Moderatore) utente).getTipo().equals(Moderatore.Tipo.MODCOMMENTI)) {
            recensioneService.removeRecensione(recensione);
        } else {
            throw new NotAuthorizedException();
        }
    }


}

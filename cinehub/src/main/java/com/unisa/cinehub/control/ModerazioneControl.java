package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.recensione.RecensioneService;
import com.unisa.cinehub.model.utente.SegnalazioneService;
import com.unisa.cinehub.model.utente.UtenteService;
import com.unisa.cinehub.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Recensore segnalatore = (Recensore) SecurityUtils.getLoggedIn();
                segnalazioneService.addSegnalazione(recensione, segnalatore);
            } catch (ClassCastException | BeanNotExsistException e) {
                throw new NotAuthorizedException();
            }
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("moderazione/bannaccount")
    public void bannaRecensore(@RequestParam String email) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
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

            } catch (ClassCastException | InvalidBeanException | BeanNotExsistException e) {
               throw new NotAuthorizedException();
            }
        }
        return false;
    }

    @PostMapping("moderazione/remove/recensione")
    public void deleteRecensione (@RequestBody Recensione recensione) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof Moderatore && ((Moderatore) utente).getTipo().equals(Moderatore.Tipo.MODCOMMENTI)) {
            recensioneService.removeRecensione(recensione);
        } else {
            throw new NotAuthorizedException();
        }
    }


}

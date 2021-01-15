package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.exception.NotLoggedException;
import com.unisa.cinehub.model.service.MiPiaceService;
import com.unisa.cinehub.model.service.RecensioneService;
import com.unisa.cinehub.model.service.UtenteService;
import com.unisa.cinehub.security.SecurityUtils;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/catalogo")
public class CatalogoControl {

    @Autowired
    RecensioneService recensioneService;

    @Autowired
    MiPiaceService miPiaceService;

    @Autowired
    UtenteService utenteService;

    public CatalogoControl(RecensioneService recensioneService, MiPiaceService miPiaceService, UtenteService utenteService) {
        this.recensioneService = recensioneService;
        this.miPiaceService = miPiaceService;
        this.utenteService = utenteService;
    }

    @PostMapping("add/recensione")
    public void addRecensione(@RequestBody Recensione recensione) throws NotLoggedException, NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {

                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                recensioneService.addRecensione(recensione, recensore);
            } catch (ClassCastException e) {
                throw new NotAuthorizedException();
            }
        } else {
            throw new NotLoggedException();
        }
    }
    @PostMapping("request/key/recensione")
    public Recensione requestRecensioneById(@RequestBody Long id){
        return recensioneService.retrieveById(id);
    }



    @GetMapping("add/mipiace")
    public void addMiPiace(@RequestParam("tipo") boolean b, @RequestBody Recensione recensione) throws NotLoggedException, NotAuthorizedException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                miPiaceService.handleMiPiace(b, recensione, recensore);
            } catch (ClassCastException | InvalidBeanException e) {
                throw new NotAuthorizedException();
            }
        } else {
            throw new NotLoggedException();
        }
    }

    @PostMapping("request/key/mipiace")
    public MiPiace findMyPiaceById(@RequestBody Recensione recensione) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                return miPiaceService.findMiPiaceById(recensore, recensione);
            } catch (ClassCastException e) {
                throw new NotAuthorizedException();
            }
        }
        return null;
    }

    @PostMapping("request/num/mipiace")
    public Integer getNumeroMiPiaceOfRecensione(@RequestBody Recensione recensione){
        return miPiaceService.getNumeroMiPiaceOfRecensione(recensione);
    }

    @PostMapping("request/num/nonmipiace")
    public Integer getNumeroNonMiPiaceOfRecensione(@RequestBody Recensione recensione){
        return miPiaceService.getNumeroNonMiPiaceOfRecensione(recensione);
    }

    @PostMapping("add/risposta")
    public void rispondiARecensione(@RequestBody Recensione risposta, @Param("id") Long idPadre) throws NotLoggedException, NotAuthorizedException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                recensioneService.addRisposta(recensore, risposta, idPadre);
            } catch (ClassCastException e) {
                throw new NotAuthorizedException();
            } catch (BeanNotExsistException e) {
                Notification.show("Si è verificato un erorre");
            }
        } else {
            throw new NotLoggedException();
        }

    }

}

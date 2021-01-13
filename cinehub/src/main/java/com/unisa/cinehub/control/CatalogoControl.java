package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.model.exception.NotLoggedException;
import com.unisa.cinehub.model.service.MiPiaceService;
import com.unisa.cinehub.model.service.RecensioneService;
import com.unisa.cinehub.model.service.UtenteService;
import com.unisa.cinehub.security.SecurityUtils;
import org.atmosphere.config.service.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
    public void addRecensione(@RequestBody Recensione recensione) throws NotLoggedException{
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                recensioneService.addRecensione(recensione, recensore);
            } catch (ClassCastException e) {
                e.printStackTrace();
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
    public void addMiPiace(@RequestParam("tipo") boolean b, @RequestBody Recensione recensione) throws NotLoggedException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                miPiaceService.addMiPiace(b, recensione, recensore);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        } else {
            throw new NotLoggedException();
        }
    }

    @PostMapping("request/key/mipiace")
    public MiPiace findMyPiaceById(@RequestBody Recensione recensione) {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                return miPiaceService.findMiPiaceById(recensore, recensione);
            } catch (ClassCastException e) {
                e.printStackTrace();
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
    public void rispondiARecensione(@RequestBody Recensione risposta, @Param("id") Long idPadre) throws NotLoggedException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                recensioneService.addRisposta(recensore, risposta, idPadre);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        } else {
            throw new NotLoggedException();
        }

    }

}

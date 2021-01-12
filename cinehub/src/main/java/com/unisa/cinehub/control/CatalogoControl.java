package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.model.service.MiPiaceService;
import com.unisa.cinehub.model.service.RecensioneService;
import com.unisa.cinehub.model.service.UtenteService;
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
    @PostMapping("request/key/recensione")
    public Recensione requestRecensioneById(@RequestBody Long id){
        return recensioneService.retrieveById(id);
    }



    @GetMapping("add/mipiace")
    public void addMiPiace(@RequestParam("tipo") boolean b, @RequestBody Recensione recensione) {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try {
                Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                Recensore recensore = extractedRecensore(p);
                miPiaceService.addMiPiace(b, recensione, recensore);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("request/key/mipiace")
    public MiPiace findMyPiaceById(@RequestBody Recensione recensione) {
        System.out.println("sto nel metodo findmypiacebyid");
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try {
                Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                Recensore recensore = extractedRecensore(p);
                System.out.println("recenosre della sessione:" + recensore);
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
    public void rispondiARecensione(@RequestBody Recensione risposta, @Param("id") Long idPadre) {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try {
                Recensore recensore = extractedRecensore(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                recensioneService.addRisposta(recensore, risposta, idPadre);
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

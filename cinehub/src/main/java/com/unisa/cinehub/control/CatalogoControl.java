package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.model.service.RecensioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/catalogo")
public class CatalogoControl {

    @Autowired
    RecensioneService recensioneService;

    public CatalogoControl(RecensioneService recensioneService) {
        this.recensioneService = recensioneService;
    }

    @PostMapping("add/recensione")
    public void addRecensione(@RequestBody Recensione recensione){
        recensioneService.addRecensione(recensione);
    }

}

package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Segnalazione;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.data.repository.SegnalazioneRepository;
import com.vaadin.flow.component.grid.editor.Editor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class SegnalazioneService {

    private static Logger logger = Logger.getLogger("SegnalazioneService");

    @Autowired
    SegnalazioneRepository segnalazioneRepository;

    @Autowired
    RecensioneRepository recensioneRepository;

    @Autowired
    RecensoreRepository recensoreRepository;

    public SegnalazioneService(SegnalazioneRepository segnalazioneRepository, RecensioneRepository recensioneRepository, RecensoreRepository recensoreRepository) {
        this.segnalazioneRepository = segnalazioneRepository;
        this.recensioneRepository = recensioneRepository;
        this.recensoreRepository = recensoreRepository;
    }

    public void addSegnalazione(Recensione recensione) {
        if(recensione != null) {
            Recensore recensore = recensione.getRecensore();
            Segnalazione segnalazione = new Segnalazione();
            segnalazione.setRecensione(recensione);
            segnalazione.setRecensore(recensore);
            segnalazioneRepository.save(segnalazione);
            recensore.getListaSegnalazioni().add(segnalazione);
            recensoreRepository.save(recensore);
            recensione.getListaSegnalazioni().add(segnalazione);
            recensioneRepository.save(recensione);
        }
    }

    public List<Segnalazione> retrieveAll() { return segnalazioneRepository.findAll(); }
}

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

    public void addSegnalazione(Long idRecensione) {
        if(idRecensione != null) {
            //Ottengo la recensione dato il suo id
            Recensione recensione = recensioneRepository.findById(idRecensione).orElse(null);
            if(recensione != null) {
                //Dalla recensione ottengo il recensore
                Recensore recensore = recensione.getRecensore();
                //Creo la segnalazione, setto recensore e recensione e la rendo persistente
                Segnalazione segnalazione = new Segnalazione();
                segnalazione.setRecensione(recensione);
                segnalazione.setRecensore(recensore);
                segnalazioneRepository.save(segnalazione);
                //Aggiungo la segnalazione alla lista delle segnalazioni del recensore e della recenzione ed aggiorno entambi
                recensore.getListaSegnalazioni().add(segnalazione);
                recensoreRepository.save(recensore);
                recensione.getListaSegnalazioni().add(segnalazione);
                recensioneRepository.save(recensione);

            }
        }
    }
}

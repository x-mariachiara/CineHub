package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Segnalazione;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.data.repository.SegnalazioneRepository;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
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

    public Segnalazione addSegnalazione(Recensione recensione, Recensore segnalatore) throws InvalidBeanException, NotAuthorizedException {
        if(recensione != null && segnalatore != null) {
            Recensore recensore = recensione.getRecensore();
            if(!recensore.equals(segnalatore)) {
                Segnalazione segnalazione = new Segnalazione();
                segnalazione.setRecensione(recensione);
                segnalazione.setRecensore(recensore);
                segnalazione.setSegnalatoreId(segnalatore.getEmail());
                Segnalazione salvata = segnalazioneRepository.save(segnalazione);
                recensore.getListaSegnalazioni().add(segnalazione);
                recensoreRepository.save(recensore);
                recensione.getListaSegnalazioni().add(segnalazione);
                recensioneRepository.save(recensione);
                return salvata;
            }
            throw new NotAuthorizedException();
        }
        throw new InvalidBeanException();
    }

    public List<Segnalazione> retrieveAll() { return segnalazioneRepository.findAll(); }

    public boolean puoSegnalare(Recensione recensione, Recensore segnalatore) {
        if(recensione != null && segnalatore != null) {
            Recensore recensore = recensione.getRecensore();
            if (!recensore.equals(segnalatore)) {
                Segnalazione.SegnalazioneID id = new Segnalazione.SegnalazioneID(segnalatore.getEmail(), recensore.getEmail(), recensione.getId());
                return !segnalazioneRepository.existsById(id);
            }
            return false;
        }
        return false;
    }
}

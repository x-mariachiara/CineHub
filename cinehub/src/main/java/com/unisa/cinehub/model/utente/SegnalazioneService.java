package com.unisa.cinehub.model.utente;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Segnalazione;
import com.unisa.cinehub.model.recensione.RecensioneRepository;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
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
                segnalazione.setRecensioneId(recensione.getId());
                segnalazione.setRecensoreId(recensore.getEmail());
                Segnalazione salvata = segnalazioneRepository.saveAndFlush(segnalazione);
                recensore.getListaSegnalazioni().add(salvata);
                recensoreRepository.saveAndFlush(recensore);
                recensione.getListaSegnalazioni().add(salvata);
                recensioneRepository.saveAndFlush(recensione);
                return salvata;
            }
            throw new NotAuthorizedException("Un recensore non può segnalare se stesso");
        }
        throw new InvalidBeanException("recensione: " + recensione + "e/o sengalatore: " + segnalatore + "non validi");
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

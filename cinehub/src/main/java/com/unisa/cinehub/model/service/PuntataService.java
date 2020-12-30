package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.unisa.cinehub.data.repository.PuntataRepository;
import com.unisa.cinehub.data.repository.SerieTVRepository;
import com.unisa.cinehub.data.repository.StagioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;


@Service
public class PuntataService {

    private static Logger logger = Logger.getLogger("PuntataService");

    @Autowired
    private StagioneRepository stagioneRepository;
    @Autowired
    private PuntataRepository puntataRepository;
    @Autowired
    private SerieTVService serieTVService;

    public PuntataService(SerieTVService serieTVService, StagioneRepository stagioneRepository, PuntataRepository puntataRepository) {
        this.serieTVService = serieTVService;
        this.stagioneRepository = stagioneRepository;
        this.puntataRepository = puntataRepository;
    }

    /**
     * Questo metodo consente di aggiungere una nuova puntata.
     * Controlla che la serie tv a cui deve aggiungere la puntata sia valida, controlla se esiste una stagione con quel
     * numero all'interno della serie tv: se non esiste la crea, altrimenti procede a creare la puntata, ad associargli
     * una stagione e la rende persistente
     * @param puntata puntata da inserire
     * @param numeroStagione numero della stagione di appartenenza della puntata
     * @param idSerieTv id della serie tv di appartenenza della puntata
     */
    public void addPuntata(Puntata puntata, Integer numeroStagione, Long idSerieTv) {
        SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
        if(serieTv != null) {
            Stagione stagione = serieTVService.getStagione(serieTv, numeroStagione).orElse(null);
            if(stagione == null) {
                logger.info("La stagione " + numeroStagione + " non esiste, aggiungo");
                stagione = new Stagione(numeroStagione);
                stagione.setSerieTv(serieTv);
                serieTVService.addStagione(serieTv,stagione);
            }
            puntata.setStagione(stagione);
            puntataRepository.save(puntata);
            logger.info("Aggiunta stagione " + stagione + " alla puntata: " + puntata);
            stagione.getPuntate().add(puntata);
            serieTVService.aggiornaStagione(stagione);

        }
    }

    public Puntata retrievePuntataByKey(Puntata.PuntataID puntataID) {
        return puntataRepository.findById(puntataID).orElse(null);
    }

    public void removePuntata(Puntata daEliminare) {
        if(daEliminare != null) {
            puntataRepository.delete(daEliminare);
        }
    }

    /**
     * Dato l'id di una serie tv restituisce tutte le puntate di quella serie
     * @param idSerieTv id della serie di cui si vogliono recuperare le puntate
     * @return lista di puntate appartenenti a quella serie
     */
    public List<Puntata> retrieveBySerieTV(Long idSerieTv) {
        List<Puntata> puntate = new ArrayList<>();
        if(idSerieTv != null) {
            SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
            for(Stagione s : serieTv.getStagioni()) {
                puntate.addAll(s.getPuntate());
            }
            return puntate;
        }

        return null;
    }

    /**
     * Dato l'id di una serie tv e il numero di una stagione permette di recuperare tutte le puntate relative a
     * quella stagione
     * @param idSerieTv id della serie tv di appartenenza
     * @param numeroStagione numero nella stagione
     * @return lista di puntate appartenenti a quella serie - stagione
     */
    public List<Puntata> retrieveByStagione(Long idSerieTv, Integer numeroStagione) {
        if(idSerieTv != null && numeroStagione != null) {
            SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
            Stagione stagione = serieTVService.getStagione(serieTv, numeroStagione).orElse(null);
            if (stagione != null) {
                return new ArrayList<>(stagione.getPuntate());
            }
        }
        return null;
    }

    public void mergePuntata(Puntata puntata) {
        puntataRepository.save(puntata);
    }
}

package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.unisa.cinehub.data.repository.PuntataRepository;
import com.unisa.cinehub.data.repository.StagioneRepository;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Puntata addPuntata(Puntata puntata, Integer numeroStagione, Long idSerieTv) throws InvalidBeanException, AlreadyExsistsException {
        SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
        if(serieTv != null && numeroStagione != null && numeroStagione > 0) {
            Puntata.PuntataID id = new Puntata.PuntataID(numeroStagione, puntata.getStagioneId());
            if(!puntataRepository.existsById(id)) {
                Stagione stagione = serieTVService.getStagione(serieTv, numeroStagione).orElse(null);
                if (stagione == null) {
                    logger.info("La stagione " + numeroStagione + " non esiste, aggiungo");
                    stagione = new Stagione(numeroStagione);
                    stagione.setSerieTv(serieTv);
                    serieTVService.addStagione(serieTv, stagione);
                }
                puntata.setStagione(stagione);
                Puntata salvata = puntataRepository.save(puntata);
                logger.info("Aggiunta stagione " + stagione + " alla puntata: " + puntata);
                stagione.getPuntate().add(puntata);
                serieTVService.aggiornaStagione(stagione);
                return salvata;
            }
            throw new AlreadyExsistsException();
        }
        throw new InvalidBeanException();
    }

    public void removePuntata(Puntata.PuntataID id) throws BeanNotExsistException, InvalidBeanException {
        if(id != null) {
            if(puntataRepository.existsById(id)) {
                Stagione stagione = serieTVService.getStagione(id.getStagioneId().getSerieTvId(), id.getStagioneId().getNumeroStagione()).orElse(null);
                Puntata daRimuovere = puntataRepository.findById(id).get();
                stagione.getPuntate().remove(daRimuovere);
                serieTVService.aggiornaStagione(stagione);
                daRimuovere.setStagione(null);
                mergePuntata(daRimuovere);
                puntataRepository.flush();
                puntataRepository.delete(daRimuovere);
            }
            throw new BeanNotExsistException();
        }
        throw new InvalidBeanException();
    }

    public List<Puntata> retrieveAll() { return puntataRepository.findAll(); }

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

    public Puntata retrievePuntataByKey(Puntata.PuntataID puntataID) {
        return puntataRepository.findById(puntataID).orElse(null);
    }

    public void mergePuntata(Puntata puntata) {
        puntataRepository.save(puntata);
    }

    public List<Puntata> searchByTitle(String titolo) {
        List<Puntata> risultati = new ArrayList<>();
        if(!titolo.isBlank()) {
            titolo = titolo.trim();
            logger.info("Ricerca per titolo avviata con titolo: " + titolo);
            risultati.addAll(puntataRepository.findPuntataByTitle(titolo));
            return risultati;
        }
        return null;
    }
}

package com.unisa.cinehub.model.media.serietv;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.utente.UtenteRepository;
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
    @Autowired
    private UtenteRepository utenteRepository;

    public PuntataService(StagioneRepository stagioneRepository, PuntataRepository puntataRepository, SerieTVService serieTVService, UtenteRepository utenteRepository) {
        this.stagioneRepository = stagioneRepository;
        this.puntataRepository = puntataRepository;
        this.serieTVService = serieTVService;
        this.utenteRepository = utenteRepository;
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
    public Puntata addPuntata(Puntata puntata, Integer numeroStagione, Long idSerieTv) throws InvalidBeanException, AlreadyExsistsException, BeanNotExsistException {
        SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
        if(numeroStagione != null && numeroStagione > 0 && Puntata.checkPuntata(puntata)) {
            Puntata.PuntataID id = new Puntata.PuntataID(puntata.getNumeroPuntata(), new Stagione.StagioneID(numeroStagione, idSerieTv));
            if(!puntataRepository.existsById(id)) {
                logger.info("La puntata non esiste");
                Stagione stagione;
                try {
                    stagione = serieTVService.getStagione(serieTv, numeroStagione);
                } catch (BeanNotExsistException e) {
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
            throw new AlreadyExsistsException("La puntata " + puntata + " esiste già");
        }
        throw new InvalidBeanException("La puntata " + puntata + "non è valida");
    }

    public void removePuntata(Puntata.PuntataID id) throws BeanNotExsistException, InvalidBeanException {
        if(id != null) {
            if(puntataRepository.existsById(id)) {
                Stagione stagione = serieTVService.getStagione(id.getStagioneId().getSerieTvId(), id.getStagioneId().getNumeroStagione());
                Puntata daRimuovere = puntataRepository.findById(id).get();
                stagione.getPuntate().remove(daRimuovere);
                stagioneRepository.save(stagione);
                for(Recensione r : daRimuovere.getListaRecensioni()) {
                    Recensore rec = r.getRecensore();
                    rec.getListaRecensioni().remove(r);
                    utenteRepository.save(rec);
                }
                daRimuovere.getListaRecensioni().clear();
                puntataRepository.save(daRimuovere);
                puntataRepository.delete(daRimuovere);
            }
            else throw new BeanNotExsistException("La puntata con PuntataID " + id + " non esiste");
        }
        else throw new InvalidBeanException("Il PuntataID " + id + " non è valido");
    }

    public List<Puntata> retrieveAll() { return puntataRepository.findAll(); }

    /**
     * Dato l'id di una serie tv restituisce tutte le puntate di quella serie
     * @param idSerieTv id della serie di cui si vogliono recuperare le puntate
     * @return lista di puntate appartenenti a quella serie
     */
    public List<Puntata> retrieveBySerieTV(Long idSerieTv) throws InvalidBeanException, BeanNotExsistException {
        List<Puntata> puntate = new ArrayList<>();
        if(idSerieTv != null) {
            SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
            for(Stagione s : serieTv.getStagioni()) {
                puntate.addAll(s.getPuntate());
            }
            return puntate;
        }
        throw new InvalidBeanException("idSerieTv non può essere null");
    }

    /**
     * Dato l'id di una serie tv e il numero di una stagione permette di recuperare tutte le puntate relative a
     * quella stagione
     * @param idSerieTv id della serie tv di appartenenza
     * @param numeroStagione numero nella stagione
     * @return lista di puntate appartenenti a quella serie - stagione
     */
    public List<Puntata> retrieveByStagione(Long idSerieTv, Integer numeroStagione) throws InvalidBeanException, BeanNotExsistException {
        if(idSerieTv != null && numeroStagione > 0) {
            SerieTv serieTv = serieTVService.retrieveByKey(idSerieTv);
            Stagione stagione = serieTVService.getStagione(serieTv, numeroStagione);
            if (stagione != null) {
                return new ArrayList<>(stagione.getPuntate());
            }
        }
        throw new InvalidBeanException("idSerieTv e/o numeroStagione non sono validi");
    }

    public Puntata retrievePuntataByKey(Puntata.PuntataID puntataID) throws BeanNotExsistException, InvalidBeanException {
        if(puntataID != null) {
            Puntata trovata = puntataRepository.findById(puntataID).orElse(null);
            if(trovata != null) {
                return trovata;
            }
            else throw new BeanNotExsistException("non esiste una puntata con puntataID: " + puntataID);
        }
        else throw new InvalidBeanException("puntataID non può essere null");
    }

    public Puntata mergePuntata(Puntata puntata) throws InvalidBeanException, BeanNotExsistException {
        if(Puntata.checkPuntata(puntata)) {
            if(puntataRepository.existsById(new Puntata.PuntataID(puntata.getNumeroPuntata(), puntata.getStagioneId()))) {
                return puntataRepository.save(puntata);
            }
            else throw new BeanNotExsistException("La puntata " + puntata + "non esiste");
        }
        else throw new InvalidBeanException("La puntata " + puntata + " non è valida");

    }

    public List<Puntata> searchByTitle(String titolo) {
        List<Puntata> risultati = new ArrayList<>();
        if(!titolo.isBlank()) {
            titolo = titolo.trim();
            logger.info("Ricerca per titolo avviata con titolo: " + titolo);
            risultati.addAll(puntataRepository.findPuntataByTitle(titolo));
            return risultati;
        }
        return risultati;
    }
}

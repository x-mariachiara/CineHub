package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RecensioneService {

    private static Logger logger = Logger.getLogger("RecenzioneService");

    @Autowired
    RecensioneRepository recensioneRepository;

    @Autowired
    FilmService filmService;

    @Autowired
    PuntataService puntataService;

    @Autowired
    UtenteService utenteService;

    @Autowired
    SerieTVService serieTVService;

    public RecensioneService(RecensioneRepository recensioneRepository, FilmService filmService, PuntataService puntataService, UtenteService utenteService, SerieTVService serieTVService) {
        this.recensioneRepository = recensioneRepository;
        this.filmService = filmService;
        this.puntataService = puntataService;
        this.utenteService = utenteService;
        this.serieTVService = serieTVService;
    }

    public void addRecensione(Recensione recensione, Recensore recensore) throws InvalidBeanException, BeanNotExsistException {
        if(recensione != null && !recensione.getContenuto().isBlank() && recensione.getPunteggio() >= 1 && recensione.getPunteggio() <= 5) {
            if(recensione.getFilm() != null) {
                Film film = filmService.retrieveByKey(recensione.getFilm().getId());
                Recensione daAggiungere = new Recensione(recensione.getContenuto(), recensione.getPunteggio(), film);
                daAggiungere.setRecensore(recensore);
                recensioneRepository.save(daAggiungere);
                recensore.getListaRecensioni().add(daAggiungere);
                utenteService.saveRegisteredUser(recensore);
                film.aggiungiRecensione(daAggiungere);
                filmService.mergeFilm(film);

            } else if (recensione.getPuntata() != null) {
                Puntata.PuntataID puntataID = new Puntata.PuntataID (recensione.getPuntata().getNumeroPuntata(), recensione.getPuntata().getStagioneId());
                Puntata puntata = puntataService.retrievePuntataByKey(puntataID);
                Recensione daAggiungere = new Recensione(recensione.getContenuto(), recensione.getPunteggio(), puntata);
                daAggiungere.setRecensore(recensore);
                recensioneRepository.save(daAggiungere);
                recensore.getListaRecensioni().add(daAggiungere);
                utenteService.saveRegisteredUser(recensore);
                puntata.aggiungiRecensione(daAggiungere);
                puntataService.mergePuntata(puntata);
                logger.info("Aggiungo recensione: " + daAggiungere + "alla puntata: " + puntata);
                logger.info(puntata.getListaRecensioni().size() + "");
                SerieTv serieTv = serieTVService.retrieveByKey(puntataID.getStagioneId().getSerieTvId());
                serieTv.calcolaMediaVoti();
                serieTVService.mergeSerieTV(serieTv);
            } else {
                throw new InvalidBeanException("recensione non riferita a nulla");
            }
        } else {
            throw new InvalidBeanException("recensione null o contenuto vuoto o punteggio vuoto");
        }
    }

    public List<Recensione> retrieveAll() { return recensioneRepository.findAll(); }

    public void removeRecensione(Recensione recensione) throws InvalidBeanException, BeanNotExsistException {
        if(recensione != null) {
            if(recensioneRepository.existsById(recensione.getId())) {
                Film recensito = recensione.getFilm();
                if(recensito == null) {
                    Puntata puntataRecensita = recensione.getPuntata();
                    puntataRecensita.getListaRecensioni().remove(recensione);
                    puntataService.mergePuntata(puntataRecensita);
                } else {
                    recensito.getListaRecensioni().remove(recensione);
                    filmService.mergeFilm(recensito);
                }
                Recensore utente = recensione.getRecensore();
                utente.getListaRecensioni().remove(recensione);
                utenteService.saveRegisteredUser(utente);
                recensioneRepository.delete(recensione);
            }
            else throw new BeanNotExsistException("La recensione non esiste");
        }
        else throw new InvalidBeanException("La Recensione non può essere null");
    }

    public void addRisposta(Recensore recensore, Recensione recensione, Long idPadre) throws BeanNotExsistException, InvalidBeanException {
        if(!recensione.getContenuto().isBlank()) {
            if (recensioneRepository.existsById(idPadre)) {
                Recensione risposta = new Recensione();
                Recensione padre = recensioneRepository.findById(idPadre).get();
                risposta.setRecensore(recensore);
                risposta.setContenuto(recensione.getContenuto());
                ;
                risposta.setPadre(padre);
                padre.getListaRisposte().add(risposta);
                recensioneRepository.save(risposta);
                recensioneRepository.save(padre);
            }
            else throw new BeanNotExsistException("Non esiste la recensione padre");
        }
        else throw new InvalidBeanException("La risposta non può essere vuota");
    }

    public Recensione retrieveById(Long id) throws BeanNotExsistException, InvalidBeanException {
        if(id != null) {
            if(recensioneRepository.existsById(id)) {
                return recensioneRepository.findById(id).get();
            }
            else throw new BeanNotExsistException("non esiste nessuna recensione con id: " + id);
        }
        else throw new InvalidBeanException("id non può essere null");
    }
}

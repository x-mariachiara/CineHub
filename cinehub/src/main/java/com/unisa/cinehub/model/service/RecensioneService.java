package com.unisa.cinehub.model.service;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.RecensioneRepository;
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

    public RecensioneService(RecensioneRepository recensioneRepository, FilmService filmService, PuntataService puntataService, UtenteService utenteService) {
        this.recensioneRepository = recensioneRepository;
        this.filmService = filmService;
        this.puntataService = puntataService;
        this.utenteService = utenteService;
    }

    public void addRecensione(Recensione recensione, Recensore recensore) {
        if(recensione != null) {
            if(recensione.getFilm() != null) {
                Film film = filmService.retrieveByKey(recensione.getFilm().getId());
                Recensione daAggiungere = new Recensione(recensione.getContenuto(), recensione.getPunteggio(), film);
                daAggiungere.setRecensore(recensore);
                recensioneRepository.save(daAggiungere);
                recensore.getListaRecensioni().add(daAggiungere);
                utenteService.saveRegisteredUser(recensore);
                film.aggiungiRecensione(daAggiungere);
                filmService.mergeFilm(film);
                logger.info("Aggiungo recensione: " + recensione + "al film: " + film);
                logger.info(film.getListaRecensioni().size() + "");

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
                puntata = puntataService.retrievePuntataByKey(puntataID);
                logger.info("Aggiungo recensione: " + daAggiungere + "alla puntata: " + puntata);
                logger.info(puntata.getListaRecensioni().size() + "");
            }
        }
    }

    public List<Recensione> retrieveAll() { return recensioneRepository.findAll(); }

    public void removeRecensione(Recensione recensione) {
        if(recensione != null) {
            recensioneRepository.delete(recensione);
        }
    }


}

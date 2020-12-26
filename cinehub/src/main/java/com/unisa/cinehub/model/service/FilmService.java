package com.unisa.cinehub.model.service;

import java.util.*;
import java.util.logging.Logger;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.repository.FilmRepository;
import com.unisa.cinehub.data.repository.GenereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FilmService {

    private static Logger logger = Logger.getLogger("FilmService");

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private GenereRepository genereRepository;

    public FilmService(FilmRepository filmRepository, GenereRepository genereRepository) {
        this.filmRepository = filmRepository;
        this.genereRepository = genereRepository;
    }

    /**
     * Rende persistente un film
     * @param film film da rendere persistente
     */
    public void addFilm(Film film){
        //TODO Aggiungere logica controlli
        filmRepository.save(film);
    }

    public void removeFilm(Long id) {
        if(id != null && filmRepository.existsById(id))
            filmRepository.delete(retrieveByKey(id));
    }

    public List<Film> retrieveAll() {
        return  filmRepository.findAll();
    }

    /**
     * Effettua una ricerca per chiave di un film
     * @param id id del film da cercare
     * @return un film se presente, altrimenti torna null
     */
    public Film retrieveByKey(Long id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        //TODO per ora ritorna null se il film non lo trova
        return filmOptional.orElse(null);
    }

    /**
     * Permette di aggiungere uno o più generi ad un film
     * @param generi collection di generi da aggiungere
     * @param id id del film a cui aggiungere i generi
     */
    public void addGeneri(Collection<Genere> generi, Long id) {

        Film film;
        Optional<Film>  fromDB = filmRepository.findById(id);
        if (!fromDB.isPresent()) {
            logger.severe("Film non trovato. Annulata operazione");
            return ;
        } else {
            film = fromDB.get();
        }

        //Controlla se i generi sono già presenti sul DB, in caso negativo li aggiunge
        for(Genere g : generi) {
            if (!genereRepository.existsById(g.getNomeGenere())) {
                logger.info("Genere " + g.getNomeGenere() + " mai inserito prima. Aggiungo.");
                genereRepository.save(g);
            }
        }


        film.getGeneri().addAll(generi);
        filmRepository.save(film);
    }

    /**
     * mergeFilm permette di modificare un film.
     * Effettua la modifica solamente se il film è effettivamente presente nel db e
     * se il film passato come parametro contiene effettivamente l'attributo id
     * @param film Film modificato
     */
    public void mergeFilm(Film film){
        if (film.getId() != null && filmRepository.existsById(film.getId())) {
            filmRepository.save(film);
            logger.info("film: " + film + " modificato correttamente");
        }

    }

    /**
     * Consente di cercare film tramite titolo o parte di esso.
     * Precondizione che il titolo sia diverso dalla stringa vuota o contenente solo spazi
     * @param titolo titolo da ricercare
     * @return zero o più film trovati
     */
    public List<Film> searchByTitle(String titolo) {
        List<Film> risultati = new ArrayList<>();
        if(!titolo.isBlank()) {
            titolo = titolo.trim();
            logger.info("Ricerca per titolo avviata con titolo: " + titolo);
            risultati.addAll(filmRepository.findFilmByTitle(titolo));
            return risultati;
        }
        return null;
    }

    /**
     * Consente di cercare film data una collection di generi
     * Precondizione
     * @param nomiGeneri generi dei film da cercare
     * @return
     */
    public Collection<Film> searchByGenere(Collection<Genere> nomiGeneri) {
        HashSet<Film> risultati = new HashSet<>();
        if(nomiGeneri != null && !nomiGeneri.isEmpty()) {

            List<Genere> generi = new ArrayList<>();
            for(Genere g : nomiGeneri) {
                generi.add(genereRepository.findById(g.getNomeGenere()).get());
            }

            for(Genere g : generi) {
                Set<Media> media = g.getMediaCollegati();
                for(Media m : media) {
                    if(m instanceof Film)
                        risultati.add((Film) m);
                }
            }
            return risultati;
        }
        return null;
    }

}

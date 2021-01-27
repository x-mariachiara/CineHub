package com.unisa.cinehub.model.media.film;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.media.GenereRepository;
import com.unisa.cinehub.model.utente.UtenteRepository;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class FilmService {

    private static Logger logger = Logger.getLogger("FilmService");

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private GenereRepository genereRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public FilmService(FilmRepository filmRepository, GenereRepository genereRepository, UtenteRepository utenteRepository) {
        this.filmRepository = filmRepository;
        this.genereRepository = genereRepository;
        this.utenteRepository = utenteRepository;
    }

    /**
     * Rende persistente un film
     *
     * @param film film da rendere persistente
     */
    public Film addFilm(Film film) throws AlreadyExsistsException, InvalidBeanException {
        if (Media.checkMedia(film)) {
            if (!filmRepository.existsByTitleAnnoUscita(film.getTitolo(), film.getAnnoUscita())) {
                film.getGeneri().clear();
                return filmRepository.save(film);
            } else {
                throw new AlreadyExsistsException("Il film: " + film + "esiste già");
            }
        } else {
            throw new InvalidBeanException("Il film " + film + "non è valido");
        }
    }

    @Transactional
    public void removeFilm(Long id) throws BeanNotExsistException, InvalidBeanException {
        if (id != null && filmRepository.existsById(id)) {
            Film toRemove = retrieveByKey(id);
            toRemove.setVisibile(false);
            filmRepository.save(toRemove);
        } else {
            throw new BeanNotExsistException("Il Film con id " + id + " non eiste");
        }
    }

    public List<Film> retrieveAll() {
        return filmRepository.findAll();
    }

    /**
     * Effettua una ricerca per chiave di un film
     *
     * @param id id del film da cercare
     * @return un film se presente, altrimenti torna null
     */
    public Film retrieveByKey(Long id) throws BeanNotExsistException, InvalidBeanException {
        if(id != null) {
            Optional<Film> filmOptional = filmRepository.findById(id);
            if(filmOptional.isPresent() && filmOptional.get().getVisibile()) {
                return filmOptional.get();
            }
            else throw new BeanNotExsistException("Il Film con id " + id + " non eiste");
        }
        else throw new InvalidBeanException("Hai Cercato di fare un retrive con id = null");
    }

    /**
     * Permette di aggiungere uno o più generi ad un film
     *
     * @param generi collection di generi da aggiungere
     * @param id     id del film a cui aggiungere i generi
     */
    public Film addGeneri(Collection<Genere> generi, Long id) throws BeanNotExsistException, InvalidBeanException {

        if(!generi.isEmpty() && id != null) {
            Film film;
            Optional<Film> fromDB = filmRepository.findById(id);
            if (!fromDB.isPresent()) {
                logger.severe("Film non trovato. Annulata operazione");
                throw new BeanNotExsistException("Film con id = " + id + "non esistente");
            } else {
                film = fromDB.get();
            }

            //Controlla se i generi sono già presenti sul DB, in caso negativo li aggiunge
            for (Genere g : generi) {
                if (!genereRepository.existsById(g.getNomeGenere())) {
                    logger.info("Genere " + g.getNomeGenere() + " mai inserito prima. Aggiungo.");
                    genereRepository.save(g);
                }
            }

            HashSet<Genere> daAggiungere = new HashSet<>(generi);
            film.setGeneri(daAggiungere);
            return filmRepository.save(film);
        }
        else throw new InvalidBeanException("Aggiunta generi non valida per generi: " + generi + "e id=" + id);
    }

    public Film addCast(Collection<Ruolo> ruoli, Long id) throws BeanNotExsistException, InvalidBeanException {
        if (!ruoli.isEmpty() && id != null) {
            Film film = filmRepository.findById(id).orElse(null);
            if (film != null) {
                film.setRuoli(ruoli);
                return filmRepository.save(film);
            } else {
                throw new BeanNotExsistException("Film con id = " + id + "non esistente");
            }
        } else {
            throw new InvalidBeanException("Aggiunta cast non valida per generi: " + ruoli + "e id=" + id);
        }
    }


    /**
     * mergeFilm permette di modificare un film.
     * Effettua la modifica solamente se il film è effettivamente presente nel db e
     * se il film passato come parametro contiene effettivamente l'attributo id
     * @param film Film modificato
     */
    public void mergeFilm(Film film) throws InvalidBeanException, BeanNotExsistException {
        if (film.getId() != null && filmRepository.existsById(film.getId())) {
            if (Media.checkMedia(film)) {
                filmRepository.save(film);
                logger.info("film: " + film + " modificato correttamente");
            }
            else throw new InvalidBeanException("Film :" + film + "non valido");
        }
        else throw new BeanNotExsistException("Film con id = " + film.getId() + "non esistente");
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
        return risultati;
    }

    /**
     * Consente di cercare film data una collection di generi
     * Precondizione
     * @param nomiGeneri generi dei film da cercare
     * @return
     */
    public Collection<Film> searchByGenere(Collection<Genere> nomiGeneri) {
        HashSet<Film> risultati = new HashSet<>();
        if(!nomiGeneri.isEmpty()) {

            List<Genere> generi = new ArrayList<>();
            for(Genere g : nomiGeneri) {
                if(genereRepository.existsById(g.getNomeGenere())) {
                    generi.add(genereRepository.findById(g.getNomeGenere()).get());
                }
            }

            for(Genere g : generi) {
                Set<Media> media = g.getMediaCollegati().stream().filter(media1 -> media1.getVisibile()).collect(Collectors.toSet());
                for(Media m : media) {
                    if(m instanceof Film)
                        risultati.add((Film) m);
                }
            }
            return risultati;
        }
        return risultati;
    }

    /**
     * Restituisce i "howMany" film più recenti
     * @param howMany quanti film restituire
     * @return la lista dei "howMany" film più recenti
     */
    public List<Film> findMostRecentFilm(Integer howMany) {
        List<Film> mostRecentFilm = new ArrayList<>();
        List<Film> film = filmRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        int size = film.size();
        if(howMany >= 0) {
            if (size <= howMany) {
                mostRecentFilm.addAll(film);
            } else {
                for (int i = 1; i <= howMany; i++) {
                    mostRecentFilm.add(film.get(i));
                }
            }
            return mostRecentFilm;
        }
        return mostRecentFilm;
    }

}

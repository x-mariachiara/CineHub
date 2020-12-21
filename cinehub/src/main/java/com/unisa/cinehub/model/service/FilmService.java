package com.unisa.cinehub.model.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Genere;
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



    public void addFilm(Film film){
        //TODO Aggiungere logica controlli
        filmRepository.save(film);
    }

    public List<Film> retrieveAll() {
        return  filmRepository.findAll();
    }

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
            if (!genereRepository.findById(g.getNomeGenere()).isPresent()) {
                logger.info("Genere " + g.getNomeGenere() + " mai inserito prima. Aggiungo.");
                genereRepository.save(g);
            }
        }


        film.getGeneri().addAll(generi);
        filmRepository.save(film);
    }
}

package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.model.service.FilmService;
import com.unisa.cinehub.model.service.PuntataService;
import com.unisa.cinehub.model.service.SerieTVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/gestionecatalogo")
public class GestioneCatalogoControl {
    private static Logger logger = Logger.getLogger("gestioneCatalogoControl");


    @Autowired
    private FilmService filmService;
    @Autowired
    private SerieTVService serieTVService;
    @Autowired
    private PuntataService puntataService;

    public GestioneCatalogoControl(FilmService filmService, SerieTVService serieTVService, PuntataService puntataService) {
        this.filmService = filmService;
        this.serieTVService = serieTVService;
        this.puntataService = puntataService;
    }

    @PostMapping("add/film")
    public void addFilm(@RequestBody Film film) {
        logger.info("Film da aggiungere: " + film);
        filmService.addFilm(film);
    }

    @PostMapping("add/serietv")
    public void addSerieTV(@RequestBody SerieTv serieTv) {
        logger.info("SerieTV da aggiungere: " + serieTv);
        serieTVService.addSerieTV(serieTv);
    }

    @PostMapping("add/puntata")
    public void addPuntata(@RequestBody Puntata puntata, @RequestParam("idserietv") Long idSerieTv, @RequestParam("numerostagione") Integer numeroStagione) {
        logger.info("Puntata da aggiungere: " + puntata + "\nper la serie tv: " + idSerieTv + "\nalla stagione: " + numeroStagione);
        puntataService.addPuntata(puntata, numeroStagione, idSerieTv);
    }

    @GetMapping("request/all/film")
    public List<Film> findAllFilm() {
        return filmService.retrieveAll();
    }

    @GetMapping("request/bystagione/puntata")
    public List<Puntata> puntateByStagione(@RequestParam("idserietv") Long idSerieTv, @RequestParam("numerostagione") Integer numeroStagione) {
        return puntataService.retrieveByStagione(idSerieTv, numeroStagione);
    }

    @PostMapping("request/key/film")
    public Film findFilmById(@RequestParam("id") Long id) {
        logger.info("id del film cercato: " + id);
        return filmService.retrieveByKey(id);
    }

    @PostMapping("request/title/film")
    public List<Film> searchFilmByTitle(@RequestBody String titolo){
        logger.info("Effettuata ricerca per titolo: " + titolo);
        return filmService.searchByTitle(titolo);
    }

    @PostMapping("request/genere/film")
    public Collection<Film> searchFilmByGenere(@RequestBody Collection<Genere> generi) {
        logger.info("Effettuata ricerca per generi: " + generi);
        return filmService.searchByGenere(generi);
    }

    @PostMapping("update/film")
    public void updateFilm(@RequestBody Film film) {
        filmService.mergeFilm(film);
    }

    @PostMapping("remove/film")
    public void removeFilm(@RequestParam("id") Long id) { filmService.removeFilm(id); }

    @PostMapping("addGeneri/film")
    public void addGeneriFilm(@RequestBody Collection<Genere> generi, @RequestParam("id") Long id) {
        logger.info("Generi da aggiungere: {" + generi + "} al film con id: " + id + "");
        filmService.addGeneri(generi, id);
    }

}

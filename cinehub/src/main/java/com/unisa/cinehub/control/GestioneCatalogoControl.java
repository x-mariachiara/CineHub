package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.model.service.FilmService;
import com.unisa.cinehub.model.service.PuntataService;
import com.unisa.cinehub.model.service.SerieTVService;
import org.atmosphere.config.service.Post;
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

    @GetMapping("request/all/serietv")
    public List<SerieTv> findAllSerieTv() { return serieTVService.retrieveAll(); }

    @GetMapping("request/bystagione/puntata")
    public List<Puntata> puntateByStagione(@RequestParam("idserietv") Long idSerieTv, @RequestParam("numerostagione") Integer numeroStagione) {
        return puntataService.retrieveByStagione(idSerieTv, numeroStagione);
    }

    @GetMapping("request/byserietv/puntata")
    public List<Puntata> puntataBySerie(@RequestParam("idserietv") Long idSerieTv) {
        return puntataService.retrieveBySerieTV(idSerieTv);
    }

    @GetMapping("request/key/film")
    public Film findFilmById(@RequestParam("id") Long id) {
        logger.info("id del film cercato: " + id);
        return filmService.retrieveByKey(id);
    }

    @GetMapping("request/key/serietv")
    public SerieTv findSerieTvById(@RequestParam("id") Long id) {
        logger.info("id della serie tv cercata: " + id);
        return serieTVService.retrieveByKey(id);
    }

    @GetMapping("request/key/puntata")
    public Puntata findPuntataById(@RequestBody Puntata.PuntataID puntataID) {
        return puntataService.retrievePuntataByKey(puntataID);
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

    @PostMapping("update/serietv")
    public void updateSerieTv(@RequestBody SerieTv serieTv) { serieTVService.mergeSerieTV(serieTv); }

    @PostMapping("update/puntata")
    public void updatePuntata(@RequestBody Puntata puntata) { puntataService.mergePuntata(puntata); }

    @PostMapping("remove/film")
    public void removeFilm(@RequestParam("id") Long id) { filmService.removeFilm(id); }

    @PostMapping("remove/serietv")
    public void removeSerieTV(@RequestParam("id") Long id) {
        serieTVService.removeSerieTV(id);
    }

    @PostMapping("remove/puntata")
    public void removePuntata(@RequestParam("id")Puntata.PuntataID id) { puntataService.removePuntata(id); }

    @PostMapping("addGeneri/film")
    public void addGeneriFilm(@RequestBody Collection<Genere> generi, @RequestParam("id") Long id) {
        logger.info("Generi da aggiungere: {" + generi + "} al film con id: " + id + "");
        filmService.addGeneri(generi, id);
    }

    @PostMapping("addGeneri/serietv")
    public void addGeneriSerieTv(@RequestBody Collection<Genere> generi, @RequestParam("id") Long id) {
        logger.info("Generi da aggiungere: {" + generi + "} alla serie tv con id: " + id);
        serieTVService.addGeneri(generi, id);
    }
}

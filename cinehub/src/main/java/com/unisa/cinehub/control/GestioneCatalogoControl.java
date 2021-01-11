package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
    @Autowired
    private CastService castService;
    @Autowired
    private RuoloService ruoloService;

    public GestioneCatalogoControl(FilmService filmService, SerieTVService serieTVService, PuntataService puntataService, CastService castService, RuoloService ruoloService) {
        this.filmService = filmService;
        this.serieTVService = serieTVService;
        this.puntataService = puntataService;
        this.castService = castService;
        this.ruoloService = ruoloService;
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

    @PostMapping("add/cast")
    public void addCast(@RequestBody Cast cast) {
        logger.info("Cast da aggiungere: " + cast);
        castService.addCast(cast);
    }

    @PostMapping("add/ruolo")
    public void addRuolo(@RequestBody Ruolo ruolo, @RequestParam("castid") Long castId, @RequestParam("mediaid") Long mediaId){
        logger.info("Ruolo da aggiungere: " + ruolo + " al media con id: " + mediaId + " riferito al cast con id: " + castId);
        ruoloService.addRuolo(ruolo, castId, mediaId);
    }

    @GetMapping("request/all/film")
    public List<Film> findAllFilm() {
        return filmService.retrieveAll();
    }

    @GetMapping("request/all/serietv")
    public List<SerieTv> findAllSerieTv() { return serieTVService.retrieveAll(); }

    @GetMapping("request/mostRecent")
    public List<Media> findMostRecentMedia(@RequestParam("howMany") Integer howMany) {
        List<Media> mostRecentMedia = new ArrayList<>();
        mostRecentMedia.addAll(filmService.findMostRecentFilm(howMany));
        mostRecentMedia.addAll(serieTVService.findMostRecentSerieTv(howMany));
        return  mostRecentMedia;
    }

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

    @PostMapping("addRuoli/film")
    public void addRuoliFilm(@RequestBody Collection<Ruolo> ruoli, @RequestParam("id") Long id) {
        logger.info("Ruoli da aggiungere : {" + ruoli + "} al film con id: " + id);
        filmService.addCast(ruoli, id);
    }

    @PostMapping("sortrecensioni")
    public List<Recensione> findRecensioniByMiPiace(@RequestBody Recensibile recensibile) {
        if(recensibile instanceof Film){
            Film film = filmService.retrieveByKey(((Film) recensibile).getId());
            recensibile = (Recensibile) film;
        } else {
            Puntata puntata = puntataService.retrievePuntataByKey(new Puntata.PuntataID(((Puntata) recensibile).getNumeroPuntata(), ((Puntata) recensibile).getStagioneId()));
            recensibile = (Recensibile) puntata;
        }
        List<Recensione> recensioni = recensibile.getListaRecensioni();
        recensioni.sort(new Comparator<Recensione>() {
            @Override
            public int compare(Recensione o1, Recensione o2) {
                int numMiPiace1 = o1.getListaMiPiace().size();
                int numMiPiace2 = o2.getListaMiPiace().size();
                return numMiPiace1 < numMiPiace2 ? 1 :  numMiPiace1 == numMiPiace2 ?  0 :  -1;
            }
        });
        return recensioni;
    }
}

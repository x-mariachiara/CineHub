package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.service.*;
import com.unisa.cinehub.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    @Autowired
    private RecensioneService recensioneService;
    @Autowired
    private UtenteService utenteService;

    public GestioneCatalogoControl(FilmService filmService, SerieTVService serieTVService, PuntataService puntataService, CastService castService, RuoloService ruoloService, RecensioneService recensioneService, UtenteService utenteService) {
        this.filmService = filmService;
        this.serieTVService = serieTVService;
        this.puntataService = puntataService;
        this.castService = castService;
        this.ruoloService = ruoloService;
        this.recensioneService = recensioneService;
        this.utenteService = utenteService;
    }

    @PostMapping("add/film")
    public Film addFilm(@RequestBody Film film) throws NotAuthorizedException, InvalidBeanException, AlreadyExsistsException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("Film da aggiungere: " + film);
            return filmService.addFilm(film);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("add/serietv")
    public SerieTv addSerieTV(@RequestBody SerieTv serieTv) throws NotAuthorizedException, AlreadyExsistsException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("SerieTV da aggiungere: " + serieTv);
            return serieTVService.addSerieTV(serieTv);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("add/puntata")
    public void addPuntata(@RequestBody Puntata puntata, @RequestParam("idserietv") Long idSerieTv, @RequestParam("numerostagione") Integer numeroStagione) throws NotAuthorizedException, InvalidBeanException, AlreadyExsistsException, BeanNotExsistException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("Puntata da aggiungere: " + puntata + "\nper la serie tv: " + idSerieTv + "\nalla stagione: " + numeroStagione);
            puntataService.addPuntata(puntata, numeroStagione, idSerieTv);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("add/cast")
    public void addCast(@RequestBody Cast cast) throws NotAuthorizedException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("Cast da aggiungere: " + cast);
            castService.addCast(cast);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("add/ruolo")
    public void addRuolo(@RequestBody Ruolo ruolo, @RequestParam("castid") Long castId, @RequestParam("mediaid") Long mediaId) throws NotAuthorizedException, BeanNotExsistException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("Ruolo da aggiungere: " + ruolo + " al media con id: " + mediaId + " riferito al cast con id: " + castId);
            ruoloService.addRuolo(ruolo, castId, mediaId);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @GetMapping("request/all/film")
    public List<Film> findAllFilm() {
        return filmService.retrieveAll();
    }

    @GetMapping("request/all/serietv")
    public List<SerieTv> findAllSerieTv() { return serieTVService.retrieveAll(); }

    @GetMapping("request/mostrecent")
    public List<Media> findMostRecentMedia(@RequestParam("howMany") Integer howMany) {
        List<Media> mostRecentMedia = new ArrayList<>();
        mostRecentMedia.addAll(filmService.findMostRecentFilm(howMany));
        mostRecentMedia.addAll(serieTVService.findMostRecentSerieTv(howMany));
        return  mostRecentMedia;
    }

    @GetMapping("request/mostvoted")
    public List<Media> findMostVoted() {
        List<Media> mostVotedMedia = new ArrayList<>();
        mostVotedMedia.addAll(filmService.retrieveAll());
        mostVotedMedia.addAll(serieTVService.retrieveAll());
        mostVotedMedia.sort(new Comparator<Media>() {
            @Override
            public int compare(Media o1, Media o2) {
                Double voti1, voti2;
                if(o1 instanceof Film) {
                    voti1 = ((Film) o1).getMediaVoti();
                } else {
                    voti1 = ((SerieTv) o1).getMediaVoti();
                }
                if(o2 instanceof Film) {
                    voti2 = ((Film) o2).getMediaVoti();
                } else {
                    voti2 = ((SerieTv) o2).getMediaVoti();
                }
                return voti1 < voti2 ? 1 : voti1 == voti2 ? 0 : -1;
            }
        });
        return mostVotedMedia;
    }

    @GetMapping("request/bystagione/puntata")
    public List<Puntata> puntateByStagione(@RequestParam("idserietv") Long idSerieTv, @RequestParam("numerostagione") Integer numeroStagione) throws InvalidBeanException, BeanNotExsistException {
        return puntataService.retrieveByStagione(idSerieTv, numeroStagione);
    }

    @GetMapping("request/byserietv/puntata")
    public List<Puntata> puntataBySerie(@RequestParam("idserietv") Long idSerieTv) throws InvalidBeanException, BeanNotExsistException {
        return puntataService.retrieveBySerieTV(idSerieTv);
    }

    @GetMapping("request/key/film")
    public Film findFilmById(@RequestParam("id") Long id) {
        logger.info("id del film cercato: " + id);
        return filmService.retrieveByKey(id);
    }

    @GetMapping("request/key/serietv")
    public SerieTv findSerieTvById(@RequestParam("id") Long id) throws InvalidBeanException, BeanNotExsistException {
        logger.info("id della serie tv cercata: " + id);
        return serieTVService.retrieveByKey(id);
    }

    @GetMapping("request/key/puntata")
    public Puntata findPuntataById(@RequestBody Puntata.PuntataID puntataID) throws BeanNotExsistException {
        return puntataService.retrievePuntataByKey(puntataID);
    }

    @PostMapping("request/genere/film")
    public Collection<Film> searchFilmByGenere(@RequestBody Collection<Genere> generi) {
        logger.info("Effettuata ricerca per generi: " + generi);
        return filmService.searchByGenere(generi);
    }

    @PostMapping("request/genere/serietv")
    public Collection<SerieTv> searchSerieTVByGenere(@RequestBody Collection<Genere> generi) throws InvalidBeanException {
        logger.info("Effettuata ricerca per generi: " + generi);
        return serieTVService.searchByGenere(generi);
    }

    @PostMapping("request/title/film")
    public List<Film> searchFilmByTitle(@RequestBody String titolo){
        logger.info("Effettuata ricerca per titolo: " + titolo);
        return filmService.searchByTitle(titolo);
    }

    @PostMapping("request/all/puntata")
    public List<Puntata>  findAllPuntate() {
        return puntataService.retrieveAll();
    }

    @PostMapping("request/title/puntata")
    public List<Puntata>  findPuntataByTitle(@RequestBody String titolo) {
        return puntataService.searchByTitle(titolo);
    }

    @PostMapping("request/title/serietv")
    public List<SerieTv> searchSerieTvByTitle(@RequestBody String titolo) {
        logger.info("Effettuata ricerca per titolo: " + titolo);
        return serieTVService.searchByTitle(titolo);
    }

    @PostMapping("request/all/cast")
    public List<Cast> findAllCast() {
        return castService.retrieveAll();
    }

    @PostMapping("update/film")
    public void updateFilm(@RequestBody Film film) throws NotAuthorizedException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            filmService.mergeFilm(film);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("update/serietv")
    public void updateSerieTv(@RequestBody SerieTv serieTv) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            serieTVService.mergeSerieTV(serieTv);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("update/puntata")
    public void updatePuntata(@RequestBody Puntata puntata) throws NotAuthorizedException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            puntataService.mergePuntata(puntata);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("remove/film")
    public void removeFilm(@RequestParam("id") Long id) throws NotAuthorizedException, BeanNotExsistException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            filmService.removeFilm(id);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("remove/serietv")
    public void removeSerieTV(@RequestParam("id") Long id) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            serieTVService.removeSerieTV(id);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("remove/puntata")
    public void removePuntata(@RequestParam("id")Puntata.PuntataID id) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            puntataService.removePuntata(id);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("remove/cast")
    public void removeCast(Long id) throws NotAuthorizedException, BeanNotExsistException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            castService.removeCast(id);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("add/addGeneri/film")
    public void addGeneriFilm(@RequestBody Collection<Genere> generi, @RequestParam("id") Long id) throws NotAuthorizedException, BeanNotExsistException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("Generi da aggiungere: {" + generi + "} al film con id: " + id + "");
            filmService.addGeneri(generi, id);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("add/addGeneri/serietv")
    public void addGeneriSerieTv(@RequestBody Collection<Genere> generi, @RequestParam("id") Long id) throws NotAuthorizedException, BeanNotExsistException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("Generi da aggiungere: {" + generi + "} alla serie tv con id: " + id);
            serieTVService.addGeneri(generi, id);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("add/addRuoli/film")
    public void addRuoliFilm(@RequestBody Collection<Ruolo> ruoli, @RequestParam("id") Long id) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("Ruoli da aggiungere : {" + ruoli + "} al film con id: " + id);
            filmService.addCast(ruoli, id);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("sortrecensioni")
    public List<Recensione> findRecensioniByMiPiace(@RequestBody Recensibile recensibile) throws BeanNotExsistException {
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

    @PostMapping("request/all/recensione")
    public List<Recensione> requestAllRecensioni () {
        return recensioneService.retrieveAll();
    }
}

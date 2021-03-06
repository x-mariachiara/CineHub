package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.media.CastService;
import com.unisa.cinehub.model.media.RuoloService;
import com.unisa.cinehub.model.media.film.FilmService;
import com.unisa.cinehub.model.media.serietv.PuntataService;
import com.unisa.cinehub.model.media.serietv.SerieTVService;
import com.unisa.cinehub.model.recensione.RecensioneService;
import com.unisa.cinehub.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private RecensioneService recensioneService;


    public GestioneCatalogoControl(FilmService filmService, SerieTVService serieTVService, PuntataService puntataService, CastService castService, RuoloService ruoloService, RecensioneService recensioneService) {
        this.filmService = filmService;
        this.serieTVService = serieTVService;
        this.puntataService = puntataService;
        this.castService = castService;
        this.ruoloService = ruoloService;
        this.recensioneService = recensioneService;
    }
    

    @PostMapping("add/film")
    public Film addFilm(@RequestBody Film film) throws NotAuthorizedException, InvalidBeanException, AlreadyExsistsException, BeanNotExsistException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("Film da aggiungere: " + film);
            return filmService.addFilm(film);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("add/serietv")
    public SerieTv addSerieTV(@RequestBody SerieTv serieTv) throws NotAuthorizedException, AlreadyExsistsException, InvalidBeanException, BeanNotExsistException {
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
    public void addCast(@RequestBody Cast cast) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {
            logger.info("Cast da aggiungere: " + cast);
            castService.addCast(cast);
        } else {
            throw new NotAuthorizedException();
        }
    }


    @PostMapping("add/ruolo")
    public void addRuolo(@RequestBody Collection<Ruolo> ruolo, @RequestParam("mediaid") Long mediaId) throws NotAuthorizedException, BeanNotExsistException, InvalidBeanException {
        Utente utente = SecurityUtils.getLoggedIn();
        if(utente instanceof ResponsabileCatalogo) {

            try {
                logger.info("Aggiunta ruoli: " + ruolo + " al film conm id: " +  mediaId);
                filmService.retrieveByKey(mediaId);
                filmService.addCast(ruolo, mediaId);
            } catch (BeanNotExsistException  e) {
                logger.info("Aggiunta ruoli: " + ruolo + " alla serie con id: " +  mediaId);
                serieTVService.retrieveByKey(mediaId);
                serieTVService.addCast(ruolo, mediaId);
            }

        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("request/all/cast")
    public List<Cast> findAllCast() {
        return castService.retrieveAll();
    }

    @PostMapping("update/film")
    public void updateFilm(@RequestBody Film film) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
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
    public void updatePuntata(@RequestBody Puntata puntata) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
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


    @PostMapping("sortrecensioni")
    public List<Recensione> findRecensioniByMiPiace(@RequestBody Recensibile recensibile) throws BeanNotExsistException, InvalidBeanException {
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

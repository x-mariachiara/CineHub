package com.unisa.cinehub.control;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.exception.NotLoggedException;
import com.unisa.cinehub.model.service.*;
import com.unisa.cinehub.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/catalogo")
public class CatalogoControl {

    @Autowired
    RecensioneService recensioneService;

    @Autowired
    MiPiaceService miPiaceService;

    @Autowired
    UtenteService utenteService;

    @Autowired
    FilmService filmService;

    @Autowired
    SerieTVService serieTVService;

    @Autowired
    PuntataService puntataService;

    private static Logger logger = Logger.getLogger("catalogoControl");

    public CatalogoControl(RecensioneService recensioneService, MiPiaceService miPiaceService, UtenteService utenteService) {
        this.recensioneService = recensioneService;
        this.miPiaceService = miPiaceService;
        this.utenteService = utenteService;
    }

    @PostMapping("add/recensione")
    public void addRecensione(@RequestBody Recensione recensione) throws NotLoggedException, NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {

                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                recensioneService.addRecensione(recensione, recensore);
            } catch (ClassCastException e) {
                throw new NotAuthorizedException();
            }
        } else {
            throw new NotLoggedException();
        }
    }
    @PostMapping("request/key/recensione")
    public Recensione requestRecensioneById(@RequestBody Long id) throws InvalidBeanException, BeanNotExsistException {
        return recensioneService.retrieveById(id);
    }



    @GetMapping("add/mipiace")
    public void addMiPiace(@RequestParam("tipo") boolean b, @RequestBody Recensione recensione) throws NotLoggedException, NotAuthorizedException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                miPiaceService.handleMiPiace(b, recensione, recensore);
            } catch (ClassCastException | InvalidBeanException | BeanNotExsistException e) {
                throw new NotAuthorizedException();
            }
        } else {
            throw new NotLoggedException();
        }
    }

    @PostMapping("request/key/mipiace")
    public MiPiace findMyPiaceById(@RequestBody Recensione recensione) throws NotAuthorizedException, InvalidBeanException, BeanNotExsistException, NotLoggedException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                return miPiaceService.findMiPiaceById(recensore, recensione);
            } catch (ClassCastException e) {
                throw new NotAuthorizedException();
            }
        }
        else throw new NotLoggedException();
    }

    @PostMapping("request/num/mipiace")
    public Integer getNumeroMiPiaceOfRecensione(@RequestBody Recensione recensione){
        return miPiaceService.getNumeroMiPiaceOfRecensione(recensione);
    }

    @PostMapping("request/num/nonmipiace")
    public Integer getNumeroNonMiPiaceOfRecensione(@RequestBody Recensione recensione){
        return miPiaceService.getNumeroNonMiPiaceOfRecensione(recensione);
    }

    @PostMapping("add/risposta")
    public void rispondiARecensione(@RequestBody Recensione risposta, @Param("id") Long idPadre) throws NotLoggedException, NotAuthorizedException, InvalidBeanException, BeanNotExsistException {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Recensore recensore = (Recensore) SecurityUtils.getLoggedIn();
                recensioneService.addRisposta(recensore, risposta, idPadre);
            } catch (ClassCastException e) {
                throw new NotAuthorizedException();
            }
        } else {
            throw new NotLoggedException();
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

    @GetMapping("request/bystagione/puntata")
    public List<Puntata> puntateByStagione(@RequestParam("idserietv") Long idSerieTv, @RequestParam("numerostagione") Integer numeroStagione) throws InvalidBeanException, BeanNotExsistException {
        return puntataService.retrieveByStagione(idSerieTv, numeroStagione);
    }

    @GetMapping("request/byserietv/puntata")
    public List<Puntata> puntataBySerie(@RequestParam("idserietv") Long idSerieTv) throws InvalidBeanException, BeanNotExsistException {
        return puntataService.retrieveBySerieTV(idSerieTv);
    }

    @GetMapping("request/key/film")
    public Film findFilmById(@RequestParam("id") Long id) throws InvalidBeanException, BeanNotExsistException {
        logger.info("id del film cercato: " + id);
        return filmService.retrieveByKey(id);
    }

    @GetMapping("request/key/serietv")
    public SerieTv findSerieTvById(@RequestParam("id") Long id) throws InvalidBeanException, BeanNotExsistException {
        logger.info("id della serie tv cercata: " + id);
        return serieTVService.retrieveByKey(id);
    }

    @GetMapping("request/key/puntata")
    public Puntata findPuntataById(@RequestBody Puntata.PuntataID puntataID) throws BeanNotExsistException, InvalidBeanException {
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


}

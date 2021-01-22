package com.unisa.cinehub.test.integration;

import com.unisa.cinehub.Application;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.data.repository.*;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.test.PSQLTestJPAConfig;
import com.unisa.cinehub.test.SecurityTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = {Application.class, PSQLTestJPAConfig.class, SecurityTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TestGestioneCatalogoControl {

    /* classe sotto test */
    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;

    /* repository per il popolamento del db */
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private SerieTVRepository serieTVRepository;
    @Autowired
    private StagioneRepository stagioneRepository;
    @Autowired
    private PuntataRepository puntataRepository;
    @Autowired
    private GenereRepository genereRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private CastRepository castRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private RecensioneRepository recensioneRepository;

    /* Generi */
    private Genere azione = new Genere(Genere.NomeGenere.AZIONE);
    private Genere drammatico = new Genere(Genere.NomeGenere.DRAMMATICI);
    private Genere romantico = new Genere(Genere.NomeGenere.ROMANTICO);

    /* Utenti */
    private ResponsabileCatalogo responsabile = new ResponsabileCatalogo("catalogo@gmail.com", "Cata", "Logo", LocalDate.of(1996, 2, 6),"caty", "pass", false, true);
    private Recensore recensore = new Recensore("recensore@gmail.com", "Recen", "Sore", LocalDate.of(1996, 2, 4),"recy", "pass", false, true);


    /* Recensioni */
    private Recensione recensioneCasaDiCarta = new Recensione("Bel film", 4);
    private Recensione recensioneBabyDriver = new Recensione("così così", 2);

    /* Film */
    private Film babyDriver = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");

    /* SerieTv */
    private SerieTv casaDiCarta = new SerieTv("La casa di carta", 2016, "Gente mascherata rapina una banca", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://www.youtube.com/embed/oFiLrgCuFXo");

    /* Stagione */
    private Stagione stagione = new Stagione(1);

    /* Puntata */
    private Puntata puntata = new Puntata("Bella raga", 1, "signa i limoni signoraaaaa");

    /* Cast */
    private Cast cast = new Cast("Alvaro", "Morte");

    /* Ruolo */
    private Ruolo attore = new Ruolo(Ruolo.Tipo.ATTORE);
    private Ruolo regista = new Ruolo(Ruolo.Tipo.REGISTA);

    @BeforeEach
    @Transactional
    public void MyNameIsGiovanniGiorgioButEverybodyCallsMeGiorgio() {
        //padri
        genereRepository.saveAll(Arrays.asList(azione, drammatico, romantico));
        utenteRepository.saveAll(Arrays.asList(responsabile, recensore));
        castRepository.save(cast);

        //relazione film - genere
        babyDriver.getGeneri().addAll(Arrays.asList(romantico, azione, drammatico));

        azione.getMediaCollegati().add(babyDriver);
        drammatico.getMediaCollegati().add(babyDriver);
        romantico.getMediaCollegati().add(babyDriver);

        filmRepository.save(babyDriver);
        genereRepository.saveAll(Arrays.asList(azione, drammatico, romantico));

        //Relazione film - attore - cast
        attore.setCast(cast);
        attore.setMedia(babyDriver);
        cast.getRuoli().add(attore);
        babyDriver.getRuoli().add(attore);

        ruoloRepository.save(attore);
        filmRepository.save(babyDriver);
        castRepository.save(cast);

        //Relazione serietv - genere
        casaDiCarta.getGeneri().addAll(Arrays.asList(azione, drammatico));

        azione.getMediaCollegati().add(babyDriver);
        drammatico.getMediaCollegati().add(babyDriver);

        serieTVRepository.save(casaDiCarta);
        genereRepository.saveAll(Arrays.asList(azione, drammatico));

        //Relazione serietv - attore - cast
        regista.setCast(cast);
        regista.setMedia(casaDiCarta);
        cast.getRuoli().add(regista);
        casaDiCarta.getRuoli().add(regista);

        ruoloRepository.save(regista);
        serieTVRepository.save(casaDiCarta);
        castRepository.save(cast);

        //Relazione serietv - stagione - puntata

        stagione.setSerieTv(casaDiCarta);
        puntata.setStagione(stagione);
        casaDiCarta.getStagioni().add(stagione);
        stagione.getPuntate().add(puntata);

        serieTVRepository.save(casaDiCarta);
        stagioneRepository.save(stagione);
        puntataRepository.save(puntata);


        // recensioni - recensore
        recensioneBabyDriver.setRecensore(recensore);
        recensioneBabyDriver.setFilm(babyDriver);
        recensioneCasaDiCarta.setRecensore(recensore);
        recensioneCasaDiCarta.setPuntata(puntata);
        recensore.getListaRecensioni().addAll(Arrays.asList(recensioneBabyDriver, recensioneCasaDiCarta));

        babyDriver.getListaRecensioni().add(recensioneBabyDriver);
        puntata.getListaRecensioni().add(recensioneCasaDiCarta);

        recensioneRepository.saveAll(Arrays.asList(recensioneBabyDriver, recensioneCasaDiCarta));
        utenteRepository.save(recensore);
        filmRepository.save(babyDriver);
        puntataRepository.save(puntata);


    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void addFilm_valid() {
        Film oracolo = new Film("titolo",2020,"sinossi","https://www.youtube.com/embed/oFiLrgCuFXo","https://www.youtube.com/embed/oFiLrgCuFXo");
        oracolo.setGeneri(new HashSet<>(Arrays.asList(azione, drammatico, romantico)));

        try {
            gestioneCatalogoControl.addFilm(oracolo);
            assertTrue(filmRepository.exists(Example.of(oracolo)));

        } catch (NotAuthorizedException | InvalidBeanException | AlreadyExsistsException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addFilm_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, ()->gestioneCatalogoControl.addFilm(new Film()));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void addSerieTv_valid() {
        SerieTv oracolo = new SerieTv("titolo",2020,"sinossi","https://www.youtube.com/embed/oFiLrgCuFXo","https://www.youtube.com/embed/oFiLrgCuFXo");
        oracolo.setGeneri(new HashSet<>(Arrays.asList(azione, drammatico, romantico)));

        try {
            gestioneCatalogoControl.addSerieTV(oracolo);
            assertTrue(serieTVRepository.exists(Example.of(oracolo)));
        } catch (NotAuthorizedException | AlreadyExsistsException | InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addSerieTv_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, ()->gestioneCatalogoControl.addSerieTV(new SerieTv()));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void addPuntata_valid() {
        Puntata oracolo = new Puntata("titolo puntata", 2, "sinossi puntata");

        try {
            gestioneCatalogoControl.addPuntata(oracolo, casaDiCarta.getId(), stagione.getNumeroStagione());
            Puntata appenaAggiunta = puntataRepository.findAll().get(puntataRepository.findAll().size()-1);
            assertTrue(puntataRepository.existsById(new Puntata.PuntataID(appenaAggiunta.getNumeroPuntata(), appenaAggiunta.getStagioneId())));
            assertTrue(serieTVRepository.getOne(casaDiCarta.getId()).getStagioni().contains(stagione) && stagioneRepository.getOne(puntata.getStagioneId()).getPuntate().contains(appenaAggiunta));
        } catch (NotAuthorizedException | AlreadyExsistsException | InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addPuntata_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.addPuntata(new Puntata(), 1l, 1));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void addCast_valid() {
        Cast oracolo = new Cast("nome", "cognome");
        try {
            gestioneCatalogoControl.addCast(oracolo);
            Cast appenaAggiunto = castRepository.findAll().get(castRepository.findAll().size()-1);
            assertTrue(castRepository.existsById(appenaAggiunto.getId()));
        } catch (InvalidBeanException | BeanNotExsistException | NotAuthorizedException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addCast_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.addCast(new Cast()));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void addRuolo_valid() {
        Ruolo nuovoRuolo = new Ruolo(Ruolo.Tipo.VOICEACTOR);
        try {
            gestioneCatalogoControl.addRuolo(nuovoRuolo, cast.getId(), babyDriver.getId());
            Ruolo appenaAggiunto = ruoloRepository.getOne(new Ruolo.RuoloID(Ruolo.Tipo.VOICEACTOR, cast.getId(), babyDriver.getId()));
            System.out.println("test: " + appenaAggiunto);
            System.out.println("Cast: " + cast.getRuoli());
            assertTrue(ruoloRepository.existsById(new Ruolo.RuoloID(Ruolo.Tipo.VOICEACTOR, cast.getId(), babyDriver.getId())));
            assertTrue(cast.getRuoli().contains(appenaAggiunto));
            assertTrue(babyDriver.getRuoli().contains(appenaAggiunto));
        } catch (NotAuthorizedException | BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addRuolo_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.addRuolo(new Ruolo(Ruolo.Tipo.ATTORE), 1l, 1l));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void updateFilm_valid() {
        babyDriver.setSinossi("ddfg");
        Film oracolo = babyDriver;
        try {
            gestioneCatalogoControl.updateFilm(babyDriver);
            assertEquals(oracolo, filmRepository.getOne(babyDriver.getId()));
        } catch (NotAuthorizedException | InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void updateFilm_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.updateFilm(new Film()));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void updateSerieTv_valid() {
        casaDiCarta.setSinossi("ddfg");
        SerieTv oracolo = casaDiCarta;
        try {
            gestioneCatalogoControl.updateSerieTv(casaDiCarta);
            assertEquals(oracolo, serieTVRepository.getOne(casaDiCarta.getId()));
        } catch (NotAuthorizedException | InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void updateSerieTv_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.updateSerieTv(new SerieTv()));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void updatePuntata_valid() {
        puntata.setSinossi("ddfg");
        Puntata oracolo = puntata;
        try {
            gestioneCatalogoControl.updatePuntata(puntata);
            assertEquals(oracolo, puntataRepository.getOne(new Puntata.PuntataID(puntata.getNumeroPuntata(), puntata.getStagioneId())));
        } catch (NotAuthorizedException | InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void updatePuntata_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.updatePuntata(new Puntata()));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void removeFilm_valid() {
        try {
            gestioneCatalogoControl.removeFilm(babyDriver.getId());
            assertFalse(filmRepository.existsById(babyDriver.getId()));
        } catch (NotAuthorizedException | BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void removeFilm_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.removeFilm(1l));
    }

    @Test
    @Transactional
    @WithUserDetails("catalogo@gmail.com")
    public void removeSerieTV_valid() {
        try {
            gestioneCatalogoControl.removeSerieTV(casaDiCarta.getId());
            assertFalse(serieTVRepository.existsById(casaDiCarta.getId()));
        } catch (NotAuthorizedException | BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void removeSerieTV_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.removeSerieTV(1l));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void removePuntata_valid() {
        try {
            gestioneCatalogoControl.removePuntata(new Puntata.PuntataID(puntata.getNumeroPuntata(), puntata.getStagioneId()));
            assertFalse(puntataRepository.existsById(new Puntata.PuntataID(puntata.getNumeroPuntata(), puntata.getStagioneId())));
        } catch (NotAuthorizedException | BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void removePuntata_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.removePuntata(new Puntata.PuntataID(1, new Stagione.StagioneID(1, 1l))));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void removeCast_valid() {
        try {
            gestioneCatalogoControl.removeCast(cast.getId());
            assertFalse(castRepository.existsById(cast.getId()));
        } catch (NotAuthorizedException | BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void removeCast_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.removeCast(1l));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void addGeneriFilm_valid() {
        try {
            HashSet<Genere> generi = new HashSet<>(Arrays.asList(new Genere(Genere.NomeGenere.GIALLO)));
            gestioneCatalogoControl.addGeneriFilm(generi, babyDriver.getId());
            assertTrue(filmRepository.getOne(babyDriver.getId()).getGeneri().equals(generi));
        } catch (NotAuthorizedException | BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addGeneriFilm_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.addGeneriFilm(new HashSet<>(), 1l));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void addGeneriSerieTv_valid() {
        try {
            HashSet<Genere> generi = new HashSet<>(Arrays.asList(new Genere(Genere.NomeGenere.GIALLO)));
            gestioneCatalogoControl.addGeneriSerieTv(generi, casaDiCarta.getId());
            assertTrue(serieTVRepository.getOne(casaDiCarta.getId()).getGeneri().equals(generi));
        } catch (NotAuthorizedException | BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addGeneriSerieTv_NotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> gestioneCatalogoControl.addGeneriSerieTv(new HashSet<>(), 1l));
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void findRecensioniByMiPiace_filmValid() {
        List<Recensione> oracolo = new ArrayList<>();
        oracolo.add(recensioneBabyDriver);

        try {
            assertEquals(oracolo, gestioneCatalogoControl.findRecensioniByMiPiace(babyDriver));
        } catch (BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("catalogo@gmail.com")
    @Transactional
    public void findRecensioniByMiPiace_puntataValid() {
        List<Recensione> oracolo = new ArrayList<>();
        oracolo.add(recensioneCasaDiCarta);

        try {
            assertEquals(oracolo, gestioneCatalogoControl.findRecensioniByMiPiace(puntata));
        } catch (BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }
}

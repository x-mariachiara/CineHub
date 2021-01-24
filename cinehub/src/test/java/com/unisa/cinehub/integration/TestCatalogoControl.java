package com.unisa.cinehub.integration;

import com.unisa.cinehub.Application;
import com.unisa.cinehub.PSQLTestJPAConfig;
import com.unisa.cinehub.SecurityTestConfig;
import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.data.repository.*;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.exception.NotLoggedException;
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
import java.util.Arrays;

import static org.junit.Assert.*;

@SpringBootTest(classes = {Application.class, PSQLTestJPAConfig.class, SecurityTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TestCatalogoControl {

    /* Classe sotto test */
    @Autowired
    private CatalogoControl catalogoControl;

    /* Repository per popolare il db di test */
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RecensioneRepository recensioneRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenereRepository genereRepository;

    @Autowired
    private RecensoreRepository recensoreRepository;

    @Autowired
    private MiPiaceRepository miPiaceRepository;

    /* Media */
    private Film babyDriver = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");

    /* Generi */
    private Genere azione = new Genere(Genere.NomeGenere.AZIONE);
    private Genere drammatico = new Genere(Genere.NomeGenere.DRAMMATICI);
    private Genere romantico = new Genere(Genere.NomeGenere.ROMANTICO);

    /* Utenti */
    private Recensore recensore = new Recensore("recensore@gmail.com", "Recen", "Sore", LocalDate.of(1996, 2, 4),"recy", "pass", false, true);
    private Moderatore moderatoreAccount = new Moderatore("account@gmail.com", "Acc", "Ount", LocalDate.of(1996, 5, 4),"accy", "pass", false, true, Moderatore.Tipo.MODACCOUNT);

    /* Recensioni */
    private Recensione recensioneBabyDriver = new Recensione("Nice! :)", 3);

    /* MiPiace */
    private MiPiace miPiace = new MiPiace(true);

    @BeforeEach
    @Transactional
    public void dinosauri(){

        //Salvo le entità padre
        genereRepository.saveAll(Arrays.asList(azione, drammatico, romantico));
        utenteRepository.saveAll(Arrays.asList(recensore, moderatoreAccount));

        // relazioni film - genere
        babyDriver.getGeneri().addAll(Arrays.asList(romantico, azione, drammatico));

        azione.getMediaCollegati().add(babyDriver);
        drammatico.getMediaCollegati().add(babyDriver);
        romantico.getMediaCollegati().add(babyDriver);

        filmRepository.save(babyDriver);
        genereRepository.saveAll(Arrays.asList(azione, drammatico, romantico));

        // recensioni
        recensioneBabyDriver.setRecensore(recensore);
        recensioneBabyDriver.setFilm(babyDriver);

        recensore.getListaRecensioni().add(recensioneBabyDriver);
        babyDriver.getListaRecensioni().add(recensioneBabyDriver);

        filmRepository.save(babyDriver);
        recensioneRepository.save(recensioneBabyDriver);
        utenteRepository.saveAll(Arrays.asList(recensore));

        // mipiace
        miPiace.setRecensione(recensioneBabyDriver);
        miPiace.setRecensore(recensore);

        recensioneBabyDriver.getListaMiPiace().add(miPiace);
        recensore.getListaMiPiace().add(miPiace);

        recensoreRepository.save(recensore);
        recensioneRepository.save(recensioneBabyDriver);
        miPiaceRepository.save(miPiace);
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addRecensione_valid() {
        Recensione oracolo = new Recensione("Bello", 5);
        oracolo.setFilm(babyDriver);
        oracolo.setRecensore(recensore);

        try {
            catalogoControl.addRecensione(oracolo);
            Recensione appenaAggiunta = recensioneRepository.findAll().get(recensioneRepository.findAll().size()-1);
            assertTrue(recensioneRepository.exists(Example.of(appenaAggiunta)));
            assertTrue(filmRepository.findById(babyDriver.getId()).get().getListaRecensioni().contains(appenaAggiunta));
            assertTrue(recensoreRepository.findById(recensore.getEmail()).get().getListaRecensioni().contains(appenaAggiunta));
        } catch (NotLoggedException | NotAuthorizedException | InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("account@gmail.com")
    @Transactional
    public void addRecensione_userNotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> catalogoControl.addRecensione(new Recensione("brutto", 2)));
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void addMiPiace_valid() {
        try {
            catalogoControl.addMiPiace(false, recensioneBabyDriver);
            assertTrue(miPiaceRepository.existsById(new MiPiace.MiPiaceID(recensore.getEmail(), recensioneBabyDriver.getId())));
        } catch (NotLoggedException | NotAuthorizedException e) {
            assert false;
        }
    }

    @Test
    @Transactional
    public void addMiPiace_userNotLogged() {
        assertThrows(NotLoggedException.class, () -> catalogoControl.addMiPiace(false, recensioneBabyDriver));
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void findMyPiaceById_valid() {
        MiPiace oracolo = new MiPiace(true);
        oracolo.setRecensore(recensore);
        oracolo.setRecensione(recensioneBabyDriver);

        try {
            assertEquals(oracolo, catalogoControl.findMyPiaceById(recensioneBabyDriver));
        } catch (NotAuthorizedException | InvalidBeanException | BeanNotExsistException | NotLoggedException e) {
            assert false;
        }
    }

    @Test
    @WithUserDetails("account@gmail.com")
    @Transactional
    public void findMyPiaceId_userNotAuthorized() {
        assertThrows(NotAuthorizedException.class, () -> catalogoControl.findMyPiaceById(recensioneBabyDriver));
    }

    @Test
    @WithUserDetails("recensore@gmail.com")
    @Transactional
    public void rispondiARecensione_valid() {
        Recensione daAggiungere = new Recensione();
        daAggiungere.setContenuto("non sono d'accordo");
        try {
            catalogoControl.rispondiARecensione(daAggiungere, recensioneBabyDriver.getId());
            Recensione oracolo = recensioneRepository.findAll().get(recensioneRepository.findAll().size()-1);
            assertTrue(recensioneRepository.exists(Example.of(oracolo)));
            assertTrue(recensioneRepository.findById(recensioneBabyDriver.getId()).get().getListaRisposte().contains(oracolo));
        } catch (NotLoggedException | NotAuthorizedException | InvalidBeanException | BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    @Transactional
    public void rispondiARecensione_NotLogged() {
        Recensione daAggiungere = new Recensione();
        daAggiungere.setContenuto("non sono d'accordo");
        assertThrows(NotLoggedException.class, () -> catalogoControl.rispondiARecensione(daAggiungere, recensioneBabyDriver.getId()));
    }

}

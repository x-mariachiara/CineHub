package com.unisa.cinehub.unit;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Segnalazione;
import com.unisa.cinehub.model.recensione.RecensioneRepository;
import com.unisa.cinehub.model.utente.RecensoreRepository;
import com.unisa.cinehub.model.utente.SegnalazioneRepository;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.utente.SegnalazioneService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;

@SpringBootTest
public class TestSegnalazioneService {

    @Autowired
    private SegnalazioneService segnalazioneService;

    @MockBean
    private SegnalazioneRepository segnalazioneRepository;

    @MockBean
    private RecensioneRepository recensioneRepository;

    @MockBean
    private RecensoreRepository recensoreRepository;

    @Test
    public void addSegnalazione_Valid(){
        Recensione recensione = new Recensione("bellissimo", 4);
        recensione.setId(1l);
        Recensore segnalatore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        Recensore recensore = new Recensore("g.cardaropoli99@gmail.com", "Giuseppe", "Cardaropoli", LocalDate.of(1999, 12, 3), "Peppe99", new BCryptPasswordEncoder().encode("pippo"), false, true);
        recensione.setRecensore(recensore);
        recensore.getListaRecensioni().add(recensione);

        //creo l'oracolo
        Segnalazione oracoloSegnalazione = new Segnalazione();
        oracoloSegnalazione.setSegnalatoreId(segnalatore.getEmail());
        oracoloSegnalazione.setRecensione(recensione);
        oracoloSegnalazione.setRecensore(recensore);

        Mockito.when(segnalazioneRepository.saveAndFlush(any(Segnalazione.class))).thenAnswer(i -> i.getArgument(0, Segnalazione.class));
        Mockito.when(recensioneRepository.saveAndFlush(any(Recensione.class))).thenAnswer(i -> i.getArgument(0, Recensione.class));
        Mockito.when(recensoreRepository.saveAndFlush(any(Recensore.class))).thenAnswer(i -> i.getArgument(0, Recensore.class));

        try {
            assertEquals(oracoloSegnalazione, segnalazioneService.addSegnalazione(recensione, segnalatore));
        } catch (InvalidBeanException e) {
            System.out.println(e.getMessage());
        } catch (NotAuthorizedException e) {
            System.out.println(e.getMessage());;
        }
    }

    @Test
    public void addSegnalazione_recensioneNull() {
        Recensore segnalatore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        assertThrows(InvalidBeanException.class, () -> segnalazioneService.addSegnalazione(null, segnalatore));
    }

    @Test
    public void addSegnalazione_segnalatoreNull() {
        Recensione recensione = new Recensione("bellissimo", 4);
        recensione.setId(1l);
        assertThrows(InvalidBeanException.class, () -> segnalazioneService.addSegnalazione(recensione, null));
    }

    @Test
    public void addSegnalazione_segnalatoreUgualeRecensore() {
        Recensione recensione = new Recensione("bellissimo", 4);
        recensione.setId(1l);
        Recensore segnalatore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        recensione.setRecensore(segnalatore);
        segnalatore.getListaRecensioni().add(recensione);
        assertThrows(NotAuthorizedException.class, () -> segnalazioneService.addSegnalazione(recensione, segnalatore));
    }

    @Test
    public void puoSegnalare_isValid() {
        Recensione recensione = new Recensione("bellissimo", 4);
        recensione.setId(1l);
        Recensore segnalatore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        Recensore recensore = new Recensore("g.cardaropoli99@gmail.com", "Giuseppe", "Cardaropoli", LocalDate.of(1999, 12, 3), "Peppe99", new BCryptPasswordEncoder().encode("pippo"), false, true);
        recensione.setRecensore(recensore);
        recensore.getListaRecensioni().add(recensione);

        Mockito.when(segnalazioneRepository.existsById(any(Segnalazione.SegnalazioneID.class))).thenReturn(false);

        assertTrue(segnalazioneService.puoSegnalare(recensione, segnalatore));
    }

    @Test
    public void puoSegnalare_recensioneNull() {
       Recensore segnalatore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);

        assertFalse(segnalazioneService.puoSegnalare(null, segnalatore));
    }

    @Test
    public void puoSegnalare_segnalatoreNull() {
        Recensione recensione = new Recensione("bellissimo", 4);
        recensione.setId(1l);
        Recensore recensore = new Recensore("g.cardaropoli99@gmail.com", "Giuseppe", "Cardaropoli", LocalDate.of(1999, 12, 3), "Peppe99", new BCryptPasswordEncoder().encode("pippo"), false, true);
        recensione.setRecensore(recensore);
        recensore.getListaRecensioni().add(recensione);

        assertFalse(segnalazioneService.puoSegnalare(recensione, null));
    }

    @Test
    public void puoSegnalare_segnalatoreUgualeRecensore() {
        Recensione recensione = new Recensione("bellissimo", 4);
        recensione.setId(1l);
        Recensore segnalatore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        recensione.setRecensore(segnalatore);
        segnalatore.getListaRecensioni().add(recensione);

        assertFalse(segnalazioneService.puoSegnalare(recensione, segnalatore));
    }


}

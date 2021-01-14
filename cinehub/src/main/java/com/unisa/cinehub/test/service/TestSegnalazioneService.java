package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Segnalazione;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.data.repository.SegnalazioneRepository;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.service.SegnalazioneService;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@SpringBootTest
public class TestSegnalazioneService {

    @Autowired
    private SegnalazioneService segnalazioneService;

    @MockBean
    private SegnalazioneRepository segnalazioneRepository; //save

    @MockBean
    private RecensioneRepository recensioneRepository; //save

    @MockBean
    private RecensoreRepository recensoreRepository; //save

    @Test
    public void addSegnalazione_Valid() throws NotAuthorizedException, InvalidBeanException {
        Recensione recensione = new Recensione("bellissimo", 4);
        recensione.setId(1l);
        Recensore segnalatore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        Recensore recensore = new Recensore("g.cardaropoli99@gmail.com", "Giuseppe", "Cardaropoli", LocalDate.of(1999, 12, 3), "Peppe99", new BCryptPasswordEncoder().encode("pippo"), false, true);
        recensione.setRecensore(recensore);
        recensore.getListaRecensioni().add(recensione);

        //creo gli oracoli
        Segnalazione oracoloSegnalazione = new Segnalazione();
        oracoloSegnalazione.setSegnalatoreId(segnalatore.getEmail());
        oracoloSegnalazione.setRecensione(recensione);
        oracoloSegnalazione.setRecensore(recensore);

        Mockito.when(segnalazioneRepository.save(any(Segnalazione.class))).thenAnswer(i -> i.getArgument(0, Segnalazione.class));
        Mockito.when(recensioneRepository.save(any(Recensione.class))).thenAnswer(i -> i.getArgument(0, Recensione.class));
        Mockito.when(recensoreRepository.save(any(Recensore.class))).thenAnswer(i -> i.getArgument(0, Recensore.class));

        assertEquals(oracoloSegnalazione, segnalazioneService.addSegnalazione(recensione, segnalatore));
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
    public void addSegnalazione_segnalareUgualeRecensore() {
        Recensione recensione = new Recensione("bellissimo", 4);
        recensione.setId(1l);
        Recensore segnalatore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        recensione.setRecensore(segnalatore);
        segnalatore.getListaRecensioni().add(recensione);
        assertThrows(NotAuthorizedException.class, () -> segnalazioneService.addSegnalazione(recensione, segnalatore));
    }

}

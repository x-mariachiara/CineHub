package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.entity.VerificationToken;
import com.unisa.cinehub.data.repository.UtenteRepository;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BannedException;
import com.unisa.cinehub.model.exception.UserUnderAgeException;
import com.unisa.cinehub.model.service.UtenteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class TestUtenteService {

    @Autowired
    private UtenteService utenteService;

    @MockBean
    private UtenteRepository utenteRepository;

    @MockBean
    private VerificationToken verificationToken;

    @Test
    public void signup_valid() throws UserUnderAgeException, AlreadyExsistsException, BannedException {
        Utente utente = new Recensore();
        utente.setEmail("mariachiaranasto1@gmail.com");
        utente.setNome("Maria Chiara");
        utente.setCognome("Nasto");
        utente.setDataNascita(LocalDate.of(2000, 2, 7));
        utente.setUsername("xmariachiara");
        utente.setPassword("ciao");
        when(utenteRepository.existsById(anyString())).thenReturn(false);
        when(utenteRepository.save(any(Utente.class))).thenAnswer(i -> i.getArgument(0, Utente.class));
        Utente risultato = utenteService.signup(utente);
        assertEquals(false, risultato.getActive());
        assertEquals(false, risultato.getBannato());
        assertTrue(new BCryptPasswordEncoder().matches("ciao", risultato.getPassword()));
    }

    @Test
    public void signup_menoDiTrediciAnni() {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2017, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        Assertions.assertThrows(UserUnderAgeException.class, () -> utenteService.signup(utente));
    }

    @Test
    public void signup_utenteGiaRegistrato() {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        when(utenteRepository.existsById(anyString())).thenReturn(true);
        when(utenteRepository.findById(anyString())).thenReturn(Optional.of(utente));
        Assertions.assertThrows(AlreadyExsistsException.class, () -> utenteService.signup(utente));
    }

    @Test
    public void signup_utenteBannato() {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), true, true);
        when(utenteRepository.existsById(anyString())).thenReturn(true);
        when(utenteRepository.findById(anyString())).thenReturn(Optional.of(utente));
        Assertions.assertThrows(BannedException.class, () -> utenteService.signup(utente));
    }

    @Test
    public void findByEmail_valid() {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), true, true);
        when(utenteRepository.existsById(anyString())).thenReturn(true);
        when(utenteRepository.findById(anyString())).thenReturn(Optional.of(utente));
        Assertions.assertThrows(BannedException.class, () -> utenteService.signup(utente));
    }




}

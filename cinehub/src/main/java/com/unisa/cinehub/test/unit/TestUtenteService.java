package com.unisa.cinehub.test.unit;

import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.data.entity.VerificationToken;
import com.unisa.cinehub.data.repository.UtenteRepository;
import com.unisa.cinehub.model.exception.*;
import com.unisa.cinehub.model.service.UtenteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
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
        when(utenteRepository.findById(utente.getEmail())).thenReturn(Optional.of(utente));
        when(utenteRepository.existsById(anyString())).thenReturn(true);
        try {
            utenteService.findByEmail(utente.getEmail());
            assertEquals(utente, utenteService.findByEmail("mariachiaranasto1@gmail.com"));
        } catch (InvalidBeanException e) {
            System.out.println(e.getMessage());
            assert false;
        } catch (BeanNotExsistException e) {
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    public void findByEmail_emailNull() {
        Assertions.assertThrows(InvalidBeanException.class, () -> utenteService.findByEmail(null));
    }

    @Test
    public void findByEmail_emailBlank() {
        Assertions.assertThrows(InvalidBeanException.class, () -> utenteService.findByEmail(""));
    }

    @Test
    public void findUserDetailsByEmail_valid() {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), true, true);
        when(utenteRepository.findById(anyString())).thenReturn(Optional.of(utente));
        User oracolo = new User(
                "mariachiaranasto1@gmail.com",
                new BCryptPasswordEncoder().encode("ciao").toLowerCase(),
                true,
                true,
                true,
                true,
                new ArrayList<GrantedAuthority>()
        );
        assertEquals(oracolo, utenteService.findUserDetailsByEmail(utente.getEmail()));
    }

    @Test
    public void findUserDetailsByEmail_usernameNonTrovato() {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), true, true);
        when(utenteRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> utenteService.findUserDetailsByEmail(utente.getEmail()));
    }

    @Test
    public void saveRegisteredUser_valid() throws InvalidBeanException {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        when(utenteRepository.existsById(anyString())).thenReturn(true);
        when(utenteRepository.save(any(Utente.class))).thenAnswer(i -> i.getArgument(0, Utente.class));
        assertEquals(utente, utenteService.saveRegisteredUser(utente));
    }

    @Test
    public void saveRegisteredUser_utenteNonEsistente() {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, false);
        when(utenteRepository.existsById(anyString())).thenReturn(false);
        assertThrows(InvalidBeanException.class, () -> utenteService.saveRegisteredUser(utente));
    }

    @Test
    public void saveRegisteredUser_utenteNonAttivo() {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, false);
        when(utenteRepository.existsById(anyString())).thenReturn(true);
        assertThrows(InvalidBeanException.class, () -> utenteService.saveRegisteredUser(utente));
    }


    @Test
    public void bannaRecensore_valid() throws InvalidBeanException, BeanNotExsistException {
        Utente utente = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        when(utenteRepository.findById(anyString())).thenReturn(Optional.of(utente));
        when(utenteRepository.save(any(Utente.class))).thenAnswer(i -> i.getArgument(0, Utente.class));
        assertTrue(utenteService.bannaRecensore(utente.getEmail()).getBannato());
    }

    @Test
    public void bannaRecensore_emailNull() {
        assertThrows(InvalidBeanException.class, () -> utenteService.bannaRecensore(null));
    }

    @Test
    public void bannaRecensore_userDontExists() throws InvalidBeanException, BeanNotExsistException {
        when(utenteRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(BeanNotExsistException.class, () -> utenteService.bannaRecensore("mariachiaranasto1@gmail.com"));
    }









}

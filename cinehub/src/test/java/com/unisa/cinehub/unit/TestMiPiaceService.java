package com.unisa.cinehub.unit;

import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.MiPiaceRepository;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.service.MiPiaceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestMiPiaceService {

    @Autowired
    private MiPiaceService miPiaceService;

    @MockBean
    private MiPiaceRepository miPiaceRepository;

    @MockBean
    private RecensioneRepository recensioneRepository;

    @MockBean
    private RecensoreRepository recensoreRepository;

    @Test
    public void addMipiace_valid() throws InvalidBeanException {
        Recensione recensione = new Recensione();
        recensione.setId(1L);
        recensione.setPunteggio(5);
        recensione.setContenuto("Bel film");
        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        when(miPiaceRepository.existsById(any(MiPiace.MiPiaceID.class))).thenReturn(false);
        when(miPiaceRepository.save(any(MiPiace.class))).thenAnswer(i -> i.getArgument(0, MiPiace.class));
        MiPiace oracolo = new MiPiace(true);
        oracolo.setRecensione(recensione);
        oracolo.setRecensore(recensore);

        assertEquals(oracolo, miPiaceService.handleMiPiace(true, recensione, recensore));
    }

    @Test
    public void changeMiPiace_valid() throws InvalidBeanException {
        Recensione recensione = new Recensione();
        recensione.setId(1L);
        recensione.setPunteggio(5);
        recensione.setContenuto("Bel film");
        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);

        MiPiace miPiacePrima = new MiPiace(true);
        miPiacePrima.setRecensione(recensione);
        miPiacePrima.setRecensore(recensore);

        when(miPiaceRepository.existsById(any(MiPiace.MiPiaceID.class))).thenReturn(true);
        when(miPiaceRepository.findById(any(MiPiace.MiPiaceID.class))).thenReturn(Optional.of(miPiacePrima));
        when(miPiaceRepository.save(any(MiPiace.class))).thenAnswer(i -> i.getArgument(0, MiPiace.class));


        MiPiace oracolo = new MiPiace(false);
        oracolo.setRecensione(recensione);
        oracolo.setRecensore(recensore);

        Assertions.assertEquals(oracolo, miPiaceService.handleMiPiace(false, recensione, recensore));
    }

    @Test
    public void deleteMiPiace_valid() throws InvalidBeanException {
        Recensione recensione = new Recensione();
        recensione.setId(1L);
        recensione.setPunteggio(5);
        recensione.setContenuto("Bel film");

        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);

        MiPiace oracolo = new MiPiace(true);
        oracolo.setRecensione(recensione);
        oracolo.setRecensore(recensore);
        recensione.getListaMiPiace().add(oracolo);
        recensore.getListaMiPiace().add(oracolo);

        when(recensoreRepository.findById(anyString())).thenReturn(Optional.of(recensore));
        when(recensioneRepository.findById(anyLong())).thenReturn(Optional.of(recensione));
        when(miPiaceRepository.existsById(any(MiPiace.MiPiaceID.class))).thenReturn(true);
        when(miPiaceRepository.findById(any(MiPiace.MiPiaceID.class))).thenReturn(Optional.of(oracolo));
        when(miPiaceRepository.save(any(MiPiace.class))).thenAnswer(i -> i.getArgument(0, MiPiace.class));

        Assertions.assertEquals(oracolo, miPiaceService.handleMiPiace(true, recensione, recensore));
    }

    @Test
    public void handleMiPiace_recensioneNull()  {
        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        assertThrows(InvalidBeanException.class,() -> miPiaceService.handleMiPiace(true, null, recensore));

    }

    @Test
    public void handleMiPiace_recensoreNull()  {
        Recensione recensione = new Recensione();
        recensione.setId(1L);
        recensione.setPunteggio(5);
        recensione.setContenuto("Bel film");
        assertThrows(InvalidBeanException.class,() -> miPiaceService.handleMiPiace(true, recensione, null));
    }


    @Test
    public void findMiPiaceById_valid()  {
        Recensione recensione = new Recensione();
        recensione.setId(1L);
        recensione.setPunteggio(5);
        recensione.setContenuto("Bel film");

        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);

        MiPiace oracolo = new MiPiace(true);
        oracolo.setRecensione(recensione);
        oracolo.setRecensore(recensore);

        Mockito.when(miPiaceRepository.findById(any(MiPiace.MiPiaceID.class))).thenReturn(Optional.of(oracolo));
    }

    @Test
    public void findMiPiaceById_recensoreNull()  {
        Recensione recensione = new Recensione();
        recensione.setId(1L);
        recensione.setPunteggio(5);
        recensione.setContenuto("Bel film");

        assertThrows(InvalidBeanException.class,() -> miPiaceService.findMiPiaceById(null, recensione));
    }

    @Test
    public void findMiPiaceById_recensioneNull()  {
        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        assertThrows(InvalidBeanException.class,() -> miPiaceService.findMiPiaceById(recensore, null));
    }

    @Test
    public void findMiPiaceById_beansNotExist()  {
        Recensione recensione = new Recensione();
        recensione.setId(1L);
        recensione.setPunteggio(5);
        recensione.setContenuto("Bel film");

        Recensore recensore = new Recensore("mariachiaranasto1@gmail.com", "Maria Chiara", "Nasto", LocalDate.of(2000, 2, 7), "xmariachiara", new BCryptPasswordEncoder().encode("ciao"), false, true);
        when(miPiaceRepository.findById(any(MiPiace.MiPiaceID.class))).thenReturn(Optional.empty());
        assertThrows(BeanNotExsistException.class,() -> miPiaceService.findMiPiaceById(recensore, recensione));
    }


}

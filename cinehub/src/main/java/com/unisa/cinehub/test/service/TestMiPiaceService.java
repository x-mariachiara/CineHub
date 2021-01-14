package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.repository.MiPiaceRepository;
import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.data.repository.RecensoreRepository;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.service.MiPiaceService;
import com.unisa.cinehub.model.service.RecensioneService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.Assert.*;

import java.time.LocalDate;

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
}

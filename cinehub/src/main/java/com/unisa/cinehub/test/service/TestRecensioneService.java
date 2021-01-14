package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.repository.RecensioneRepository;
import com.unisa.cinehub.model.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TestRecensioneService {

    @Autowired
    private RecensioneService recensioneService;

    @MockBean
    private RecensioneRepository recensioneRepository;

    @MockBean
    private PuntataService puntataService;

    @MockBean
    private FilmService filmService;

    @MockBean
    private UtenteService utenteService;

    @MockBean
    private SerieTVService serieTVService;

    @Test
    public void addRecensioneFilm_valid(){

    }

    @Test
    public void addRecensionePuntata_valid(){

    }

    @Test
    public void addRecensione_recensioneNUll(){

    }
    @Test
    public void removeRecensione_valid() {}

}

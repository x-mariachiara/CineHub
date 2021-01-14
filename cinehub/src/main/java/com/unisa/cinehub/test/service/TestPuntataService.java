package com.unisa.cinehub.test.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.repository.PuntataRepository;
import com.unisa.cinehub.data.repository.StagioneRepository;
import com.unisa.cinehub.model.service.PuntataService;
import com.unisa.cinehub.model.service.SerieTVService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TestPuntataService {

    @Autowired
    private PuntataService puntataService;
    @MockBean
    private StagioneRepository stagioneRepository;
    @MockBean
    private PuntataRepository puntataRepository;
    @MockBean
    private SerieTVService serieTVService;

    @Test
    public void addPuntata_isValid() {
        Puntata puntata = new Puntata("titolo puntata", 1, "sinossi puntata");
        Integer numeroStagione = 2;
        Long idSerieTv = 1l;
        SerieTv serieTv = new SerieTv("titolo serie tv", 2020, "sinossi serie tv", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(idSerieTv);


        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(serieTv);
    }
}

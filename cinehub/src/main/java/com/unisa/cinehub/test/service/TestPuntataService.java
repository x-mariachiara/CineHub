package com.unisa.cinehub.test.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.unisa.cinehub.data.repository.PuntataRepository;
import com.unisa.cinehub.data.repository.StagioneRepository;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.service.PuntataService;
import com.unisa.cinehub.model.service.SerieTVService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;

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
    public void addPuntata_isValid_stagioneEsistente() throws InvalidBeanException, AlreadyExsistsException {
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;
        Puntata puntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        Stagione stagione = new Stagione(numeroStagione);
        SerieTv serieTv = new SerieTv("titolo serie tv", 2020, "sinossi serie tv", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(idSerieTv);
        stagione.setSerieTv(serieTv);
        puntata.setStagione(stagione);

        //Oracolo Puntata
        Puntata oracoloPuntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        oracoloPuntata.setStagione(stagione);

        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(serieTv);
        Mockito.when(puntataRepository.existsById(any(Puntata.PuntataID.class))).thenReturn(false);
        Mockito.when(serieTVService.getStagione(any(SerieTv.class), anyInt())).thenReturn(java.util.Optional.of(stagione));
        Mockito.when(puntataRepository.save(any(Puntata.class))).thenAnswer(i -> i.getArgument(0, Puntata.class));

        assertEquals(oracoloPuntata, puntataService.addPuntata(puntata, numeroStagione, idSerieTv));
    }

    @Test
    public void addPuntata_isValid_nuovaStagione() throws InvalidBeanException, AlreadyExsistsException {
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;
        Puntata puntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        Stagione stagione = new Stagione(numeroStagione);
        SerieTv serieTv = new SerieTv("titolo serie tv", 2020, "sinossi serie tv", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(idSerieTv);
        stagione.setSerieTv(serieTv);
        puntata.setStagione(stagione);

        Optional<Stagione> optional = Optional.empty();

        Puntata oracoloPuntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        oracoloPuntata.setStagione(stagione);

        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(serieTv);
        Mockito.when(puntataRepository.existsById(any(Puntata.PuntataID.class))).thenReturn(false);
        Mockito.when(serieTVService.getStagione(any(SerieTv.class), anyInt())).thenReturn(optional);
        Mockito.when(puntataRepository.save(any(Puntata.class))).thenAnswer(i -> i.getArgument(0, Puntata.class));

        assertEquals(oracoloPuntata, puntataService.addPuntata(puntata, numeroStagione, idSerieTv));
    }

    @Test
    public void addPuntata_puntataGiaEsiste() {
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;
        Puntata puntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        Stagione stagione = new Stagione(numeroStagione);
        SerieTv serieTv = new SerieTv("titolo serie tv", 2020, "sinossi serie tv", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(idSerieTv);
        stagione.setSerieTv(serieTv);
        puntata.setStagione(stagione);

        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(serieTv);
        Mockito.when(puntataRepository.existsById(any(Puntata.PuntataID.class))).thenReturn(true);

        assertThrows(AlreadyExsistsException.class, () -> puntataService.addPuntata(puntata, numeroStagione, idSerieTv));
    }

    @Test
    public void addPuntata_serieTvNull() {
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;
        Puntata puntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        Stagione stagione = new Stagione(numeroStagione);
        puntata.setStagione(stagione);

        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(null);

        assertThrows(InvalidBeanException.class, () -> puntataService.addPuntata(puntata, numeroStagione, idSerieTv));
    }

    @Test
    public void addPuntata_numeroStagioneNull() {
        Integer numeroStagione = null;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;
        Puntata puntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        Stagione stagione = new Stagione(numeroStagione);
        SerieTv serieTv = new SerieTv("titolo serie tv", 2020, "sinossi serie tv", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(idSerieTv);
        stagione.setSerieTv(serieTv);
        puntata.setStagione(stagione);

        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(serieTv);

        assertThrows(InvalidBeanException.class, () -> puntataService.addPuntata(puntata, numeroStagione, idSerieTv));
    }

    @Test
    public void addPuntata_numeroStagioneNonPositivo() {
        Integer numeroStagione = -3;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;
        Puntata puntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        Stagione stagione = new Stagione(numeroStagione);
        SerieTv serieTv = new SerieTv("titolo serie tv", 2020, "sinossi serie tv", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(idSerieTv);
        stagione.setSerieTv(serieTv);
        puntata.setStagione(stagione);

        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(serieTv);

        assertThrows(InvalidBeanException.class, () -> puntataService.addPuntata(puntata, numeroStagione, idSerieTv));
    }
}

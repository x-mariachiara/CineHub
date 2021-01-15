package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.unisa.cinehub.data.repository.GenereRepository;
import com.unisa.cinehub.data.repository.SerieTVRepository;
import com.unisa.cinehub.data.repository.StagioneRepository;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.service.SerieTVService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class TestSerieTVService {

    @Autowired
    private SerieTVService serieTVService;
    @MockBean
    private SerieTVRepository serieTVRepository;
    @MockBean
    private GenereRepository genereRepository;
    @MockBean
    private StagioneRepository stagioneRepository;

    @Test
    public void addSerieTV_Valid() throws AlreadyExsistsException, InvalidBeanException {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(false);
        Mockito.when(serieTVRepository.save(any(SerieTv.class))).thenReturn(serieTv);

        //Oracolo
        SerieTv oracolo = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        oracolo.setId(1l);

        assertEquals(oracolo, serieTVService.addSerieTV(serieTv));
    }

    @Test
    public void addSerieTV_SerieTVEsistente() {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(true);

        assertThrows(AlreadyExsistsException.class, () -> serieTVService.addSerieTV(serieTv));
    }

    @Test
    public void addSerieTV_SerieTVNull() {
        assertThrows(InvalidBeanException.class, () -> serieTVService.addSerieTV(null));
    }

    @Test
    public void removeSerieTV_Valid() {
        Long id = 1l;
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);
        Optional<SerieTv> optional = Optional.of(serieTv);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(serieTVRepository.findById(id)).thenReturn(optional);
        Mockito.doNothing().when(serieTVRepository).delete(any(SerieTv.class));

        try {
            serieTVService.removeSerieTV(id);
            assert true;
        } catch (InvalidBeanException e) {
            assert false;
        } catch (BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    public void removeSerieTV_SerieTVNonEsiste() {
        Long id = 1l;
        Optional<SerieTv> optional = Optional.empty();

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(false);
        Mockito.when(serieTVRepository.findById(id)).thenReturn(optional);

        assertThrows(BeanNotExsistException.class, () -> serieTVService.removeSerieTV(id));
    }

    @Test
    public void removeSerieTV_idNull() {
        assertThrows(InvalidBeanException.class, () -> serieTVService.removeSerieTV(null));
    }

    @Test
    public void retrieveByKey_Valid() throws InvalidBeanException, BeanNotExsistException {
        Long id = 1l;
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);
        Optional<SerieTv> optional = Optional.of(serieTv);

        //Oracolo
        SerieTv oracolo = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        oracolo.setId(1l);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(serieTVRepository.findById(anyLong())).thenReturn(optional);

        assertEquals(oracolo, serieTVService.retrieveByKey(id));
    }

    @Test
    public void retrieveByKey_SerieTVNonEsiste() {
        Long id = 1l;
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(id);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(BeanNotExsistException.class, () -> serieTVService.retrieveByKey(id));
    }

    @Test
    public void retrieveByKey_idNull() {
        assertThrows(InvalidBeanException.class, () -> serieTVService.retrieveByKey(null));
    }

    @Test
    public void addGeneri_Valid() throws BeanNotExsistException {
        List<Genere> generi = new ArrayList<>();
        generi.add(new Genere(Genere.NomeGenere.AZIONE));
        generi.add(new Genere(Genere.NomeGenere.DRAMMATICI));
        SerieTv serieTv = new SerieTv("Titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");

        SerieTv oracolo = new SerieTv("Titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        oracolo.setGeneri(new HashSet<>(generi));

        Mockito.when(serieTVRepository.findById(anyLong())).thenReturn(Optional.of(serieTv));
        Mockito.when(genereRepository.existsById(any(Genere.NomeGenere.class))).thenReturn(true);
        Mockito.when(serieTVRepository.save(any(SerieTv.class))).thenAnswer(i -> i.getArgument(0, SerieTv.class));

        assertEquals(oracolo.getGeneri(), serieTVService.addGeneri(generi, 1l).getGeneri());
    }

    @Test
    public void addGeneri_SerieTVNonEsiste() {
        List<Genere> generi = new ArrayList<>();
        generi.add(new Genere(Genere.NomeGenere.AZIONE));
        generi.add(new Genere(Genere.NomeGenere.DRAMMATICI));
        Mockito.when(serieTVRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BeanNotExsistException.class, () -> serieTVService.addGeneri(generi, 2l));
    }

    @Test
    public void mergeSerieTV_Valid() throws InvalidBeanException, BeanNotExsistException {
        SerieTv serieTv = new SerieTv("Titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(serieTVRepository.save(any(SerieTv.class))).thenAnswer(i -> i.getArgument(0, SerieTv.class));

        //Oracolo
        SerieTv oracolo = new SerieTv("Titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        oracolo.setId(1l);

        assertEquals(oracolo, serieTVService.mergeSerieTV(serieTv));
    }

    @Test
    public void mergeSerieTV_SerieTvNonEsiste() {
        SerieTv serieTv = new SerieTv("Titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(BeanNotExsistException.class, () -> serieTVService.mergeSerieTV(serieTv));
    }

    @Test
    public void mergeSerieTV_SerieTvNull() {
        SerieTv serieTv = new SerieTv("Titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        assertThrows(InvalidBeanException.class, () -> serieTVService.mergeSerieTV(serieTv));
    }

    @Test
    public void searchByTitle_Valid() {
        SerieTv serieTv1 = new SerieTv("titolo 1", 2020, "sinossi 1", "https://www.pornhub.com/", "https://www.pornhub.com/");
        SerieTv serieTv2 = new SerieTv("titolo 2", 2020, "sinossi 2", "https://www.pornhub.com/", "https://www.pornhub.com/");
        SerieTv serieTv3 = new SerieTv("titolo 3", 2020, "sinossi 3", "https://www.pornhub.com/", "https://www.pornhub.com/");
        SerieTv serieTv4 = new SerieTv("dsdsdd", 2020, "sinossi 4", "https://www.pornhub.com/", "https://www.pornhub.com/");


        List<SerieTv> serieTv = new ArrayList<>();
        serieTv.add(serieTv1);
        serieTv.add(serieTv2);
        serieTv.add(serieTv3);

        //Oracolo
        List<SerieTv> oracolo = new ArrayList<>();
        oracolo.add(serieTv1);
        oracolo.add(serieTv2);
        oracolo.add(serieTv3);

        Mockito.when(serieTVRepository.findSerieTVByTitle(anyString())).thenReturn(serieTv);

        assertEquals(oracolo, serieTVService.searchByTitle("tolo"));
    }

    @Test
    public void searchByTitle_titoloBlank() {
        SerieTv serieTv1 = new SerieTv("titolo 1", 2020, "sinossi 1", "https://www.pornhub.com/", "https://www.pornhub.com/");
        SerieTv serieTv2 = new SerieTv("titolo 2", 2020, "sinossi 2", "https://www.pornhub.com/", "https://www.pornhub.com/");
        SerieTv serieTv3 = new SerieTv("titolo 3", 2020, "sinossi 3", "https://www.pornhub.com/", "https://www.pornhub.com/");
        SerieTv serieTv4 = new SerieTv("dsdsdd", 2020, "sinossi 4", "https://www.pornhub.com/", "https://www.pornhub.com/");

        List<SerieTv> serieTv = new ArrayList<>();

        //Oracolo
        List<SerieTv> oracolo = new ArrayList<>();


        Mockito.when(serieTVRepository.findSerieTVByTitle(anyString())).thenReturn(serieTv);

        assertEquals(oracolo, serieTVService.searchByTitle(""));
    }

    //TODO fare searchByGenere

    @Test
    public void addStagione_valid() throws InvalidBeanException {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);
        Stagione stagione = new Stagione(1);
        stagione.setSerieTv(serieTv);
        serieTv.getStagioni().add(stagione);

        //Oracolo
        SerieTv oracolo = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        oracolo.setId(1l);
        oracolo.getStagioni().add(stagione);

        Mockito.when(serieTVRepository.save(any(SerieTv.class))).thenReturn(serieTv);

        assertEquals(oracolo, serieTVService.addStagione(serieTv, stagione));
    }

    @Test
    public void addStagione_serieTvNull() {
        Stagione stagione = new Stagione(1);

        assertThrows(InvalidBeanException.class, () -> serieTVService.addStagione(null, stagione));
    }

    @Test
    public void addStagione_stagioneNull() {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);

        assertThrows(InvalidBeanException.class, () -> serieTVService.addStagione(serieTv, null));
    }

    @Test
    public void removeStagione_valid() throws InvalidBeanException, BeanNotExsistException {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);
        Stagione stagione = new Stagione(1);
        stagione.setSerieTv(serieTv);
        serieTv.getStagioni().add(stagione);

        //Oracolo
        SerieTv oracolo = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        oracolo.setId(1l);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(serieTVRepository.save(any(SerieTv.class))).thenAnswer(i -> i.getArgument(0, SerieTv.class));

        assertEquals(oracolo, serieTVService.removeStagione(serieTv, 1));
    }

    @Test
    public void removeStagione_serieTVNonEsiste() {

    }




}

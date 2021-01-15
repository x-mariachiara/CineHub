package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.entity.*;
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
import org.springframework.data.domain.Sort;

import java.util.*;

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
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);
        Stagione stagione = new Stagione(1);
        stagione.setSerieTv(serieTv);
        serieTv.getStagioni().add(stagione);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(BeanNotExsistException.class, () -> serieTVService.removeStagione(serieTv, 1));
    }

    @Test
    public void removeStagione_stagioneNonEsiste() {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(true);

        assertThrows(BeanNotExsistException.class, () -> serieTVService.removeStagione(serieTv, 1));
    }

    @Test
    public void removeStagione_serieTVNull() {
        assertThrows(InvalidBeanException.class, () -> serieTVService.removeStagione(null, 1));
    }

    @Test
    public void removeStagione_numeroStagioneNull() {
        assertThrows(InvalidBeanException.class, () -> serieTVService.removeStagione(new SerieTv(), null));
    }

    @Test
    public void getStagione_Valid() {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        Stagione stagione = new Stagione(1);
        serieTv.setId(1l);
        stagione.setSerieTv(serieTv);
        serieTv.getStagioni().add(stagione);

        //Oracolo
        Optional<Stagione> oracolo = Optional.of(stagione);

        assertEquals(oracolo, serieTVService.getStagione(serieTv, 1));
    }

    @Test
    public void getStagione_StagioneNonPresente() {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        Stagione stagione = new Stagione(1);
        serieTv.setId(1l);
        stagione.setSerieTv(serieTv);
        serieTv.getStagioni().add(stagione);

        //Oracolo
        Optional<Stagione> oracolo = Optional.empty();

        assertEquals(oracolo, serieTVService.getStagione(serieTv, 2));
    }

    @Test
    public void getStagione_NessunaStagione() {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(1l);

        //Oracolo
        Optional<Stagione> oracolo = Optional.empty();

        assertEquals(oracolo, serieTVService.getStagione(serieTv, 1));
    }

    @Test
    public void getStagione2_Valid() throws InvalidBeanException, BeanNotExsistException {
        SerieTv serieTv = new SerieTv("titolo", 2020, "sinossi", "https://www.pornhub.com/", "https://www.pornhub.com/");
        Stagione stagione = new Stagione(1);
        serieTv.setId(1l);
        stagione.setSerieTv(serieTv);
        serieTv.getStagioni().add(stagione);
        Optional<SerieTv> optional = Optional.of(serieTv);

        //Oracolo
        Optional<Stagione> oracolo = Optional.of(stagione);

        Mockito.when(serieTVRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(serieTVRepository.findById(anyLong())).thenReturn(optional);

        assertEquals(oracolo, serieTVService.getStagione(1l, 1));
    }

    @Test
    public void getStagione2_SerieTVNonEsiste() throws InvalidBeanException, BeanNotExsistException {
        Optional<SerieTv> oracolo = Optional.empty();

        assertEquals(oracolo, serieTVService.getStagione(1l, 1));
    }

    @Test
    public void aggiornaStagione_Valid() throws InvalidBeanException {
        Stagione stagione = new Stagione(1);
        Stagione oracolo = new Stagione(1);

        Mockito.when(stagioneRepository.save(any(Stagione.class))).thenAnswer(i -> i.getArgument(0, Stagione.class));

        assertEquals(oracolo, serieTVService.aggiornaStagione(stagione));
    }

    @Test
    public void aggiornaStagione_StagioneNull() {
        assertThrows(InvalidBeanException.class, () -> serieTVService.aggiornaStagione(null));
    }

    @Test
    public void findMostRecentSerieTV_howManyMaggiore() {
        List<SerieTv> oracolo = new ArrayList<>();
        oracolo.add(new SerieTv("titolo 1", 2020, "sinossi 1", "https://www.pornhub.com/", "https://www.pornhub.com/"));
        oracolo.add(new SerieTv("titolo 2", 2020, "sinossi 2", "https://www.pornhub.com/", "https://www.pornhub.com/"));
        oracolo.add(new SerieTv("titolo 3", 2020, "sinossi 3", "https://www.pornhub.com/", "https://www.pornhub.com/"));

        List<SerieTv> toReturnFindAll = new ArrayList<>();
        toReturnFindAll.add(new SerieTv("titolo 1", 2020, "sinossi 1", "https://www.pornhub.com/", "https://www.pornhub.com/"));
        toReturnFindAll.add(new SerieTv("titolo 2", 2020, "sinossi 2", "https://www.pornhub.com/", "https://www.pornhub.com/"));
        toReturnFindAll.add(new SerieTv("titolo 3", 2020, "sinossi 3", "https://www.pornhub.com/", "https://www.pornhub.com/"));

        for(int i = 1; i <= 3; i++) {
            toReturnFindAll.get(i-1).setId(Integer.toUnsignedLong(i));
            oracolo.get(i-1).setId(Integer.toUnsignedLong(i));
        }

        Mockito.when(serieTVRepository.findAll(any(Sort.class))).thenReturn(toReturnFindAll);

        assertEquals(oracolo, serieTVService.findMostRecentSerieTv(10));
    }

    @Test
    public void findMostRecentSerieTV_howManyMinore() {
        SerieTv serieTv1 = new SerieTv("titolo 1", 2020, "sinossi 1", "https://www.pornhub.com/", "https://www.pornhub.com/");
        SerieTv serieTv2 = new SerieTv("titolo 2", 2020, "sinossi 2", "https://www.pornhub.com/", "https://www.pornhub.com/");
        SerieTv serieTv3 = new SerieTv("titolo 3", 2020, "sinossi 3", "https://www.pornhub.com/", "https://www.pornhub.com/");

        List<SerieTv> oracolo = new ArrayList<>();
        oracolo.add(serieTv3);
        oracolo.add(serieTv2);

        List<SerieTv> toReturnFindAll = new ArrayList<>();
        toReturnFindAll.add(serieTv3);
        toReturnFindAll.add(serieTv2);
        toReturnFindAll.add(serieTv1);

        Mockito.when(serieTVRepository.findAll(any(Sort.class))).thenReturn(toReturnFindAll);

        assertEquals(oracolo, serieTVService.findMostRecentSerieTv(2));
    }

    @Test
    public void searchByGenere_Valid() throws InvalidBeanException {
        Collection<Genere> generi = new ArrayList<>();
        Set<Media> media = new HashSet<>();
        Genere animazione = new Genere(Genere.NomeGenere.ANIMAZIONE);
        Genere anime = new Genere(Genere.NomeGenere.ANIME);
        Genere avventura = new Genere(Genere.NomeGenere.AVVENTURA);
        SerieTv serieTv1 = new SerieTv("titolo serietv 1", 2020, "sinossi serietv 1", "https://www.pornhub.com/", "https://www.pornhub.com/");
        SerieTv serieTv2 = new SerieTv("titolo serietv 2", 2020, "sinossi serietv 2", "https://www.pornhub.com/", "https://www.pornhub.com/");
        Film film = new Film("titolo film", 2020, "sinossi film", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv1.setId(1l);
        serieTv2.setId(2l);
        film.setId(3l);
        media.add(serieTv1);
        media.add(serieTv2);
        media.add(film);
        animazione.setMediaCollegati(media);
        anime.setMediaCollegati(media);
        avventura.setMediaCollegati(media);
        generi.add(animazione);
        generi.add(anime);
        generi.add(avventura);

        //Oracolo
        Collection<SerieTv> oracolo = new HashSet<>();
        oracolo.add(serieTv1);
        oracolo.add(serieTv2);

        Mockito.when(genereRepository.findById(Genere.NomeGenere.ANIMAZIONE)).thenReturn(Optional.of(animazione));
        Mockito.when(genereRepository.findById(Genere.NomeGenere.ANIME)).thenReturn(Optional.of(anime));
        Mockito.when(genereRepository.findById(Genere.NomeGenere.AVVENTURA)).thenReturn(Optional.of(avventura));

        assertEquals(oracolo, serieTVService.searchByGenere(generi));
    }

    @Test
    public void searchByGenere_mediaNonCollegati() throws InvalidBeanException {
        Collection<Genere> generi = new ArrayList<>();
        Genere animazione = new Genere(Genere.NomeGenere.ANIMAZIONE);
        Genere anime = new Genere(Genere.NomeGenere.ANIME);
        Genere avventura = new Genere(Genere.NomeGenere.AVVENTURA);
        generi.add(animazione);
        generi.add(anime);
        generi.add(avventura);

        //Oracolo
        Collection<SerieTv> oracolo = new HashSet<>();

        Mockito.when(genereRepository.findById(Genere.NomeGenere.ANIMAZIONE)).thenReturn(Optional.of(animazione));
        Mockito.when(genereRepository.findById(Genere.NomeGenere.ANIME)).thenReturn(Optional.of(anime));
        Mockito.when(genereRepository.findById(Genere.NomeGenere.AVVENTURA)).thenReturn(Optional.of(avventura));

        assertEquals(oracolo, serieTVService.searchByGenere(generi));
    }

    @Test
    public void searchByGenere_nessunGenere() throws InvalidBeanException {
        Collection<Genere> generi = new ArrayList<>();

        //oracolo
        Collection<SerieTv> oracolo = new HashSet<>();

        assertEquals(oracolo, serieTVService.searchByGenere(generi));
    }

    @Test
    public void searchByGenere_generiNull() {
        assertThrows(InvalidBeanException.class, () -> serieTVService.searchByGenere(null));
    }
}

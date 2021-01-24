package com.unisa.cinehub.unit;

import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.unisa.cinehub.model.media.serietv.PuntataRepository;
import com.unisa.cinehub.model.media.serietv.StagioneRepository;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.media.serietv.PuntataService;
import com.unisa.cinehub.model.media.serietv.SerieTVService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

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
    public void addPuntata_isValid_stagioneEsistente() throws InvalidBeanException, AlreadyExsistsException, BeanNotExsistException {
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
        Mockito.when(serieTVService.getStagione(any(SerieTv.class), anyInt())).thenReturn(stagione);
        Mockito.when(puntataRepository.save(any(Puntata.class))).thenAnswer(i -> i.getArgument(0, Puntata.class));

        assertEquals(oracoloPuntata, puntataService.addPuntata(puntata, numeroStagione, idSerieTv));
    }

    //TODO qua deve lanciare un eccezione
    @Test
    public void addPuntata_isValid_nuovaStagione() throws InvalidBeanException, AlreadyExsistsException, BeanNotExsistException {
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
        Mockito.when(serieTVService.getStagione(any(SerieTv.class), anyInt())).thenThrow(BeanNotExsistException.class);
        Mockito.when(puntataRepository.save(any(Puntata.class))).thenAnswer(i -> i.getArgument(0, Puntata.class));

        assertEquals(oracoloPuntata, puntataService.addPuntata(puntata, numeroStagione, idSerieTv));
    }

    @Test
    public void addPuntata_puntataGiaEsiste() throws InvalidBeanException, BeanNotExsistException {
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
    public void addPuntata_puntataNonValida() throws InvalidBeanException, BeanNotExsistException {
        Integer numeroStagione = 1;
        Long idSerieTv = 1l;
        Puntata puntata = new Puntata("   " , -1 , "");

        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(new SerieTv());

        assertThrows(InvalidBeanException.class, () -> puntataService.addPuntata(puntata, numeroStagione, idSerieTv));
    }

    @Test
    public void addPuntata_numeroStagioneNull() throws InvalidBeanException, BeanNotExsistException {
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
    public void addPuntata_numeroStagioneNonPositivo() throws InvalidBeanException, BeanNotExsistException {
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

    @Test
    public void removePuntata_isValid() throws InvalidBeanException, BeanNotExsistException {
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;
        Puntata puntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        Stagione stagione = new Stagione(numeroStagione);
        SerieTv serieTv = new SerieTv("titolo serie tv", 2020, "sinossi serie tv", "https://www.pornhub.com/", "https://www.pornhub.com/");
        serieTv.setId(idSerieTv);
        stagione.setSerieTv(serieTv);
        puntata.setStagione(stagione);

        Optional<Puntata> optionalPuntata = Optional.of(puntata);


        Stagione.StagioneID stagioneID = new Stagione.StagioneID(numeroStagione, idSerieTv);
        Puntata.PuntataID puntataID = new Puntata.PuntataID(numeroPuntata, stagioneID);

        Mockito.when(puntataRepository.existsById(any(Puntata.PuntataID.class))).thenReturn(true);
        Mockito.when(serieTVService.getStagione(anyLong(),anyInt())).thenReturn(stagione);
        Mockito.when(puntataRepository.findById(any(Puntata.PuntataID.class))).thenReturn(optionalPuntata);
        Mockito.when(puntataRepository.save(any(Puntata.class))).thenAnswer(i -> i.getArgument(0, Puntata.class));
        Mockito.doNothing().when(puntataRepository).deleteById(puntataID);

        try {
            puntataService.removePuntata(puntataID);
            assert true;
        } catch (BeanNotExsistException | InvalidBeanException e) {
            assert false;
        }
    }

    @Test
    public void removePuntata_puntataNonEsiste() {
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;

        Stagione.StagioneID stagioneID = new Stagione.StagioneID(numeroStagione, idSerieTv);
        Puntata.PuntataID puntataID = new Puntata.PuntataID(numeroPuntata, stagioneID);

        Mockito.when(puntataRepository.existsById(any(Puntata.PuntataID.class))).thenReturn(false);

        assertThrows(BeanNotExsistException.class, () -> puntataService.removePuntata(puntataID));
    }

    @Test
    public void removePuntata_idNull() {
        assertThrows(InvalidBeanException.class, () -> puntataService.removePuntata(null));
    }

    @Test
    public void retrieveBySerieTV_Valid() throws InvalidBeanException, BeanNotExsistException {
        Long idSerieTv = 1l;
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Puntata puntata1 = new Puntata("titolo puntata 1", numeroPuntata, "sinossi puntata 1");
        Puntata puntata2 = new Puntata("titolo puntata 1", numeroPuntata + 1, "sinossi puntata 1");
        Puntata puntata3 = new Puntata("titolo puntata 1", numeroPuntata + 2, "sinossi puntata 1");
        Stagione stagione = new Stagione(numeroStagione);
        SerieTv serieTv = new SerieTv("titolo serie tv", 2020, "sinossi serie tv", "https://www.pornhub.com/", "https://www.pornhub.com/");

        serieTv.setId(idSerieTv);
        stagione.setSerieTv(serieTv);
        puntata1.setStagione(stagione);
        puntata2.setStagione(stagione);
        puntata3.setStagione(stagione);

        stagione.getPuntate().add(puntata1);
        stagione.getPuntate().add(puntata2);
        stagione.getPuntate().add(puntata3);
        serieTv.getStagioni().add(stagione);

        //Oracolo
        List<Puntata> oracolo = new ArrayList<>();
        oracolo.add(puntata1);
        oracolo.add(puntata2);
        oracolo.add(puntata3);

        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(serieTv);

        assertEquals(oracolo, puntataService.retrieveBySerieTV(idSerieTv));

    }

    @Test
    public void retrieveBySerieTV_idSerieTvNull() throws InvalidBeanException, BeanNotExsistException {
        assertThrows(InvalidBeanException.class, () -> puntataService.retrieveBySerieTV(null));
    }

    @Test
    public void retrieveByStagione_Valid() throws InvalidBeanException, BeanNotExsistException {
        Long idSerieTv = 1l;
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Puntata puntata1 = new Puntata("titolo puntata 1", numeroPuntata, "sinossi puntata 1");
        Puntata puntata2 = new Puntata("titolo puntata 1", numeroPuntata + 1, "sinossi puntata 1");
        Puntata puntata3 = new Puntata("titolo puntata 1", numeroPuntata + 2, "sinossi puntata 1");
        Stagione stagione = new Stagione(numeroStagione);
        SerieTv serieTv = new SerieTv("titolo serie tv", 2020, "sinossi serie tv", "https://www.pornhub.com/", "https://www.pornhub.com/");

        serieTv.setId(idSerieTv);
        stagione.setSerieTv(serieTv);
        puntata1.setStagione(stagione);
        puntata2.setStagione(stagione);
        puntata3.setStagione(stagione);

        stagione.getPuntate().add(puntata1);
        stagione.getPuntate().add(puntata2);
        stagione.getPuntate().add(puntata3);
        serieTv.getStagioni().add(stagione);

        Optional<Stagione> optional = Optional.of(stagione);

        //Oracolo
        List<Puntata> oracolo = new ArrayList<>();
        oracolo.add(puntata1);
        oracolo.add(puntata2);
        oracolo.add(puntata3);

        Mockito.when(serieTVService.retrieveByKey(anyLong())).thenReturn(serieTv);
        Mockito.when(serieTVService.getStagione(any(SerieTv.class), anyInt())).thenReturn(stagione);

        assertEquals(oracolo, puntataService.retrieveByStagione(idSerieTv, numeroStagione));
    }

    @Test
    public void retrieveByStagione_idSerieTvNull() throws InvalidBeanException, BeanNotExsistException {
        assertThrows(InvalidBeanException.class, () -> puntataService.retrieveByStagione(null, 1));
    }

    @Test
    public void retrieveByStagione_numeroStagioneNonPositivo() throws InvalidBeanException, BeanNotExsistException {
        assertThrows(InvalidBeanException.class, () -> puntataService.retrieveByStagione(1l, -2));
    }

    @Test
    public void retrievePuntataByKey_Valid() throws BeanNotExsistException, InvalidBeanException {
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;
        Stagione.StagioneID stagioneID = new Stagione.StagioneID(numeroStagione, idSerieTv);
        Puntata.PuntataID puntataID = new Puntata.PuntataID(numeroPuntata, stagioneID);
        Puntata puntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        Stagione stagione = new Stagione(numeroStagione);
        puntata.setStagione(stagione);

        Optional<Puntata> optional = Optional.of(puntata);

        //Oracolo
        Puntata oracolo = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        oracolo.setStagione(stagione);

        Mockito.when(puntataRepository.findById(any(Puntata.PuntataID.class))).thenReturn(optional);

        assertEquals(oracolo, puntataService.retrievePuntataByKey(puntataID));

    }

    @Test
    public void retrievePuntataByKey_PuntataNonTrovata() {
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Long idSerieTv = 1l;
        Stagione.StagioneID stagioneID = new Stagione.StagioneID(numeroStagione, idSerieTv);
        Puntata.PuntataID puntataID = new Puntata.PuntataID(numeroPuntata, stagioneID);
        Optional<Puntata> optional = Optional.empty();

        Mockito.when(puntataRepository.findById(any(Puntata.PuntataID.class))).thenReturn(optional);

        assertThrows(BeanNotExsistException.class, () -> puntataService.retrievePuntataByKey(puntataID));
    }

    @Test
    public void retrievePuntataByKey_PuntataIDNull() {
        assertThrows(InvalidBeanException.class, () -> puntataService.retrievePuntataByKey(null));
    }

    @Test
    public void mergePuntata_isValid() throws InvalidBeanException, BeanNotExsistException {
        Integer numeroStagione = 1;
        Integer numeroPuntata = 1;
        Puntata puntata = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        Stagione stagione = new Stagione(numeroStagione);
        puntata.setStagione(stagione);

        Mockito.when(puntataRepository.existsById(any(Puntata.PuntataID.class))).thenReturn(true);
        Mockito.when(puntataRepository.save(any(Puntata.class))).thenAnswer(i -> i.getArgument(0, Puntata.class));

        //oracolo
        Puntata oracolo = new Puntata("titolo puntata", numeroPuntata, "sinossi puntata");
        oracolo.setStagione(stagione);

        assertEquals(oracolo, puntataService.mergePuntata(puntata));
    }

    @Test
    public void mergePuntata_PuntataInvalida() throws InvalidBeanException {
        assertThrows(InvalidBeanException.class, () -> puntataService.mergePuntata(null));
    }

    @Test
    public void mergePuntata_PuntataNonEsiste() {
        Puntata puntata = new Puntata("titolo", 1, "sinossi");
        Mockito.when(puntataRepository.existsById(any(Puntata.PuntataID.class))).thenReturn(false);
        assertThrows(BeanNotExsistException.class, () -> puntataService.mergePuntata(puntata));
    }

    @Test
    public void searchByTittle_Valid() {
        Puntata puntata1 = new Puntata("titolo puntata 1", 1, "sinossi puntata 1");
        Puntata puntata2 = new Puntata("titolo puntata 2",  2, "sinossi puntata 2");
        Puntata puntata3 = new Puntata("titolo puntata 3", 3, "sinossi puntata 3");
        Puntata puntata4 = new Puntata("seweaf4ws", 4, "sinossi puntata 4");

        List<Puntata> puntate = new ArrayList<>();
        puntate.add(puntata1);
        puntate.add(puntata2);
        puntate.add(puntata3);

        //Oracolo
        List<Puntata> oracolo = new ArrayList<>();
        oracolo.add(puntata1);
        oracolo.add(puntata2);
        oracolo.add(puntata3);

        Mockito.when(puntataRepository.findPuntataByTitle(anyString())).thenReturn(puntate);

        assertEquals(oracolo, puntataService.searchByTitle("tolo"));
    }

    @Test
    public void searchByTittle_TitoloBlank() {
        //oracolo
        List<Puntata> oracolo = new ArrayList<>();

        Mockito.when(puntataRepository.findPuntataByTitle(anyString())).thenReturn(new ArrayList<>());

        assertEquals(oracolo, puntataService.searchByTitle(""));
    }


}

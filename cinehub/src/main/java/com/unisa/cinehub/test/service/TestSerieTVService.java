package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.entity.SerieTv;
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


}

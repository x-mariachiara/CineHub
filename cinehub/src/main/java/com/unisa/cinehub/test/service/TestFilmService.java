package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.repository.FilmRepository;
import com.unisa.cinehub.data.repository.GenereRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.service.FilmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class TestFilmService {

    @Autowired
    private FilmService filmService;

    @MockBean
    FilmRepository filmRepository;

    @MockBean
    GenereRepository genereRepository;

    @Test
    public void removeFilm_valid(){
        Mockito.when(filmRepository.existsById(anyLong())).thenReturn(true);
        Mockito.doNothing().when(filmRepository).delete(any(Film.class));
        try{
            filmService.removeFilm(1l);
            assert true;
        } catch (BeanNotExsistException b){
            assert false;
        }
    }

    @Test
    public void removeFilm_idInvalid(){
        Mockito.when(filmRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(BeanNotExsistException.class, () -> filmService.removeFilm(1l));
    }

    @Test
    public void removeFilm_idNull() {
        assertThrows(BeanNotExsistException.class, () -> filmService.removeFilm(null));
    }

    @Test
    public void addGeneri_valid() throws BeanNotExsistException {
        List<Genere> generi = new ArrayList<>();
        generi.add(new Genere(Genere.NomeGenere.AZIONE));
        generi.add(new Genere(Genere.NomeGenere.DRAMMATICI));
        Film film = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
        Mockito.when(filmRepository.findById(anyLong())).thenReturn(Optional.of(film));
        Mockito.when(genereRepository.existsById(any(Genere.NomeGenere.class))).thenReturn(true);
        Film oracolo = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
        oracolo.setGeneri(new HashSet<>(generi));
        Mockito.when(filmRepository.save(any(Film.class))).thenAnswer(i -> i.getArgument(0, Film.class));
        assertEquals(oracolo.getGeneri(), filmService.addGeneri(generi, 1l).getGeneri());
    }

    @Test
    public void addGeneri_filmNonCe() throws BeanNotExsistException {
        List<Genere> generi = new ArrayList<>();
        generi.add(new Genere(Genere.NomeGenere.AZIONE));
        generi.add(new Genere(Genere.NomeGenere.DRAMMATICI));
        Mockito.when(filmRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BeanNotExsistException.class, () -> filmService.addGeneri(generi, 2l));
    }
}

package com.unisa.cinehub.unit;

import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.entity.Ruolo;
import com.unisa.cinehub.model.media.CastRepository;
import com.unisa.cinehub.model.media.RuoloRepository;
import com.unisa.cinehub.model.media.RuoloService;
import com.unisa.cinehub.model.media.film.FilmRepository;
import com.unisa.cinehub.model.media.GenereRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.media.film.FilmService;
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

    @MockBean
    CastRepository castRepository;

    @MockBean
    RuoloRepository ruoloRepository;

    @MockBean
    RuoloService ruoloService;

    @Test
    public void removeFilm_valid(){
        Film film = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
        film.setId(1L);
        Mockito.when(filmRepository.existsById(anyLong())).thenReturn(true);
        Mockito.doNothing().when(filmRepository).delete(any(Film.class));
        Mockito.when(filmRepository.findById(anyLong())).thenReturn(Optional.of(film));
        try{
            filmService.removeFilm(1l);
            assert true;
        } catch (BeanNotExsistException | InvalidBeanException b){
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
    public void addGeneri_valid() throws BeanNotExsistException, InvalidBeanException {
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

    @Test
    public void addCast_valid() throws InvalidBeanException, BeanNotExsistException {
        List<Ruolo> ruoli = new ArrayList<>();
        Ruolo r1 = new Ruolo(Ruolo.Tipo.ATTORE);
        Ruolo r2 = new Ruolo(Ruolo.Tipo.REGISTA);
        Cast cast = new Cast("pippo", "franco");
        Film film = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
        cast.setId(1l);
        film.setId(2l);
        r1.setCast(cast);
        r2.setCast(cast);
        r1.setMedia(film);
        r2.setMedia(film);
        cast.getRuoli().addAll(Arrays.asList(r1, r2));
        film.getRuoli().addAll(Arrays.asList(r1, r2));

        Mockito.when(filmRepository.findById(anyLong())).thenReturn(Optional.of(film));
        Mockito.doNothing().when(ruoloService).cleanRuolo(anyLong());
        Mockito.when(ruoloRepository.save(any(Ruolo.class))).thenAnswer(i -> i.getArgument(0, Ruolo.class));
        Mockito.when(castRepository.save(any(Cast.class))).thenAnswer(i -> i.getArgument(0, Cast.class));
        Mockito.when(filmRepository.save(any(Film.class))).thenAnswer(i -> i.getArgument(0, Film.class));

        Film oracolo = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
        oracolo.setId(2l);
        oracolo.setRuoli(new ArrayList<>(ruoli));
        assertEquals(oracolo.getRuoli(), (filmService.addCast(ruoli, film.getId())).getRuoli());
    }
}

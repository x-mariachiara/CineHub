package com.unisa.cinehub.test.service;

import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Ruolo;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.repository.CastRepository;
import com.unisa.cinehub.data.repository.FilmRepository;
import com.unisa.cinehub.data.repository.RuoloRepository;
import com.unisa.cinehub.data.repository.SerieTVRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.service.RuoloService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.Assert.*;
import java.util.Optional;

@SpringBootTest
public class TestRuoloService {

    @Autowired
    private RuoloService ruoloService;

    @MockBean
    private RuoloRepository ruoloRepository;

    @MockBean
    private CastRepository castRepository;

    @MockBean
    private FilmRepository filmRepository;

    @MockBean
    private SerieTVRepository serieTVRepository;

    @Test
    public void addRuolo_validFilm() throws BeanNotExsistException, InvalidBeanException {
        Cast cast = new Cast("Kevin", "Spacey");
        cast.setId(1L);
        Film film = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
        film.setId(1L);
        Mockito.when(filmRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(film));
        Mockito.when(castRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cast));
        Mockito.when(ruoloRepository.save(Mockito.any(Ruolo.class))).thenAnswer(i -> i.getArgument(0, Ruolo.class));
        Ruolo ruolo = new Ruolo(Ruolo.Tipo.ATTORE);
        ruolo.setMedia(film);
        ruolo.setCast(cast);
        assertEquals(ruolo, ruoloService.addRuolo(new Ruolo(Ruolo.Tipo.ATTORE), 1L, 1L));

    }

    @Test
    public void addRuolo_validSerieTv() throws BeanNotExsistException, InvalidBeanException {
        Cast cast = new Cast("Kevin", "Spacey");
        cast.setId(1L);
        SerieTv serieTv = new SerieTv("La Casa di Carta",
                    2017,
                    "La storia narra gli sviluppi di una rapina estremamente ambiziosa e originale: irrompere nella Fábrica Nacional de Moneda y Timbre, a Madrid, far stampare migliaia di milioni di banconote e scappare con il bottino. L'ideatore di questa impresa è un uomo conosciuto come \"il Professore\". Il reclutamento di ogni singolo membro della squadra non è affatto casuale: il Professore, infatti, seleziona attentamente un gruppo di individui con precedenti penali, i quali, per motivi di estrazione sociale, non hanno nulla da perdere. Ciascun membro durante la rapina agisce vestito di rosso con una maschera del pittore spagnolo Salvador Dalí.",
                    "https://www.youtube.com/embed/bNWnxJBFlDQ",
                    "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi2.wp.com%2Ftv.badtaste.it%2Fwp%2Fwp-content%2Fuploads-badtv%2F2019%2F01%2Fcasa-di-carta.jpg%3Ffit%3D300%252C450%26quality%3D85%26strip%3Dall%26ssl%3D1&f=1&nofb=1");
        serieTv.setId(1L);
        Mockito.when(serieTVRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(serieTv));
        Mockito.when(castRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cast));
        Mockito.when(ruoloRepository.save(Mockito.any(Ruolo.class))).thenAnswer(i -> i.getArgument(0, Ruolo.class));
        Ruolo ruolo = new Ruolo(Ruolo.Tipo.ATTORE);
        ruolo.setMedia(serieTv);
        ruolo.setCast(cast);
        assertEquals(ruolo, ruoloService.addRuolo(new Ruolo(Ruolo.Tipo.ATTORE), 1L, 1L));

    }

    @Test
    public void addRuolo_ruoloNull()  {
        assertThrows(InvalidBeanException.class, () -> ruoloService.addRuolo(null, 1L, 1L));
    }

    @Test
    public void addRuolo_castIdNull() {
        assertThrows(InvalidBeanException.class, () -> ruoloService.addRuolo(new Ruolo(Ruolo.Tipo.ATTORE), null, 1L));
    }

    @Test
    public void addRuolo_mediaIdNull() {
        assertThrows(InvalidBeanException.class, () -> ruoloService.addRuolo(new Ruolo(Ruolo.Tipo.ATTORE), null, 1L));
    }

    @Test
    public void addRuolo_tipoRuoloIsNull() {
        assertThrows(InvalidBeanException.class, () -> ruoloService.addRuolo(new Ruolo(), 1L, 1L));
    }

    @Test
    public void addRuolo_mediaNotExists() {
        Cast cast = new Cast("Kevin", "Spacey");
        cast.setId(1L);
        Mockito.when(filmRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(castRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cast));
        assertThrows(BeanNotExsistException.class, () -> ruoloService.addRuolo(new Ruolo(Ruolo.Tipo.ATTORE), 1L, 1L));
    }

    @Test
    public void addRuolo_castNotExists() {
        Film film = new Film("Baby Driver", 2017, "Un giovane pilota è costretto a lavorare per un boss del crimine e deve usare tutta la propria abilità quando una rapina, destinata a fallire, minaccia la sua vita e la sua libertà.", "https://www.youtube.com/embed/oFiLrgCuFXo", "https://pad.mymovies.it/filmclub/2015/09/049/locandina.jpg");
        film.setId(1L);
        Mockito.when(filmRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(film));
        Mockito.when(castRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(BeanNotExsistException.class, () -> ruoloService.addRuolo(new Ruolo(Ruolo.Tipo.ATTORE), 1L, 1L));
    }



}

package com.unisa.cinehub.test.unit;


import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Recensore;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;


public class TestFilm {
    //Classe da testare
    private Film film;

    @Before
    public void init() {
        film = new Film();
        Recensione recensione1 = new Recensione("", 5);
        Recensione recensione2 = new Recensione("", 3);;
        film.aggiungiRecensione(recensione1);
        film.aggiungiRecensione(recensione2);
    }

    @Test
    public void calcoloMedia_ConRecensioni() {
        assertEquals(new Double(4.0), film.getMediaVoti());
    }


}

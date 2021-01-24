package com.unisa.cinehub.unit;


import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Recensione;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;


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

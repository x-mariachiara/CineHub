package com.unisa.cinehub.test.entity;

import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.Recensione;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPuntata {

    private Puntata puntata;

    @Before
    public void init() {
        puntata = new Puntata();
        Recensione recensione1 = new Recensione("", 5);
        Recensione recensione2 = new Recensione("", 3);
        puntata.aggiungiRecensione(recensione1);
        puntata.aggiungiRecensione(recensione2);
    }

    @Test
    public void calcolaMediaVoti() {
        assertEquals(new Double(4), puntata.getMediaVoti());
    }

}

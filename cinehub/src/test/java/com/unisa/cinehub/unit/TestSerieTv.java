package com.unisa.cinehub.unit;

import com.unisa.cinehub.data.entity.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;

public class TestSerieTv {

    private SerieTv serieTv;

    @Before
    public void init() {
        serieTv = new SerieTv("titolo", 2021, "sinossi", "https://www.google.it/", "https://www.google.it/");
        Recensione recensione1 = new Recensione("bello", 5);
        Recensione recensione2 = new Recensione("pessimo", 1);
        Recensione recensione3 = new Recensione("nella norma", 3);
        Puntata puntata1 = new Puntata("titolo puntata 1", 1, "sinossi puntata 1");
        puntata1.aggiungiRecensione(recensione1);
        puntata1.aggiungiRecensione(recensione2);
        Puntata puntata2 = new Puntata("titolo puntata 2", 2, "sinossi puntata 2");
        puntata2.aggiungiRecensione(recensione3);
        Stagione stagione = new Stagione(1);
        stagione.setPuntate(Arrays.asList(puntata1, puntata2));
        serieTv.setStagioni(Arrays.asList(stagione));

    }

    @Test
    public void calcolaMediaVoti() {
        Double oracolo = 3.0;
        serieTv.calcolaMediaVoti();
        assertEquals(oracolo, serieTv.getMediaVoti());
    }


}

package com.unisa.cinehub.data.entity;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Film extends Media implements Recensibile{

    private Double mediaVoti;
    private List<Recensione> listaRecensioni;

    public Film() {
        listaRecensioni = new ArrayList<Recensione>();
    }

    public Film(String titolo, Integer annoUscita, String sinossi, String linkTrailer, String linkLocandina) {
        super(titolo, annoUscita, sinossi, linkTrailer, linkLocandina);
        listaRecensioni = new ArrayList<Recensione>();
    }


    @Override
    public Double getMediaVoti() {
        return mediaVoti;
    }

    @Override
    public void calcolaMediaVoti() {

    }

    @Override
    public void aggiungiRecensione(Recensione recensione) {

    }

    @Override
    public void rimuoviRecensione(Recensione recensione) {

    }

    @Override
    public List<Recensione> getListaRecensioni() {
        return listaRecensioni;
    }
}

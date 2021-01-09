package com.unisa.cinehub.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Film extends Media implements Recensibile{

    private Double mediaVoti;
    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    @JsonIgnore
    private List<Recensione> listaRecensioni;

    public Film() {
        listaRecensioni = new ArrayList<Recensione>();
        this.mediaVoti = 0.0;
    }

    public Film(String titolo, Integer annoUscita, String sinossi, String linkTrailer, String linkLocandina) {
        super(titolo, annoUscita, sinossi, linkTrailer, linkLocandina);
        listaRecensioni = new ArrayList<Recensione>();
        this.mediaVoti = 0.0;
    }



    public Double getMediaVoti() {
        return mediaVoti;
    }

    public void setMediaVoti(Double mediaVoti) {
        this.mediaVoti = mediaVoti;
    }

    public void setListaRecensioni(List<Recensione> listaRecensioni) {
        this.listaRecensioni = listaRecensioni;
    }

    private void calcolaMediaVoti() {
        Double totalizer = 0.0;
        for(Recensione r : listaRecensioni) {
            totalizer += r.getPunteggio();
        }
        this.setMediaVoti((totalizer / listaRecensioni.size()));
    }

    @Override
    public void aggiungiRecensione(Recensione recensione) {
        this.listaRecensioni.add(recensione);
        calcolaMediaVoti();
    }

    @Override
    public void rimuoviRecensione(Recensione recensione) {
        this.listaRecensioni.remove(recensione);
        calcolaMediaVoti();
    }

    @Override
    public List<Recensione> getListaRecensioni() {
        return listaRecensioni;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "mediaVoti=" + mediaVoti +
                //", listaRecensioni=" + listaRecensioni +
                '}';
    }
}

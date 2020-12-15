package com.unisa.cinehub.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Film extends Media implements Recensibile{

    private Double mediaVoti;
    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
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

    public void setMediaVoti(Double mediaVoti) {
        this.mediaVoti = mediaVoti;
    }

    public void setListaRecensioni(List<Recensione> listaRecensioni) {
        this.listaRecensioni = listaRecensioni;
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

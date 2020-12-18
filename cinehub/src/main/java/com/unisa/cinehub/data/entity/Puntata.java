package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Puntata extends AbstractEntity implements Recensibile, Cloneable{

    private String titolo;
    private Integer numeroPuntata;
    private String sinossi;

    public Puntata() {
    }

    public Puntata(String titolo, Integer numeroPuntata, String sinossi) {
        this.titolo = titolo;
        this.numeroPuntata = numeroPuntata;
        this.sinossi = sinossi;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Integer getNumeroPuntata() {
        return numeroPuntata;
    }

    public void setNumeroPuntata(Integer numeroPuntata) {
        this.numeroPuntata = numeroPuntata;
    }

    public String getSinossi() {
        return sinossi;
    }

    public void setSinossi(String sinossi) {
        this.sinossi = sinossi;
    }

    @Override
    public String toString() {
        return "Puntata{" +
                "titolo='" + titolo + '\'' +
                ", numeroPuntata=" + numeroPuntata +
                ", sinossi='" + sinossi + '\'' +
                '}';
    }

    @Override
    public Double getMediaVoti() {
        return null;
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
        return null;
    }
}

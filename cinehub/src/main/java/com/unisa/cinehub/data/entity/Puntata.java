package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Puntata extends AbstractEntity {

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
}

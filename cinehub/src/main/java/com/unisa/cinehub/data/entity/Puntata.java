package com.unisa.cinehub.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(Puntata.PuntataID.class)
public class Puntata implements Recensibile, Cloneable{

    private String titolo;
    @Id
    private Integer numeroPuntata;

    private String sinossi;

    @Id
    private Stagione.StagioneID stagioneId;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JsonIgnore
    private Stagione stagione;

    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    @JsonIgnore
    private List<Recensione> listaRecensioni;

    private Double mediaVoti;

    public Puntata() {
    }

    public Puntata(String titolo, Integer numeroPuntata, String sinossi) {
        this.titolo = titolo;
        this.numeroPuntata = numeroPuntata;
        this.sinossi = sinossi;
    }

    public Stagione.StagioneID getStagioneID() {
        return stagioneId;
    }

    public void setStagioneID(Stagione.StagioneID stagioneID) {
        this.stagioneId = stagioneID;
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

    public Stagione getStagione() {
        return stagione;
    }

    public void setStagione(Stagione stagione) {
        this.stagione = stagione;
        this.stagioneId = new Stagione.StagioneID(stagione.getNumeroStagione(), stagione.getSerieTvId());
    }

    @Override
    public String toString() {
        return "Puntata{" +
                "titolo='" + titolo + '\'' +
                ", numeroPuntata=" + numeroPuntata +
                ", sinossi='" + sinossi + '\'' +
                ", stagioneId=" + stagioneId +
                ", mediaVoti=" + mediaVoti +
                '}';
    }

    @Override
    public Double getMediaVoti() {
        return this.mediaVoti;
    }


    private void calcolaMediaVoti() {
        Double totalizer = 0.0;
        for (Recensione r : listaRecensioni) {
            totalizer += r.getPunteggio();
        }
        setMediaVoti(totalizer / listaRecensioni.size());
    }

    @Override
    public void aggiungiRecensione(Recensione recensione) {
        listaRecensioni.add(recensione);
        calcolaMediaVoti();
    }

    @Override
    public void rimuoviRecensione(Recensione recensione) {
        listaRecensioni.remove(recensione);
        calcolaMediaVoti();
    }

    @Override
    public List<Recensione> getListaRecensioni() {
        return listaRecensioni;
    }

    public Stagione.StagioneID getStagioneId() {
        return stagioneId;
    }

    public void setStagioneId(Stagione.StagioneID stagioneId) {
        this.stagioneId = stagioneId;
    }

    public void setListaRecensioni(List<Recensione> listaRecensioni) {
        this.listaRecensioni = listaRecensioni;
    }

    public void setMediaVoti(Double mediaVoti) {
        this.mediaVoti = mediaVoti;
    }

    public static class PuntataID implements Serializable {

        private Integer numeroPuntata;
        private Stagione.StagioneID stagioneId;

        public PuntataID() {
        }

        public PuntataID(Integer numeroPuntata, Stagione.StagioneID stagioneId) {
            this.numeroPuntata = numeroPuntata;
            this.stagioneId = stagioneId;
        }

        public Integer getNumeroPuntata() {
            return numeroPuntata;
        }

        public void setNumeroPuntata(Integer numeroPuntata) {
            this.numeroPuntata = numeroPuntata;
        }

        public Stagione.StagioneID getStagioneId() {
            return stagioneId;
        }

        public void setStagioneId(Stagione.StagioneID stagioneId) {
            this.stagioneId = stagioneId;
        }

        @Override
        public String toString() {
            return "PuntataID{" +
                    "numeroPuntata=" + numeroPuntata +
                    ", stagioneId=" + stagioneId +
                    '}';
        }
    }
}

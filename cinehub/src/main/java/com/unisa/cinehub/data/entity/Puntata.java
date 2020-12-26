package com.unisa.cinehub.data.entity;

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
    @ManyToOne
    private Stagione stagione;

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

    public Stagione getStagione() {
        return stagione;
    }

    public void setStagione(Stagione stagione) {
        this.stagione = stagione;
    }

    @Override
    public String toString() {
        return "Puntata{" +
                "titolo='" + titolo + '\'' +
                ", numeroPuntata=" + numeroPuntata +
                ", sinossi='" + sinossi + '\'' +
                ", stagione=" + stagione +
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

    public static class PuntataID implements Serializable {

        private Integer numeroPuntata;
        private Stagione stagione;
        private SerieTv serieTv;

        public PuntataID() {
        }

        public PuntataID(Integer numeroPuntata, Stagione stagione) {
            this.numeroPuntata = numeroPuntata;
            this.stagione = stagione;
            serieTv = stagione.getSerieTv();
        }

        public Integer getNumeroPuntata() {
            return numeroPuntata;
        }

        public void setNumeroPuntata(Integer numeroPuntata) {
            this.numeroPuntata = numeroPuntata;
        }

        public Stagione getStagione() {
            return stagione;
        }

        public void setStagione(Stagione stagione) {
            this.stagione = stagione;
        }

        public SerieTv getSerieTv() {
            return serieTv;
        }

        public void setSerieTv(SerieTv serieTv) {
            this.serieTv = serieTv;
        }

        @Override
        public String toString() {
            return "PuntataID{" +
                    "numeroPuntata=" + numeroPuntata +
                    ", stagione=" + stagione +
                    ", serieTv=" + serieTv +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PuntataID)) return false;
            PuntataID puntataID = (PuntataID) o;
            return Objects.equals(getNumeroPuntata(), puntataID.getNumeroPuntata()) && Objects.equals(getStagione(), puntataID.getStagione()) && Objects.equals(getSerieTv(), puntataID.getSerieTv());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getNumeroPuntata(), getStagione(), getSerieTv());
        }
    }
}

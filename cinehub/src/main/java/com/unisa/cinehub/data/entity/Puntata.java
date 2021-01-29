package com.unisa.cinehub.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@IdClass(Puntata.PuntataID.class)
public class Puntata implements Recensibile, Cloneable{

    @NotBlank
    @NotNull
    private String titolo;
    @Id
    @Min(value = 1, message = "Il numero della puntata deve essere positivo")
    private Integer numeroPuntata;

    @Size(max = 1000, message = "Inserire meno di mille caratteri")
    @Column(length = 1000)
    private String sinossi;

    @Id
    @Column(length = 1000)
    private Stagione.StagioneID stagioneId;

    @ManyToOne
    @JsonIgnore
    private Stagione stagione;

    @OneToMany(cascade = {
            CascadeType.ALL
    }, orphanRemoval = true)
    @JsonIgnore
    private List<Recensione> listaRecensioni;

    private Double mediaVoti;

    private Boolean visibile = true;

    @Id
    @Column(length = 1000)
    private PuntataID puntataID;

    public Puntata() {
        this.mediaVoti = 0.0;
        this.listaRecensioni = new ArrayList<>();
        puntataID = new PuntataID();
    }

    public Puntata(String titolo, Integer numeroPuntata, String sinossi) {
        this.titolo = titolo;
        this.numeroPuntata = numeroPuntata;
        this.sinossi = sinossi;
        this.mediaVoti = 0.0;
        this.listaRecensioni = new ArrayList<>();
        puntataID = new PuntataID(numeroPuntata, stagioneId);
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
        this.puntataID.setNumeroPuntata(numeroPuntata);
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
        if(stagione != null) {
            this.stagioneId = new Stagione.StagioneID(stagione.getNumeroStagione(), stagione.getSerieTvId());
            this.puntataID.setStagioneId(this.stagioneId);
        }
    }

    public void setStagioneId(Stagione.StagioneID stagioneId) {
        this.stagioneId = stagioneId;
    }

    public PuntataID getPuntataID() {
        return puntataID;
    }

    public void setPuntataID(PuntataID puntataID) {
        this.puntataID = puntataID;
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


    public void setMediaVoti(Double mediaVoti) {
        this.mediaVoti = mediaVoti;
    }

    public static boolean checkPuntata(Puntata p) {
        return p != null && p.getNumeroPuntata() > 0 && !p.getSinossi().isBlank() && !p.getTitolo().isBlank();
    }

    public void setListaRecensioni(List<Recensione> listaRecensioni) {
        this.listaRecensioni = listaRecensioni;
    }

    public Boolean getVisibile() {
        return visibile;
    }

    public void setVisibile(Boolean visibile) {
        this.visibile = visibile;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Puntata)) return false;
        Puntata puntata = (Puntata) o;
        return Objects.equals(getNumeroPuntata(), puntata.getNumeroPuntata()) && Objects.equals(getStagioneId(), puntata.getStagioneId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroPuntata(), getStagioneId());
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PuntataID)) return false;
            PuntataID puntataID = (PuntataID) o;
            return Objects.equals(getNumeroPuntata(), puntataID.getNumeroPuntata()) && Objects.equals(getStagioneId(), puntataID.getStagioneId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getNumeroPuntata(), getStagioneId());
        }
    }

    public String puntataPath(){
        return numeroPuntata + "-" + stagioneId.getNumeroStagione() + "-" + stagioneId.getSerieTvId();
    }
}

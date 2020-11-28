package com.unisa.cinehub.data.entity;

import com.helger.commons.url.URLValidator;
import com.unisa.cinehub.data.AbstractEntity;

public abstract class Media extends AbstractEntity {
    private String titolo;
    private Integer annoUscita;
    private String sinossi;
    private String linkTrailer;
    private String linkLocandina;

    public Media(String titolo, Integer annoUscita, String sinossi, String linkTrailer, String linkLocandina) {
        this.titolo = titolo;
        this.annoUscita = annoUscita;
        this.sinossi = sinossi;
        this.linkTrailer = linkTrailer;
        this.linkLocandina = linkLocandina;
    }

    public Media() {
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Integer getAnnoUscita() {
        return annoUscita;
    }

    public void setAnnoUscita(Integer annoUscita) {
        this.annoUscita = annoUscita;
    }

    public String getSinossi() {
        return sinossi;
    }

    public void setSinossi(String sinossi) {
        this.sinossi = sinossi;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        if(URLValidator.isValid(linkTrailer))
            this.linkTrailer = linkTrailer;
    }

    public String getLinkLocandina() {
        return linkLocandina;
    }

    public void setLinkLocandina(String linkLocandina) {
        if(URLValidator.isValid(linkLocandina))
            this.linkLocandina = linkLocandina;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "titolo='" + titolo + '\'' +
                ", annoUscita=" + annoUscita +
                ", sinossi='" + sinossi + '\'' +
                ", linkTrailer='" + linkTrailer + '\'' +
                ", linkLocandina='" + linkLocandina + '\'' +
                '}';
    }
}

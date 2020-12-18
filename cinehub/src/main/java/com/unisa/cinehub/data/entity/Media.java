package com.unisa.cinehub.data.entity;

import com.helger.commons.url.URLValidator;
import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;


public abstract class Media extends AbstractEntity implements Cloneable{
    private String titolo;
    private Integer annoUscita;
    private String sinossi;
    private String linkTrailer;
    private String linkLocandina;

    /*@ManyToMany
    @JoinTable(
            name = "media_genere",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "genere_id")
    )
    private Set<Genere> generi;*/

    public Media(String titolo, Integer annoUscita, String sinossi, String linkTrailer, String linkLocandina) {
        this.titolo = titolo;
        this.annoUscita = annoUscita;
        this.sinossi = sinossi;
        this.linkTrailer = linkTrailer;
        this.linkLocandina = linkLocandina;
    }

    public Media() {
    }
/*
    public Set<Genere> getGeneri() {
        return generi;
    }

    public void setGeneri(Set<Genere> generi) {
        this.generi = generi;
    }
*/
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
        return getClass().getSimpleName() + "{" +
                "titolo='" + titolo + '\'' +
                ", annoUscita=" + annoUscita +
                ", sinossi='" + sinossi + '\'' +
                ", linkTrailer='" + linkTrailer + '\'' +
                ", linkLocandina='" + linkLocandina + '\'' +
                '}';
    }
}

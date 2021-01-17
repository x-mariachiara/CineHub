package com.unisa.cinehub.data.entity;

import com.helger.commons.url.URLValidator;
import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Media extends AbstractEntity implements Cloneable{
    @NotNull
    private String titolo;
    @NotNull
    private Integer annoUscita;
    @Column(length = 1000)
    private String sinossi;
    private String linkTrailer;
    private String linkLocandina;

    private Double mediaVoti;

    @ManyToMany()
    @JoinTable(
            name = "media_genere",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "genere_id")
    )
    @NotEmpty
    private Set<Genere> generi;

    @OneToMany
    private Collection<Ruolo> ruoli;

    public Media(String titolo, Integer annoUscita, String sinossi, String linkTrailer, String linkLocandina) {
        this.titolo = titolo;
        this.annoUscita = annoUscita;
        this.sinossi = sinossi;
        this.linkTrailer = linkTrailer;
        this.linkLocandina = linkLocandina;
        this.generi = new HashSet<>();
        this.ruoli = new HashSet<>();
        this.mediaVoti = 0.0;
    }

    public Media() {
        this.generi= new HashSet<>();
        this.ruoli = new HashSet<>();
        this.mediaVoti = 0.0;
    }


    public Set<Genere> getGeneri() {
        return generi;
    }

    public void setGeneri(Set<Genere> generi) {
        this.generi = generi;
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

    public Double getMediaVoti() {
        return mediaVoti;
    }

    public void setMediaVoti(Double mediaVoti) {
        this.mediaVoti = mediaVoti;
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

    public Collection<Ruolo> getRuoli() {
        return ruoli;
    }

    public void setRuoli(Collection<Ruolo> ruoli) {
        this.ruoli = ruoli;
    }

    public abstract void calcolaMediaVoti();

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

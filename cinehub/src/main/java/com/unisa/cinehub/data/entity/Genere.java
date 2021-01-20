package com.unisa.cinehub.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.*;
import java.util.*;

@Entity
public class Genere implements Cloneable{

    public enum NomeGenere {
        ANIMAZIONE, ANIME, AVVENTURA, AZIONE, BIOGRAFICO, COMMEDIA, DOCUMENTARIO,
        DRAMMATICI, EROTICO, FANTASCIENZA, FANTASY, GUERRA, GIALLO, HORROR,
        MUSICAL, BAMBINI, POLIZIESCO, ROMANTICO, SPORTIVO, THRILLER, WESTERN
    };

    @Id
    private NomeGenere nomeGenere;

   @ManyToMany(
            mappedBy = "generi",
            fetch = FetchType.LAZY
    )
   @JsonIgnore
    private Set<Media> mediaCollegati = new HashSet<>();

    public Genere() { }

    public Genere(NomeGenere nomeGenere) {
        this.nomeGenere = nomeGenere;
    }

    public NomeGenere getNomeGenere() {
        return nomeGenere;
    }

    public void setNomeGenere(NomeGenere nomeGenere) {
        this.nomeGenere = nomeGenere;
    }

    public Set<Media> getMediaCollegati() {
        return mediaCollegati;
    }

    public void setMediaCollegati(Set<Media> mediaCollegati) {
        this.mediaCollegati = mediaCollegati;
    }

    public static Set<Genere> getTuttiGeneri() {
        Set<Genere> generi = new HashSet<>();
        Arrays.stream(NomeGenere.values()).forEach(nomeGenere -> {
            generi.add(new Genere(nomeGenere));
        });
        return generi;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "nomeGenere=" + nomeGenere +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genere)) return false;
        Genere genere = (Genere) o;
        return getNomeGenere() == genere.getNomeGenere();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNomeGenere());
    }
}

package com.unisa.cinehub.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.*;
import java.util.Set;

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
    private Set<Media> mediaCollegati;

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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "nomeGenere=" + nomeGenere +
                '}';
    }
}

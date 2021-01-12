package com.unisa.cinehub.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
}

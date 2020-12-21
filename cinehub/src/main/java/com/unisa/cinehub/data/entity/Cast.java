package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity(name = "castFilm")
public class Cast extends AbstractEntity{

    private String nomeGenere;

    @OneToMany
    private Collection<Ruolo> ruoli;


    public Cast() { }

    public Cast(String nomeGenere) {
        this.nomeGenere = nomeGenere;
    }

    public String getNomeGenere() {
        return nomeGenere;
    }

    public void setNomeGenere(String nomeGenere) {
        this.nomeGenere = nomeGenere;
    }

    public Collection<Ruolo> getRuoli() {
        return ruoli;
    }

    public void setRuoli(Collection<Ruolo> ruoli) {
        this.ruoli = ruoli;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "nomeGenere='" + nomeGenere + '\'' +
                '}';
    }
}

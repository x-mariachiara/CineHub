package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Genere extends AbstractEntity {

    private String nomeGenere;

    public Genere() { }

    public Genere(String nomeGenere) {
        this.nomeGenere = nomeGenere;
    }

    public String getNomeGenere() {
        return nomeGenere;
    }

    public void setNomeGenere(String nomeGenere) {
        this.nomeGenere = nomeGenere;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "nomeGenere='" + nomeGenere + '\'' +
                '}';
    }
}

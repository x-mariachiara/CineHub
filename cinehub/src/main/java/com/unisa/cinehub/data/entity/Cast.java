package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Collection;

@Entity
public class Cast extends AbstractEntity{

    private String nomeGenere;


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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "nomeGenere='" + nomeGenere + '\'' +
                '}';
    }
}

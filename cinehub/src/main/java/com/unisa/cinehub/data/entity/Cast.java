package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "castFilm")
public class Cast extends AbstractEntity{


    private String nome;
    private String cognome;

    @OneToMany
    private Collection<Ruolo> ruoli;


    public Cast() { }

    public Cast(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Collection<Ruolo> getRuoli() {
        return ruoli;
    }

    public void setRuoli(Collection<Ruolo> ruoli) {
        this.ruoli = ruoli;
    }



    @Override
    public String toString() {
        return "Cast{" +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", ruoli=" + ruoli +
                '}';
    }
}

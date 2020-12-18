package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;
import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
public class Recensione extends AbstractEntity {

    private Timestamp createdAt;
    private String contenuto;
    private Integer punteggio;

    @ManyToOne
    private Recensore recensore;

    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    private List<Segnalazione> listaSegnalazioni;

    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    private List<MiPiace> listaMiPiace;

    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    private List<Recensione> listaRisposte;
/*
    //@Target(Film.class)
    @ManyToOne
    private Recensibile recensibile;
*/
    public Recensione() {
    }

    public Recensione(String contenuto, Integer punteggio) {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.contenuto = contenuto;
        if(punteggio >= 1 && punteggio <= 5)
            this.punteggio = punteggio;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public Integer getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(Integer punteggio) {
        if(punteggio >= 1 && punteggio <= 5)
            this.punteggio = punteggio;
    }

    public Recensore getRecensore() {
        return recensore;
    }

    public void setRecensore(Recensore recensore) {
        this.recensore = recensore;
    }

    public Collection<Segnalazione> getListaSegnalazioni() {
        return listaSegnalazioni;
    }

    public void setListaSegnalazioni(List<Segnalazione> listaSegnalazioni) {
        this.listaSegnalazioni = listaSegnalazioni;
    }

    public List<MiPiace> getListaMiPiace() {
        return listaMiPiace;
    }

    public void setListaMiPiace(List<MiPiace> listaMiPiace) {
        this.listaMiPiace = listaMiPiace;
    }

    public List<Recensione> getListaRisposte() {
        return listaRisposte;
    }

    public void setListaRisposte(List<Recensione> listaRisposte) {
        this.listaRisposte = listaRisposte;
    }
/*
    public Recensibile getRecensibile() {
        return recensibile;
    }

    public void setRecensibile(Recensibile recensibile) {
        this.recensibile = recensibile;
    }
*/
    @Override
    public String toString() {
        return "Recensione{" +
                "createdAt=" + createdAt +
                ", contenuto='" + contenuto + '\'' +
                ", punteggio=" + punteggio +
                ", recensore=" + recensore +
                ", listaSegnalazioni=" + listaSegnalazioni +
                '}';
    }
}

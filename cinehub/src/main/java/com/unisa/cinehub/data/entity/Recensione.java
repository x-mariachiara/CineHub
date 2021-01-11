package com.unisa.cinehub.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisa.cinehub.data.AbstractEntity;
import org.hibernate.annotations.Target;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
public class Recensione extends AbstractEntity {

    @Id
    private Long id;

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

    @ManyToOne
    private Film film;

    @ManyToOne
    private Puntata puntata;

    public Recensione() {
    }

    public Recensione(String contenuto, Integer punteggio) {

        this.contenuto = contenuto;
        if(punteggio >= 1 && punteggio <= 5)
            this.punteggio = punteggio;

    }

    public Recensione(String contenuto, Integer punteggio, Film film) {

        this.contenuto = contenuto;
        if(punteggio >= 1 && punteggio <= 5)
            this.punteggio = punteggio;
        this.film = film;
    }

    public Recensione(String contenuto, Integer punteggio, Puntata puntata) {

        this.contenuto = contenuto;
        if(punteggio >= 1 && punteggio <= 5)
            this.punteggio = punteggio;
        this.puntata = puntata;
    }

    @PostConstruct
    public void addCreationDate(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
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

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        if (this.puntata == null) {
            this.film = film;
        }
    }

    public Puntata getPuntata() {
        return puntata;
    }

    public void setPuntata(Puntata puntata) {
        if(this.film == null) {
            this.puntata = puntata;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "{Recensione{" +
                "createdAt=" + createdAt +
                ", contenuto='" + contenuto + '\'' +
                ", punteggio=" + punteggio +
                ", recensore=" + recensore +
                ", listaSegnalazioni=" + listaSegnalazioni +
                ", listaMiPiace=" + listaMiPiace +
                ", listaRisposte=" + listaRisposte +
                ", film=" + film +
                ", puntata=" + puntata +
                '}';
    }
}

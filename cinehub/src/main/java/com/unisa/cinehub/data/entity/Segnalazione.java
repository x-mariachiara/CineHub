package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;

@Entity
public class Segnalazione extends AbstractEntity {
    private Timestamp createdAt;


    @ManyToOne
    private Recensore recensore;

    @ManyToOne
    private Recensione recensione;

    public Segnalazione() {
    }

    @PostConstruct
    public void initializeCreatedAt() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Recensore getRecensore() {
        return recensore;
    }

    public void setRecensore(Recensore recensore) {
        this.recensore = recensore;
    }

    public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
    }

    @Override
    public String toString() {
        return "Segnalazione{" +
                "createdAt=" + createdAt +
                ", recensore=" + recensore +
                ", recensione=" + recensione +
                '}';
    }
}

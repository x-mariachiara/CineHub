package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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

    public Segnalazione(Timestamp createdAt) {
        this.createdAt = createdAt;
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

package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Recensione extends AbstractEntity {

    private Timestamp createdAt;
    private String contenuto;
    private Integer punteggio;

    public Recensione() {
    }

    public Recensione(Timestamp createdAt, String contenuto, Integer punteggio) {
        this.createdAt = createdAt;
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

    @Override
    public String toString() {
        return "Recensione{" +
                "createdAt=" + createdAt +
                ", contenuto='" + contenuto + '\'' +
                ", punteggio=" + punteggio +
                '}';
    }
}

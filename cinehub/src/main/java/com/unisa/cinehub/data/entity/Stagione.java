package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Stagione extends AbstractEntity {

    private Integer numeroStagione;

    public Stagione() {
    }

    public Stagione(Integer numeroStagione) {
        this.numeroStagione = numeroStagione;
    }

    public Integer getNumeroStagione() {
        return numeroStagione;
    }

    public void setNumeroStagione(Integer numeroStagione) {
        this.numeroStagione = numeroStagione;
    }

    @Override
    public String toString() {
        return "Stagione{" +
                "numeroStagione=" + numeroStagione +
                '}';
    }
}

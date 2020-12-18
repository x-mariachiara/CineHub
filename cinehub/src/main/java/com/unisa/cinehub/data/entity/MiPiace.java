package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class MiPiace extends AbstractEntity implements Cloneable {

    private boolean tipo;
    private Timestamp createdAt;
    @ManyToOne
    private Recensione recensione;
    @ManyToOne
    private Recensore recensore;

    public MiPiace() {
    }

    public MiPiace(boolean tipo) {
        this.tipo = tipo;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
    }

    public Recensore getRecensore() {
        return recensore;
    }

    public void setRecensore(Recensore recensore) {
        this.recensore = recensore;
    }

    @Override
    public String toString() {
        return "MiPiace{" +
                "tipo=" + tipo +
                ", createdAt=" + createdAt +
                ", recensione=" + recensione +
                ", recensore=" + recensore +
                '}';
    }
}

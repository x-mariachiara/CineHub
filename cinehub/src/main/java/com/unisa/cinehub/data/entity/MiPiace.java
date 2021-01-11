package com.unisa.cinehub.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(MiPiace.MiPiaceID.class)
public class MiPiace implements Cloneable {

    private boolean tipo;
    private Timestamp createdAt;

    @Id
    @ManyToOne
    private Recensione recensione;

    @Id
    @ManyToOne
    private Recensore recensore;

    public MiPiace() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
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

    public  static  class MiPiaceID implements Serializable {
        private Recensore recensore;
        private Recensione recensione;

        public MiPiaceID() {
        }

        public MiPiaceID(Recensore recensore, Recensione recensione) {
            this.recensore = recensore;
            this.recensione = recensione;
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
            return "MiPiaceID{" +
                    "recensore=" + recensore +
                    ", recensione=" + recensione +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MiPiaceID)) return false;
            MiPiaceID miPiaceID = (MiPiaceID) o;
            return Objects.equals(getRecensore(), miPiaceID.getRecensore()) && Objects.equals(getRecensione(), miPiaceID.getRecensione());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getRecensore(), getRecensione());
        }
    }
}

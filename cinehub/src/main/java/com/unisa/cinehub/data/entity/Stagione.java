package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;



@Entity
@IdClass(Stagione.StagioneID.class)
public class Stagione implements Cloneable {

    @Id
    private Integer numeroStagione;

    public Stagione() {
    }

    @OneToMany(cascade = CascadeType.REMOVE)
    private Collection<Puntata> puntate;

    @Id
    @ManyToOne
    private SerieTv serieTv;

    public Stagione(Integer numeroStagione) {
        this.numeroStagione = numeroStagione;
    }

    public Integer getNumeroStagione() {
        return numeroStagione;
    }

    public void setNumeroStagione(Integer numeroStagione) {
        this.numeroStagione = numeroStagione;
    }

    public Collection<Puntata> getPuntate() {
        return puntate;
    }

    public void setPuntate(Collection<Puntata> puntate) {
        this.puntate = puntate;
    }

    public SerieTv getSerieTv() {
        return serieTv;
    }

    public void setSerieTv(SerieTv serieTv) {
        this.serieTv = serieTv;
    }

    @Override
    public String toString() {
        return "Stagione{" +
                "numeroStagione=" + numeroStagione +
                ", puntate=" + puntate +
                ", serieTv=" + serieTv +
                '}';
    }

    public static class StagioneID implements Serializable {

        private Integer numeroStagione;
        private SerieTv serieTv;

        public StagioneID() {
        }

        public StagioneID(Integer numeroStagione, SerieTv serieTv) {
            this.numeroStagione = numeroStagione;
            this.serieTv = serieTv;
        }

        public Integer getNumeroStagione() {
            return numeroStagione;
        }

        public void setNumeroStagione(Integer numeroStagione) {
            this.numeroStagione = numeroStagione;
        }

        public SerieTv getSerieTv() {
            return serieTv;
        }

        public void setSerieTv(SerieTv serieTv) {
            this.serieTv = serieTv;
        }

        @Override
        public String toString() {
            return "StagioneID{" +
                    "numeroStagione=" + numeroStagione +
                    ", serieTv=" + serieTv +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StagioneID)) return false;
            StagioneID that = (StagioneID) o;
            return Objects.equals(numeroStagione, that.numeroStagione) && Objects.equals(serieTv, that.serieTv);
        }

        @Override
        public int hashCode() {
            return Objects.hash(numeroStagione, serieTv);
        }
    }
}

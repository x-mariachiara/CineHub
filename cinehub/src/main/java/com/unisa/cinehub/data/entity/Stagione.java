package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;



@Entity
@IdClass(Stagione.StagioneID.class)
public class Stagione implements Cloneable {

    @Id
    @Column(name = "numero_stagione")
    private Integer numeroStagione;

    @Id
    @Column(name = "serie_tv_id")
    private Long serieTvId;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Collection<Puntata> puntate;

    @ManyToOne
    @JoinColumn(name = "serie_tv_id", insertable = false, updatable = false)
    private SerieTv serieTv;



    public Stagione() {
        this.puntate = new ArrayList<>();
    }

    public Stagione(Integer numeroStagione) {
        this.numeroStagione = numeroStagione;
        this.puntate = new ArrayList<>();
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
        setSerieTvId(serieTv.getId());
    }

    public Long getSerieTvId() {
        return serieTvId;
    }

    public void setSerieTvId(Long serieTvId) {
        this.serieTvId = serieTvId;
    }

    public String getNomeStagione() {
        return "Stagione " + numeroStagione;
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

        @Column(name = "numero_stagione")
        private Integer numeroStagione;
        @Column(name = "serie_tv_id")
        private Long serieTvId;

        public StagioneID() {
        }

        public StagioneID(Integer numeroStagione, Long serieTvId) {
            this.numeroStagione = numeroStagione;
            this.serieTvId = serieTvId;
        }

        public Integer getNumeroStagione() {
            return numeroStagione;
        }

        public void setNumeroStagione(Integer numeroStagione) {
            this.numeroStagione = numeroStagione;
        }

        public Long getSerieTvId() {
            return serieTvId;
        }

        public void setSerieTvId(Long serieTvId) {
            this.serieTvId = serieTvId;
        }

        @Override
        public String toString() {
            return "StagioneID{" +
                    "numeroStagione=" + numeroStagione +
                    ", serieTvId=" + serieTvId +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StagioneID that = (StagioneID) o;
            return numeroStagione.equals(that.numeroStagione) && serieTvId.equals(that.serieTvId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(numeroStagione, serieTvId);
        }
    }
}

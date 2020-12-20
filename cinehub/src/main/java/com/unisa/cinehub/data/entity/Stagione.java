package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class Stagione extends AbstractEntity implements Cloneable {

    private Integer numeroStagione;

    public Stagione() {
    }

    @OneToMany(cascade = CascadeType.REMOVE)
    private Collection<Puntata> puntate;

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
}

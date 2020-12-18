package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Ruolo extends AbstractEntity implements Cloneable{

    protected enum Tipo {REGISTA, ATTORE, VOICEACTOR};

    private Tipo tipo;

//    @OneToMany
//    private Cast cast;

    public Ruolo() {
    }

//    public Cast getCast() {
//        return cast;
//    }
//
//    public void setCast(Cast cast) {
//        this.cast = cast;
//    }

    public Ruolo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "tipo=" + tipo +
                '}';
    }
}

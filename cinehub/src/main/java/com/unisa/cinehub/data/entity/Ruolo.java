package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Ruolo extends AbstractEntity {

    protected enum Tipo {REGISTRA, ATTORE, VOICEACTOR};

    private Tipo tipo;
    //  private Cast cast;
    //  private Media media;

    public Ruolo() {
    }

    public Ruolo(Cast cast, Media media, Tipo tipo) {
        // this.cast = cast;
        // this.media = media;
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

package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class SerieTv extends Media {

    private Integer mediaVoti;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Collection<Stagione> stagioni;

    public SerieTv() {
        this.stagioni = new ArrayList<>();
    }

    public SerieTv(String titolo, Integer annoUscita, String sinossi, String linkTrailer, String linkLocandina) {
        super(titolo, annoUscita, sinossi, linkTrailer, linkLocandina);
        this.stagioni = new ArrayList<>();
    }

    public Integer getMediaVoti() {
        return mediaVoti;
    }

    public void setMediaVoti(Integer mediaVoti) {
        this.mediaVoti = mediaVoti;
    }

    public Collection<Stagione> getStagioni() {
        return stagioni;
    }

    public void setStagioni(Collection<Stagione> stagioni) {
        this.stagioni = stagioni;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "mediaVoti=" + mediaVoti +
                //", stagioni=" + stagioni +
                '}';
    }
}

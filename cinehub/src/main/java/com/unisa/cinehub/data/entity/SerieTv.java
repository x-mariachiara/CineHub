package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class SerieTv extends Media {

    private Double mediaVoti;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Collection<Stagione> stagioni;

    public SerieTv() {
        this.stagioni = new ArrayList<>();
        this.mediaVoti = 0.0;
    }

    public SerieTv(String titolo, Integer annoUscita, String sinossi, String linkTrailer, String linkLocandina) {
        super(titolo, annoUscita, sinossi, linkTrailer, linkLocandina);
        this.stagioni = new ArrayList<>();
        this.mediaVoti = 0.0;
    }

    public Double getMediaVoti() {
        return mediaVoti;
    }

    public void setMediaVoti(Double mediaVoti) {
        this.mediaVoti = mediaVoti;
    }

    public Collection<Stagione> getStagioni() {
        return stagioni;
    }

    public void setStagioni(Collection<Stagione> stagioni) {
        this.stagioni = stagioni;
    }

    public void calcolaMediaVoti() {
        Double totalizzatore = 0.0;
        for(Stagione stagione: stagioni) {
            totalizzatore += calcolaMediaVotiStagione(stagione);
        }
        setMediaVoti(totalizzatore / stagioni.size());
    }

    private Double calcolaMediaVotiStagione(Stagione stagione) {
        Double totalizzatore = 0.0;
        for(Puntata puntata : stagione.getPuntate()) {
            totalizzatore += puntata.getMediaVoti();
        }
        return totalizzatore / stagione.getPuntate().size();
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "mediaVoti=" + mediaVoti +
                //", stagioni=" + stagioni +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class SerieTv extends AbstractEntity {

    private Integer mediaVoti;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Collection<Stagione> stagioni;

    public SerieTv() {
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
                ", stagioni=" + stagioni +
                '}';
    }
}

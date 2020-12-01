package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class SerieTv extends AbstractEntity {

    private Integer mediaVoti;

    public SerieTv() {
    }

    public Integer getMediaVoti() {
        return mediaVoti;
    }

    public void setMediaVoti(Integer mediaVoti) {
        this.mediaVoti = mediaVoti;
    }
}

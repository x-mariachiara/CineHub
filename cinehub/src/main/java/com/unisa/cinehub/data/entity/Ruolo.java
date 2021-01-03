package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(Ruolo.RuoloID.class)
public class Ruolo implements Cloneable{

    protected enum Tipo {REGISTA, ATTORE, VOICEACTOR};

    @Id
    @Column(name = "tipo_ruolo")
    private Tipo tipo;

    @Id
    @Column(name = "cast_id")
    private Long castId;

    @Id
    @Column(name = "media_id")
    private Long mediaId;

    @ManyToOne
    @JoinColumn(name = "cast_id", insertable = false, updatable = false)
    private Cast cast;

    @ManyToOne
    @JoinColumn(name = "media_id", insertable = false, updatable = false)
    private Media media;

    public Ruolo() {
    }

    public Ruolo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Long getCastId() {
        return castId;
    }

    public void setCastId(Long castId) {
        this.castId = castId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Cast getCast() {
        return cast;
    }

    public void setCast(Cast cast) {
        this.cast = cast;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "Ruolo{" +
                "tipo=" + tipo +
                ", cast=" + cast +
                ", media=" + media +
                '}';
    }

    public static class RuoloID implements Serializable {

        @Column(name = "tipo_ruolo")
        private Tipo tipo;
        @Column(name = "cast_id")
        private Long castId;
        @Column(name = "media_id")
        private Long mediaId;

        public RuoloID() {
        }

        public RuoloID(Tipo tipo, Long castId, Long mediaId) {
            this.tipo = tipo;
            this.castId = castId;
            this.mediaId = mediaId;
        }

        public Tipo getTipo() {
            return tipo;
        }

        public void setTipo(Tipo tipo) {
            this.tipo = tipo;
        }

        public Long getCastId() {
            return castId;
        }

        public void setCastId(Long castId) {
            this.castId = castId;
        }

        public Long getMediaId() {
            return mediaId;
        }

        public void setMediaId(Long mediaId) {
            this.mediaId = mediaId;
        }

        @Override
        public String toString() {
            return "RuoloID{" +
                    "tipo=" + tipo +
                    ", castId=" + castId +
                    ", mediaId=" + mediaId +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RuoloID ruoloID = (RuoloID) o;
            return tipo.equals(ruoloID.tipo) && castId.equals(ruoloID.castId) && mediaId.equals(ruoloID.mediaId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTipo(), getCastId(), getMediaId());
        }
    }
}

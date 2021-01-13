package com.unisa.cinehub.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(Segnalazione.SegnalazioneID.class)
public class Segnalazione implements Cloneable {

    private Timestamp createdAt;

    @Id
    private String segnalatoreId;

    @Id
    @Column(name = "recensore_email")
    private String recensoreId;

    @Id
    @Column(name = "recensione_id")
    private Long recensioneId;

    @ManyToOne
    @JoinColumn(name = "recensore_email", insertable = false, updatable = false)
    private Recensore recensore;

    @ManyToOne
    @JoinColumn(name = "recensione_id", insertable = false, updatable = false)
    private Recensione recensione;

    public Segnalazione() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getSegnalatoreId() {
        return segnalatoreId;
    }

    public void setSegnalatoreId(String segnalatoreId) {
        this.segnalatoreId = segnalatoreId;
    }

    public String getRecensoreId() {
        return recensoreId;
    }

    public void setRecensoreId(String recensoreId) {
        this.recensoreId = recensoreId;
    }

    public Long getRecensioneId() {
        return recensioneId;
    }

    public void setRecensioneId(Long recensioneId) {
        this.recensioneId = recensioneId;
    }

    public Recensore getRecensore() {
        return recensore;
    }

    public void setRecensore(Recensore recensore) {
        this.recensore = recensore;
        this.recensoreId = recensore.getEmail();
    }

    public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
        this.recensioneId = recensione.getId();
    }

    @Override
    public String toString() {
        return "Segnalazione{" +
                "createdAt=" + createdAt +
                ", segnalatoreId='" + segnalatoreId + '\'' +
                ", recensoreId='" + recensoreId + '\'' +
                ", recensioneId=" + recensioneId +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Segnalazione)) return false;
        Segnalazione segnalazione = (Segnalazione) o;
        return Objects.equals(createdAt, segnalazione.createdAt) &&
                Objects.equals(segnalatoreId, segnalazione.segnalatoreId) &&
                Objects.equals(recensoreId, segnalazione.recensoreId) &&
                Objects.equals(recensioneId, segnalazione.recensioneId) &&
                Objects.equals(recensione, segnalazione.recensione) &&
                Objects.equals(recensore, segnalazione.recensore);
    }

    @Override
    public int hashCode() { return Objects.hash(createdAt, segnalatoreId, recensoreId, recensioneId, recensore, recensione); }

    public static class SegnalazioneID implements Serializable {

        private String segnalatoreId;

        @Column(name = "recensore_email", insertable = false, updatable = false)
        private String recensoreId;

        @Column(name = "recensione_id", insertable = false, updatable = false)
        private Long recensioneId;

        public SegnalazioneID(){
        }

        public SegnalazioneID(String segnalatoreId, String recensoreId, Long recensioneId) {
            this.segnalatoreId = segnalatoreId;
            this.recensoreId = recensoreId;
            this.recensioneId = recensioneId;
        }

        public String getSegnalatoreId() {
            return segnalatoreId;
        }

        public void setSegnalatoreId(String segnalatoreId) {
            this.segnalatoreId = segnalatoreId;
        }

        public String getRecensoreId() {
            return recensoreId;
        }

        public void setRecensoreId(String recensoreId) {
            this.recensoreId = recensoreId;
        }

        public Long getRecensioneId() {
            return recensioneId;
        }

        public void setRecensioneId(Long recensioneId) {
            this.recensioneId = recensioneId;
        }

        @Override
        public String toString() {
            return "SegnalazioneID{" +
                    "segnalatoreId='" + segnalatoreId + '\'' +
                    ", recensoreId='" + recensoreId + '\'' +
                    ", recensioneId=" + recensioneId +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(!(o instanceof SegnalazioneID)) return false;
            SegnalazioneID segnalazioneID = (SegnalazioneID) o;
            return Objects.equals(segnalatoreId, segnalazioneID.segnalatoreId) &&
                    Objects.equals(recensioneId, segnalazioneID.recensioneId) &&
                    Objects.equals(recensoreId, segnalazioneID.recensoreId);
        }
    }
}

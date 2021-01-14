package com.unisa.cinehub.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(MiPiace.MiPiaceID.class)
public class MiPiace implements Cloneable {

    private boolean tipo;

    private Timestamp createdAt;

    @Id
    @Column(name = "recensore_email")
    private String recensoreId;

    @Id
    @Column(name = "recensione_id")
    private Long recensioneId;

    @ManyToOne
    @JoinColumn(name = "recensione_id", insertable = false, updatable = false)
    private Recensione recensione;


    @ManyToOne
    @JoinColumn(name = "recensore_email", insertable = false, updatable = false)
    private Recensore recensore;

    public MiPiace() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public MiPiace(boolean tipo) {
        this.tipo = tipo;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Recensione getRecensione() {
        return recensione;
    }

    public void setRecensione(Recensione recensione) {
        this.recensione = recensione;
        this.recensioneId = recensione.getId();
    }

    public Recensore getRecensore() {
        return recensore;
    }

    public void setRecensore(Recensore recensore) {
        this.recensore = recensore;
        this.recensoreId = recensore.getEmail();
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
        return "MiPiace{" +
                "tipo=" + tipo +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiPiace miPiace = (MiPiace) o;
        return recensoreId.equals(miPiace.recensoreId) && recensioneId.equals(miPiace.recensioneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recensoreId, recensioneId);
    }

    public  static  class MiPiaceID implements Serializable {

        @Column(name = "recensore_email", insertable = false, updatable = false)
        private String recensoreId;

        @Column(name = "recensione_id", insertable = false, updatable = false)
        private Long recensioneId;

        public MiPiaceID() {
        }

        public MiPiaceID(String recensoreId, Long recensioneId) {
            this.recensoreId = recensoreId;
            this.recensioneId = recensioneId;
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
            return "MiPiaceID{" +
                    "recensore=" + recensoreId +
                    ", recensione=" + recensioneId +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MiPiaceID)) return false;
            MiPiaceID miPiaceID = (MiPiaceID) o;
            return Objects.equals(recensoreId, miPiaceID.recensoreId) &&
                    Objects.equals(recensioneId, miPiaceID.recensioneId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(recensoreId, recensioneId);
        }
    }
}

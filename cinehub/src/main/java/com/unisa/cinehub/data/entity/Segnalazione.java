package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Segnalazione extends AbstractEntity {
    private Timestamp createdAt;

    public Segnalazione() {
    }

    public Segnalazione(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "createdAt=" + createdAt +
                '}';
    }
}

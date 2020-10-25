package com.cinehub.cinehub.segnalazione;

import java.sql.Timestamp;

public class Segnalazione {
    private String tipo;
    private Timestamp createdAt;

    public Segnalazione(String tipo, Timestamp createdAt) {
        this.tipo = tipo;
        this.createdAt = createdAt;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

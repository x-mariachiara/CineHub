package com.cinehub.cinehub.mipiace;

import java.sql.Timestamp;

public class MiPiace {
    private boolean tipo;
    private Timestamp createdAt;

    public MiPiace(boolean tipo, Timestamp createdAt) {
        this.tipo = tipo;
        this.createdAt = createdAt;
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "tipo=" + tipo +
                ", createdAt=" + createdAt +
                '}';
    }
}

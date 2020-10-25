package com.cinehub.cinehub.segnalazione;

public class Segnalazione {
    private String tipo;

    public Segnalazione(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "tipo='" + tipo + '\'' +
                '}';
    }
}

package com.cinehub.cinehub.gesioneGenere;

public class Genere {
    private String tipo;

    public Genere(String tipo) {
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

package com.cinehub.cinehub.gestioneUtente;

public class Moderatore extends Utente {
    private String tipo;

    public Moderatore(String email, String username, String password, boolean bannato, String tipo) {
        super(email, username, password, bannato);
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
        return super.toString() + this.getClass().getSimpleName() + "{tipo = " + tipo + "}";
    }
}

package com.cinehub.cinehub.utente;

import java.util.ArrayList;

public class Utente {
    private String email;
    private String username;
    private String password;
    private String tipo;
    private boolean bannato;
    private ArrayList<Recensione> listaRecensioni;
    private ArrayList<MiPiace> listaRecensioni;
    private ArrayList<Segnalazione> listaRecensioni;


    public Utente(String email, String username, String password, String tipo, boolean bannato) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.bannato = bannato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isBannato() {
        return bannato;
    }

    public void setBannato(boolean bannato) {
        this.bannato = bannato;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tipo='" + tipo + '\'' +
                ", bannato=" + bannato +
                '}';
    }
}


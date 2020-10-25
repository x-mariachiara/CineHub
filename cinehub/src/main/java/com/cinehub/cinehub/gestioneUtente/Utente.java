package com.cinehub.cinehub.gestioneUtente;

import com.cinehub.cinehub.gestioneMiPiace.MiPiace;
import com.cinehub.cinehub.gestioneRecensione.Recensione;
import com.cinehub.cinehub.gestioneSegnalazione.Segnalazione;

import java.util.ArrayList;

public class Utente {
    private String email;
    private String username;
    private String password;
    private boolean bannato;
    private ArrayList<Recensione> listaRecensioni;
    private ArrayList<MiPiace> listaMiPiace;
    private ArrayList<Segnalazione> listaSegnalazioni;


    public Utente(String email, String username, String password, boolean bannato) {
        this.email = email;
        this.username = username;
        this.password = password;
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

    public boolean isBannato() {
        return bannato;
    }

    public void setBannato(boolean bannato) {
        this.bannato = bannato;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bannato=" + bannato +
                '}';
    }
}


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

    public Utente(String email, String username, String password, boolean bannato, ArrayList<Recensione> listaRecensioni, ArrayList<MiPiace> listaMiPiace, ArrayList<Segnalazione> listaSegnalazioni) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.bannato = bannato;
        this.listaRecensioni = listaRecensioni;
        this.listaMiPiace = listaMiPiace;
        this.listaSegnalazioni = listaSegnalazioni;
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

    public ArrayList<Recensione> getListaRecensioni() {
        return listaRecensioni;
    }

    public void setListaRecensioni(ArrayList<Recensione> listaRecensioni) {
        this.listaRecensioni = listaRecensioni;
    }

    public ArrayList<MiPiace> getListaMiPiace() {
        return listaMiPiace;
    }

    public void setListaMiPiace(ArrayList<MiPiace> listaMiPiace) {
        this.listaMiPiace = listaMiPiace;
    }

    public ArrayList<Segnalazione> getListaSegnalazioni() {
        return listaSegnalazioni;
    }

    public void setListaSegnalazioni(ArrayList<Segnalazione> listaSegnalazioni) {
        this.listaSegnalazioni = listaSegnalazioni;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bannato=" + bannato +
                ", listaRecensioni=" + listaRecensioni +
                ", listaMiPiace=" + listaMiPiace +
                ", listaSegnalazioni=" + listaSegnalazioni +
                '}';
    }
}


package com.cinehub.cinehub.recensione;

import com.cinehub.cinehub.mipiace.MiPiace;
import com.cinehub.cinehub.segnalazione.Segnalazione;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class Recensione {
    private UUID id;
    private String contenuto;
    private ArrayList<Recensione> listaRisposte;
    private Timestamp createdAt;
    private double punteggio;
    private ArrayList<MiPiace> listaMiPiace;
    private ArrayList<Segnalazione> listaSegnalazioni;

    public Recensione(UUID id, String contenuto, ArrayList<Recensione> listaRisposte, Timestamp createdAt, double punteggio, ArrayList<MiPiace> listaMiPiace, ArrayList<Segnalazione> listaSegnalazioni) {
        this.id = id;
        this.contenuto = contenuto;
        this.listaRisposte = listaRisposte;
        this.createdAt = createdAt;
        this.punteggio = punteggio;
        this.listaMiPiace = listaMiPiace;
        this.listaSegnalazioni = listaSegnalazioni;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public ArrayList<Recensione> getListaRisposte() {
        return listaRisposte;
    }

    public void setListaRisposte(ArrayList<Recensione> listaRisposte) {
        this.listaRisposte = listaRisposte;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public double getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(double punteggio) {
        this.punteggio = punteggio;
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
                "id=" + id +
                ", contenuto='" + contenuto + '\'' +
                ", listaRisposte=" + listaRisposte +
                ", createdAt=" + createdAt +
                ", punteggio=" + punteggio +
                ", listaMiPiace=" + listaMiPiace +
                ", listaSegnalazioni=" + listaSegnalazioni +
                '}';
    }
}

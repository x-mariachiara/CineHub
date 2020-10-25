package com.cinehub.cinehub.gestioneRecensione;

import com.cinehub.cinehub.gestioneMiPiace.MiPiace;
import com.cinehub.cinehub.gestioneSegnalazione.Segnalazione;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Recensione {
    private String contenuto;
    private ArrayList<Recensione> listaRisposte;
    private Timestamp data;
    private double punteggio;
    private ArrayList<MiPiace> listaMiPiace;
    private ArrayList<Segnalazione> listaSegnalazioni;

    public Recensione(String contenuto, ArrayList<Recensione> listaRisposte, Timestamp data, double punteggio, ArrayList<MiPiace> listaMiPiace, ArrayList<Segnalazione> listaSegnalazioni) {
        this.contenuto = contenuto;
        this.listaRisposte = listaRisposte;
        this.data = data;
        this.punteggio = punteggio;
        this.listaMiPiace = listaMiPiace;
        this.listaSegnalazioni = listaSegnalazioni;
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

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
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
                "contenuto='" + contenuto + '\'' +
                ", listaRisposte=" + listaRisposte +
                ", data=" + data +
                ", punteggio=" + punteggio +
                ", listaMiPiace=" + listaMiPiace +
                ", listaSegnalazioni=" + listaSegnalazioni +
                '}';
    }
}

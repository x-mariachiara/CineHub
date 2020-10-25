package com.cinehub.cinehub.gestioneMedia;

import com.cinehub.cinehub.cast.Cast;
import com.cinehub.cinehub.genere.Genere;
import com.cinehub.cinehub.stagione.Stagione;

import java.util.ArrayList;

public class SerieTv extends Media {
    private ArrayList<Stagione> listaStagioni;
    private double mediaVotiPuntate;


    public SerieTv(String titolo, int annoUscita, String trama, String linkTrailer, String linkLocandina, ArrayList<Genere> listageneri, ArrayList<Cast> listaCast, ArrayList<Stagione> listaStagioni, double mediaVotiPuntate) {
        super(titolo, annoUscita, trama, linkTrailer, linkLocandina, listageneri, listaCast);
        this.listaStagioni = listaStagioni;
        this.mediaVotiPuntate = mediaVotiPuntate;
    }

    public ArrayList<Stagione> getListaStagioni() {
        return listaStagioni;
    }

    public void setListaStagioni(ArrayList<Stagione> listaStagioni) {
        this.listaStagioni = listaStagioni;
    }

    public double getMediaVotiPuntate() {
        return mediaVotiPuntate;
    }

    public void setMediaVotiPuntate(double mediaVotiPuntate) {
        this.mediaVotiPuntate = mediaVotiPuntate;
    }

    @Override
    public String toString() {
        return super.toString() + this.getClass().getSimpleName() + "{lista stagioni = " + listaStagioni + "}" + "media voti puntate = " + mediaVotiPuntate + "}";
    }
}

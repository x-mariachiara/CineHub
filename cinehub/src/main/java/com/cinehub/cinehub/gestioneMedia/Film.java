package com.cinehub.cinehub.gestioneMedia;

import com.cinehub.cinehub.cast.Cast;
import com.cinehub.cinehub.genere.Genere;
import com.cinehub.cinehub.recensione.Recensione;

import java.util.ArrayList;

public class Film extends Media {
    private double voto;
    private ArrayList<Recensione> listaRecensioni;


    public Film(String titolo, int annoUscita, String trama, String linkTrailer, String linkLocandina, ArrayList<Genere> listageneri, ArrayList<Cast> listaCast, double voto, ArrayList<Recensione> listaRecensioni) {
        super(titolo, annoUscita, trama, linkTrailer, linkLocandina, listageneri, listaCast);
        this.voto = voto;
        this.listaRecensioni = listaRecensioni;
    }

    public double getVoto() {
        return voto;
    }

    public void setVoto(double voto) {
        this.voto = voto;
    }

    public ArrayList<Recensione> getListaRecensioni() {
        return listaRecensioni;
    }

    public void setListaRecensioni(ArrayList<Recensione> listaRecensioni) {
        this.listaRecensioni = listaRecensioni;
    }

    @Override
    public String toString() {
        return super.toString() + this.getClass().getSimpleName() + "{voto = " + voto + "}" + "{lista recensioni = " + listaRecensioni;
    }
}

package com.cinehub.cinehub.gestioneMedia;

import com.cinehub.cinehub.gesioneCast.Cast;
import com.cinehub.cinehub.gesioneGenere.Genere;

import java.util.ArrayList;

public class Media {
    private String titolo;
    private int annoUscita;
    private String trama;
    private String linkTrailer;
    private String linkLocandina;
    private ArrayList<Genere> listageneri;
    private ArrayList<Cast> listaCast;

    public Media(String titolo, int annoUscita, String trama, String linkTrailer, String linkLocandina, ArrayList<Genere> listageneri, ArrayList<Cast> listaCast) {
        this.titolo = titolo;
        this.annoUscita = annoUscita;
        this.trama = trama;
        this.linkTrailer = linkTrailer;
        this.linkLocandina = linkLocandina;
        this.listageneri = listageneri;
        this.listaCast = listaCast;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getAnnoUscita() {
        return annoUscita;
    }

    public void setAnnoUscita(int annoUscita) {
        this.annoUscita = annoUscita;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public String getLinkTrailer() {
        return linkTrailer;
    }

    public void setLinkTrailer(String linkTrailer) {
        this.linkTrailer = linkTrailer;
    }

    public String getLinkLocandina() {
        return linkLocandina;
    }

    public void setLinkLocandina(String linkLocandina) {
        this.linkLocandina = linkLocandina;
    }

    public ArrayList<Genere> getListageneri() {
        return listageneri;
    }

    public void setListageneri(ArrayList<Genere> listageneri) {
        this.listageneri = listageneri;
    }

    public ArrayList<Cast> getListaCast() {
        return listaCast;
    }

    public void setListaCast(ArrayList<Cast> listaCast) {
        this.listaCast = listaCast;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "titolo='" + titolo + '\'' +
                ", annoUscita=" + annoUscita +
                ", trama='" + trama + '\'' +
                ", linkTrailer='" + linkTrailer + '\'' +
                ", linkLocandina='" + linkLocandina + '\'' +
                ", listageneri=" + listageneri +
                ", listaCast=" + listaCast +
                '}';
    }
}

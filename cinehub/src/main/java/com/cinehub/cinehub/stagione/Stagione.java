package com.cinehub.cinehub.stagione;

import com.cinehub.cinehub.puntata.Puntata;

import java.util.ArrayList;

public class Stagione {
    private int numero;
    private ArrayList<Puntata> listaEpisodi;

    public Stagione(int numero, ArrayList<Puntata> listaEpisodi) {
        this.numero = numero;
        this.listaEpisodi = listaEpisodi;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ArrayList<Puntata> getListaEpisodi() {
        return listaEpisodi;
    }

    public void setListaEpisodi(ArrayList<Puntata> listaEpisodi) {
        this.listaEpisodi = listaEpisodi;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "numero=" + numero +
                ", listaEpisodi=" + listaEpisodi +
                '}';
    }
}

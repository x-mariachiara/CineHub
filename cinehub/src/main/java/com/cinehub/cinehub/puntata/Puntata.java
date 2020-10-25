package com.cinehub.cinehub.puntata;

import com.cinehub.cinehub.recensione.Recensione;
import com.cinehub.cinehub.utils.Recensible;

import java.util.ArrayList;
import java.util.UUID;

public class Puntata implements Recensible {
    private UUID id;
    private String titolo;
    private int numero;
    private String sinossi;
    private double voto;
    private ArrayList<Recensione> listaRecensioni;

    public Puntata(UUID id, String titolo, int numero, String sinossi, double voto, ArrayList<Recensione> listaRecensioni) {
        this.id = id;
        this.titolo = titolo;
        this.numero = numero;
        this.sinossi = sinossi;
        this.voto = voto;
        this.listaRecensioni = listaRecensioni;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getSinossi() {
        return sinossi;
    }

    public void setSinossi(String sinossi) {
        this.sinossi = sinossi;
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
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", numero=" + numero +
                ", sinossi='" + sinossi + '\'' +
                ", voto=" + voto +
                ", listaRecensioni=" + listaRecensioni +
                '}';
    }
}

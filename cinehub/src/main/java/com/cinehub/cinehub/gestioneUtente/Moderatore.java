package com.cinehub.cinehub.gestioneUtente;

import com.cinehub.cinehub.mipiace.MiPiace;
import com.cinehub.cinehub.recensione.Recensione;
import com.cinehub.cinehub.segnalazione.Segnalazione;

import java.util.ArrayList;

public class Moderatore extends Utente {
    private String tipo;

    public Moderatore(String email, String username, String password, boolean bannato, ArrayList<Recensione> listaRecensioni, ArrayList<MiPiace> listaMiPiace, ArrayList<Segnalazione> listaSegnalazioni, String tipo) {
        super(email, username, password, bannato, listaRecensioni, listaMiPiace, listaSegnalazioni);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return super.toString() + this.getClass().getSimpleName() + "{tipo = " + tipo + "}";
    }
}

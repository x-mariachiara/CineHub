package com.cinehub.cinehub.gestioneUtente;

import com.cinehub.cinehub.mipiace.MiPiace;
import com.cinehub.cinehub.recensione.Recensione;
import com.cinehub.cinehub.segnalazione.Segnalazione;

import java.util.ArrayList;

public class ResponsabileCatalogo extends Utente {

    public ResponsabileCatalogo(String email, String username, String password, boolean bannato, ArrayList<Recensione> listaRecensioni, ArrayList<MiPiace> listaMiPiace, ArrayList<Segnalazione> listaSegnalazioni) {
        super(email, username, password, bannato, listaRecensioni, listaMiPiace, listaSegnalazioni);
    }
}

package com.cinehub.cinehub.gestioneUtente;

import com.cinehub.cinehub.gestioneMiPiace.MiPiace;
import com.cinehub.cinehub.gestioneRecensione.Recensione;
import com.cinehub.cinehub.gestioneSegnalazione.Segnalazione;

import java.util.ArrayList;

public class ResponsabileCatalogo extends Utente {

    public ResponsabileCatalogo(String email, String username, String password, boolean bannato, ArrayList<Recensione> listaRecensioni, ArrayList<MiPiace> listaMiPiace, ArrayList<Segnalazione> listaSegnalazioni) {
        super(email, username, password, bannato, listaRecensioni, listaMiPiace, listaSegnalazioni);
    }
}

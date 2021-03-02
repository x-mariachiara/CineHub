package com.unisa.cinehub.data;

import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.data.entity.Utente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UtenteDTO implements Serializable {
    private Utente utente;
    private List<Recensione> recensioni = new ArrayList<>();

    public UtenteDTO() {
    }

    public UtenteDTO(Utente utente, List<Recensione> recensioni) {
        this.utente = utente;
        this.recensioni = recensioni;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Recensione> recensioni) {
        this.recensioni = recensioni;
    }
}

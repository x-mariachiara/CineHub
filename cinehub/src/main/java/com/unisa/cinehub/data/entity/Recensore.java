package com.unisa.cinehub.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
public class Recensore extends Utente {

    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    private List<Recensione> listaRecensioni = new ArrayList<>();

    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    private List<Segnalazione> listaSegnalazioni = new ArrayList<>();

    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    private List<MiPiace> listaMiPiace = new ArrayList<>();

    public Recensore() {
    }

    public Recensore(String email, String nome, String cognome, LocalDate dataNascita, String username, String password, Boolean isBannato, Boolean isActive) {
        super(email, nome, cognome, dataNascita, username, password, isBannato, isActive);
    }

    public List<Recensione> getListaRecensioni() {
        return listaRecensioni;
    }

    public void setListaRecensioni(List<Recensione> listaRecensioni) {
        this.listaRecensioni = listaRecensioni;
    }

    public List<Segnalazione> getListaSegnalazioni() {
        return listaSegnalazioni;
    }

    public void setListaSegnalazioni(List<Segnalazione> listaSegnalazioni) {
        this.listaSegnalazioni = listaSegnalazioni;
    }

    public List<MiPiace> getListaMiPiace() {
        return listaMiPiace;
    }

    public void setListaMiPiace(List<MiPiace> listaMiPiace) {
        this.listaMiPiace = listaMiPiace;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

package com.unisa.cinehub.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;

@Entity
public class Recensore extends Utente {

    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    private Collection<Recensione> listaRecensioni;
    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    private Collection<Segnalazione> listaSegnalazioni;
    @OneToMany(cascade = {
            CascadeType.REMOVE
    })
    private Collection<MiPiace> listaMiPiace;

    public Recensore() {
    }

    public Recensore(String email, String nome, String cognome, Date dataNascita, String username, String password, Boolean isBannato, Boolean isActive) {
        super(email, nome, cognome, dataNascita, username, password, isBannato, isActive);
    }

    public Collection<Recensione> getListaRecensioni() {
        return listaRecensioni;
    }

    public void setListaRecensioni(Collection<Recensione> listaRecensioni) {
        this.listaRecensioni = listaRecensioni;
    }

    public Collection<Segnalazione> getListaSegnalazioni() {
        return listaSegnalazioni;
    }

    public void setListaSegnalazioni(Collection<Segnalazione> listaSegnalazioni) {
        this.listaSegnalazioni = listaSegnalazioni;
    }

    public Collection<MiPiace> getListaMiPiace() {
        return listaMiPiace;
    }

    public void setListaMiPiace(Collection<MiPiace> listaMiPiace) {
        this.listaMiPiace = listaMiPiace;
    }

    @Override
    public String toString() {
        return super.toString() + "{" + "listaRecensioni=" + listaRecensioni +
                ", listaSegnalazioni=" + listaSegnalazioni +
                ", listaMiPiace=" + listaMiPiace + "}";
    }
}

package com.unisa.cinehub.data.entity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Moderatore extends Utente {

    private enum Tipo {MODACCOUNT, MODCOMMENTI};
    private Tipo tipo;

    public Moderatore() {
    }

    public Moderatore(String email, String nome, String cognome, Date dataNascita, String username, String password, Boolean isBannato, Boolean isActive, Tipo tipo) {
        super(email, nome, cognome, dataNascita, username, password, isBannato, isActive);
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return  super.toString() +
                "tipo=" + tipo +
                '}';
    }
}

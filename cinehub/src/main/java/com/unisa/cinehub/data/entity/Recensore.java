package com.unisa.cinehub.data.entity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Recensore extends Utente {

    public Recensore() {
    }

    public Recensore(String email, String nome, String cognome, Date dataNascita, String username, String password, Boolean isBannato, Boolean isActive) {
        super(email, nome, cognome, dataNascita, username, password, isBannato, isActive);
    }
}

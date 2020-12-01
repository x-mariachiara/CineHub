package com.unisa.cinehub.data.entity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ResponsabileCatalogo extends Utente {

    public ResponsabileCatalogo() {
    }

    public ResponsabileCatalogo(String email, String nome, String cognome, Date dataNascita, String username, String password, Boolean isBannato, Boolean isActive) {
        super(email, nome, cognome, dataNascita, username, password, isBannato, isActive);
    }
}

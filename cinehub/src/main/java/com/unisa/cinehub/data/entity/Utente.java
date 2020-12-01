package com.unisa.cinehub.data.entity;

import com.unisa.cinehub.data.AbstractEntity;

import java.util.Date;

public abstract class Utente extends AbstractEntity {

    private String email;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String username;
    private String password;
    private Boolean isBannato;
    private Boolean isActive;

    public Utente() {
    }

    public Utente(String email, String nome, String cognome, Date dataNascita, String username, String password, Boolean isBannato, Boolean isActive) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.username = username;
        this.password = password;
        this.isBannato = isBannato;
        this.isActive = isActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBannato() {
        return isBannato;
    }

    public void setBannato(Boolean bannato) {
        isBannato = bannato;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isBannato=" + isBannato +
                ", isActive=" + isActive +
                '}';
    }
}

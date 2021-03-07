package com.unisa.cinehub.data;

import com.opencsv.bean.CsvBindByPosition;

import java.time.LocalDate;

public class UtenteDataset {

    @CsvBindByPosition(position = 0)
    private String email;

    @CsvBindByPosition(position = 1)
    private String nome;

    @CsvBindByPosition(position = 2)
    private String cognome;

    @CsvBindByPosition(position = 3)
    private String sesso;

    @CsvBindByPosition(position = 4)
    private String hobby;

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

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "UtenteDataset{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", sesso='" + sesso + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}

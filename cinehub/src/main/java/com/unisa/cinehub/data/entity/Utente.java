package com.unisa.cinehub.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
//@MappedSuperclass

public abstract class Utente implements Cloneable{

    @Id
    @Email
    private String email;
    @NotNull
    @NotBlank(message = "Il campo non deve essere vuoto")
    @Pattern(regexp = "[A-Za-z.\\s]{2,50}", message = "Può contenere solo caratteri Alfabetici, punti o spazi")
    private String nome;
    @NotNull
    @NotBlank(message = "Il campo non deve essere vuoto")
    @Pattern(regexp = "[A-Za-z.\\s]{2,50}", message = "Può contenere solo caratteri Alfabetici, punti o spazi")
    private String cognome;
    @NotNull
    private LocalDate dataNascita;
    @NotNull
    private Sesso sesso;
    @NotNull
    private Hobby hobby;
    @Column(unique = true)
    @Pattern(regexp = "^\\S+${2,15}", message = "Lo username non può contenere spazi")
    @NotBlank(message = "Il campo non deve essere vuoto")
    private String username;
    @NotNull
    @Pattern(regexp = ".*(^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\d\\D]{8,})", message = "La password deve contenere almeno: una maiuscola, una minuscola e un numero e deve essere lunga almeno otto caratteri")
    @NotBlank(message = "Il campo non deve essere vuoto")
    private String password;
    @NotNull
    private Boolean isBannato;
    @NotNull
    private Boolean isActive;

    public Utente() {
    }

    public Utente(String email, String nome, String cognome, LocalDate dataNascita, String username, String password, Boolean isBannato, Boolean isActive) {
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

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
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

    public Sesso getSesso() {
        return sesso;
    }

    public void setSesso(Sesso sesso) {
        this.sesso = sesso;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente)) return false;
        Utente utente = (Utente) o;
        return getEmail().equals(utente.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", username='" + username + '\'' +
                ", sesso='" + sesso + '\'' +
                ", password='" + password + '\'' +
                ", isBannato=" + isBannato +
                ", isActive=" + isActive +
                ", hobby='" + hobby + '\'' +
                '}';
    }

    public enum Sesso {UOMO, DONNA, PREFERISCO_NON_RISPONDERE}

    public enum Hobby {
        BALLARE, BRICOLAGE, CANTARE, COLLEZZIONISMO, CUCINARE,
        DIPINGERE, DISEGNARE, FALEGNAMERIA, FOTOGRAFIA, GIARDINAGGIO,
        GIOCARE_AI_VIDEOGAMES, LEGGERE, SPORT, SUONARE, VIAGGIARE
    }

    public static Set<Sesso> getTuttiSessi() {
        Set<Sesso> sessi = new HashSet<>();
        Arrays.stream(Sesso.values()).forEach(nomeSesso -> {
            sessi.add(nomeSesso);
        });
        return sessi;
    }

    public static Set<Hobby> getTuttiHobby() {
        Set<Hobby> hobby = new HashSet<>();
        Arrays.stream(Hobby.values()).forEach(nomeHobby -> {
            hobby.add(nomeHobby);
        });
        return hobby;
    }
}

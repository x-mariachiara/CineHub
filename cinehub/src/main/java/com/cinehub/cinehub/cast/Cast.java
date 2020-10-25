package com.cinehub.cinehub.cast;

import java.util.UUID;

public class Cast {
    private UUID id;
    private String nomeCompeto;
    private String ruolo;

    public Cast(UUID id, String nomeCompeto, String ruolo) {
        this.id = id;
        this.nomeCompeto = nomeCompeto;
        this.ruolo = ruolo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeCompeto() {
        return nomeCompeto;
    }

    public void setNomeCompeto(String nomeCompeto) {
        this.nomeCompeto = nomeCompeto;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", nomeCompeto='" + nomeCompeto + '\'' +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}

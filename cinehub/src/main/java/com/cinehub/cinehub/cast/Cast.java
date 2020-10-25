package com.cinehub.cinehub.cast;

public class Cast {
    private String nomeCompeto;
    private String ruolo;

    public Cast(String nomeCompeto, String ruolo) {
        this.nomeCompeto = nomeCompeto;
        this.ruolo = ruolo;
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
                "nomeCompeto='" + nomeCompeto + '\'' +
                ", ruolo='" + ruolo + '\'' +
                '}';
    }
}

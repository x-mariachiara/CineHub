package com.unisa.cinehub.data.entity;

import java.util.List;

public interface Recensibile {

    Double getMediaVoti();
    void calcolaMediaVoti();
    void aggiungiRecensione(Recensione recensione);
    void rimuoviRecensione(Recensione recensione);
    List<Recensione> getListaRecensioni();
}

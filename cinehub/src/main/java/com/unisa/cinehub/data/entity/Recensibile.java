package com.unisa.cinehub.data.entity;

import java.io.Serializable;
import java.util.List;


public interface Recensibile extends Serializable {

    Double getMediaVoti();
    void aggiungiRecensione(Recensione recensione);
    void rimuoviRecensione(Recensione recensione);
    List<Recensione> getListaRecensioni();
}

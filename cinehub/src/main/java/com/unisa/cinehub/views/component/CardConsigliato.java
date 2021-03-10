package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Film;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardConsigliato extends VerticalLayout {

    LocandinaComponent locandinaConsigliato;

    public CardConsigliato(Film film) {
        super();
        prepare(film);
    }

    private void prepare(Film film) {
        VerticalLayout v = new VerticalLayout();
        H2 titolo = new H2("Potrebbe piacerti");
        locandinaConsigliato = new LocandinaComponent(film);
        locandinaConsigliato.setSizeFull();
        v.add(titolo, locandinaConsigliato);
        setWidth("30%");
        add(v);
    }
}

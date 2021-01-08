package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Recensione;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RecensioneComponent  extends Div {

    public RecensioneComponent(Recensione recensione) {
        setClassName("recensione");
        Text username = new Text(recensione.getRecensore().getUsername());
        Paragraph contenuto = new Paragraph(recensione.getContenuto());
        Integer valutazione = recensione.getPunteggio();
        HorizontalLayout h = new HorizontalLayout(username, popcornHandler(valutazione));
        VerticalLayout v = new VerticalLayout(h, contenuto);
        add(v);
    }

    private Div popcornHandler(Integer voto) {
        Div d = new Div();
        d.setClassName("punteggio-film-div");
        for(int i = 0; i < voto; i++) {
            d.add(new Image("images/popcorn.png", "popcorn punteggio" + voto));
        }

        return d;
    }
}

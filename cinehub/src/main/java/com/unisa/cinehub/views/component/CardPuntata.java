package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.views.puntata.PuntataView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardPuntata extends FlexLayout {



    public CardPuntata(Puntata puntata) {
        setClassName("card-media");
        H3 h3 = new H3(puntata.getTitolo());
        Paragraph p = new Paragraph(puntata.getSinossi() + "...");
        Button b = new Button("Dettagli", e -> naviga(puntata));
        VerticalLayout v = new VerticalLayout();
        v.add(h3, p, b);
        setWidth("80%");
        setHeight("350px");;
        add(v);
    }

    private void naviga(Puntata puntata) {
            getUI().ifPresent(ui -> ui.navigate(PuntataView.class, puntata.puntataPath()));
    }
}
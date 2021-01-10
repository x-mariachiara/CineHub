package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Recensione;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

public class RecensioneComponent  extends Div {

    public RecensioneComponent(Recensione recensione) {
        setClassName("recensione");
        Paragraph username = new Paragraph(recensione.getRecensore().getUsername());
        username.getStyle().set("font-weight", "bold");
        Paragraph contenuto = new Paragraph(recensione.getContenuto());
        contenuto.setClassName("contenuto");
        Integer valutazione = recensione.getPunteggio();
        HorizontalLayout h = new HorizontalLayout(new Icon(USER), username, popcornHandler(valutazione), miPiace(), nonMiPiace());
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        VerticalLayout v = new VerticalLayout(h, contenuto, rispondi());
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

    private Button rispondi(){
        Button b = new Button("Rispondi", buttonClickEvent -> {

        });
        b.setIcon(new Icon(COMMENT_O));
        return b;
    }

    private Button miPiace(){
        //TODO aggiungere logica: se l'utente loggato ha già messo mi piace caricare cuore pieno e aggiornare la lista mi piace
        Icon i = new Icon(THUMBS_UP_O);
        Button b = new Button(i, buttonClickEvent -> {
            System.out.println(buttonClickEvent.getSource().getIcon() + "\n" + buttonClickEvent.getSource().getIcon().equals(new Icon(THUMBS_UP_O)));
            if(buttonClickEvent.getSource().getIcon().equals(i)){
                System.out.println("funzio");
                buttonClickEvent.getSource().setIcon(new Icon(THUMBS_UP));
            } else {
                buttonClickEvent.getSource().setIcon(i);
            }
        });
        return b;
    }

    private Button nonMiPiace(){
        //TODO aggiungere logica: se l'utente loggato ha già messo mi piace caricare cuore pieno e aggiornare la lista mi piace
        Icon i = new Icon(THUMBS_DOWN_O);
        Button b = new Button(i, buttonClickEvent -> {
            if(buttonClickEvent.getSource().getIcon().equals(i)){
                buttonClickEvent.getSource().setIcon(new Icon(THUMBS_DOWN));
            } else {
                buttonClickEvent.getSource().setIcon(i);
            }
        });
        return b;
    }
}

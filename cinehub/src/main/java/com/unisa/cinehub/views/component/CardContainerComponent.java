package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Film;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class CardContainerComponent extends VerticalLayout {
    public CardContainerComponent(List<Film> film){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        //setWidth("80%");
        for(Film f : film) {
            add(new CardMedia(f));
        }
    }
}

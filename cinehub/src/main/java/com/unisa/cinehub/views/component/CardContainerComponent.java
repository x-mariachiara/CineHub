package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class CardContainerComponent extends VerticalLayout {

    public CardContainerComponent(List<Media> media){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        //setWidth("80%");
        for(Media m : media) {
            add(new CardMedia(m));
        }
    }
}

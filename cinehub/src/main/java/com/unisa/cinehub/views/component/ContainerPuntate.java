package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.entity.Puntata;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class ContainerPuntate extends VerticalLayout {

    public ContainerPuntate(List<Puntata> puntata){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        //setWidth("80%");
        for(Puntata m : puntata) {
            add(new CardPuntata(m));
        }
    }
}
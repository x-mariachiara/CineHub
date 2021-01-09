package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Media;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;

import java.util.List;

public class CardScrollContainer extends Div {

    public CardScrollContainer(List<Media> mediaList) {
        setWidth("70%");
        setHeight("30vh");
        setClassName("horizontal-scroll");
        for(Media m : mediaList) {
            add(new LocandinaComponent(m));
        }
    }

}

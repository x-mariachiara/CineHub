package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Media;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class CardScrollContainer extends VerticalLayout {

    private HorizontalLayout content;

    public CardScrollContainer(List<Media> mediaList, String titolo) {
        super();
        preparePanel(titolo);
        for(Media m : mediaList) {
            add(new LocandinaComponent(m));
        }
    }

    private void preparePanel(String titolo) {
        setId("card-scroll-container");
        getStyle().set("overflow", "auto");
        content = new HorizontalLayout();
        content.setWidth(null);
        content.setHeight("100%");
        super.add(new H2(titolo), content);
        setHeight("70%");
        setWidth("100%");

    }

    @Override
    public void add(Component... components){
        content.add(components);
    }


}

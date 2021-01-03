package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Media;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.awt.*;

public class CardMedia extends VerticalLayout{

    private Media media;

    public CardMedia(Media media) {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H3 titolo = new H3(media.getTitolo());
        Image locandina = new Image(media.getLinkLocandina(), "locandina");
        locandina.setId("locandina");
        locandina.setHeight("200px");
        locandina.setWidth("150px");
        Text sinossi = new Text(media.getSinossi());
        Button dettagliButton = new Button("Dettagli");

        HorizontalLayout h = new HorizontalLayout();
        VerticalLayout v = new VerticalLayout();
        v.add(titolo, sinossi, dettagliButton);
        h.add(locandina, v);
        add(h);
    }
}

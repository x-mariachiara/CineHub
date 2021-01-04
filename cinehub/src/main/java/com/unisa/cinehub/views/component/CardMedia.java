package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.views.film.InfoFilmView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.awt.*;

public class CardMedia extends FlexLayout {

    private Media media;

    public CardMedia(Media media) {
        setClassName("card-media");
        H3 h3 = new H3(media.getTitolo());
        Paragraph p = new Paragraph(media.getSinossi().substring(0, 40) + "...");
        Button b = new Button("Dettagli", buttonClickEvent -> {
            getUI().ifPresent(ui -> ui.navigate(InfoFilmView.class, media.getId()));
        });
        VerticalLayout v = new VerticalLayout();
        v.add(h3, p, b);
        Image l = new Image(media.getLinkLocandina(), "locandina" + media.getTitolo());
        Div d = new Div();
        d.setWidth("30%");
        d.add(l);

        setWidth("80%");
        setHeight("350px");

        setOrder(1, d);
        setOrder(2, v);
        add(d, v);
    }
}

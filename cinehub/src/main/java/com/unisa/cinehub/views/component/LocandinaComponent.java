package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.views.film.InfoFilmView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.awt.*;

public class LocandinaComponent extends VerticalLayout {

    public LocandinaComponent(Media media) {
        Image locandina = new Image(media.getLinkLocandina(), media.getTitolo());
        //H3 titolo = new H3(media.getTitolo());
        Div d = new Div(locandina);
        d.getStyle().set("margin", "0 auto");
        d.setWidth("80%");
        setClassName("locandina-home");
        addClickListener(e -> dettagliMedia(media));
        add(d);
    }

    private void dettagliMedia(Media media) {
        if(media instanceof Film) {
            getUI().ifPresent(ui -> ui.navigate(InfoFilmView.class, media.getId()));
        } else {
            //redirect serietv
        }

    }
}

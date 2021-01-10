package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.views.film.InfoFilmView;
import com.unisa.cinehub.views.serietv.InfoSerieTvView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.awt.*;

public class LocandinaComponent extends Image {

    public LocandinaComponent(Media media) {
        setClassName("locandina");
        setSrc(media.getLinkLocandina());
        setAlt(media.getTitolo());
        setWidth("22%");
        getStyle().set("cursor", "pointer");
        addClickListener(e -> dettagliMedia(media));
    }

    private void dettagliMedia(Media media) {
        if(media instanceof Film) {
            getUI().ifPresent(ui -> ui.navigate(InfoFilmView.class, media.getId()));
        } else {
            getUI().ifPresent(ui -> ui.navigate(InfoSerieTvView.class, media.getId()));
        }

    }
}

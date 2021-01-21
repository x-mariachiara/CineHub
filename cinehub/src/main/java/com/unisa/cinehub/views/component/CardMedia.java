package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.views.film.InfoFilmView;
import com.unisa.cinehub.views.serietv.InfoSerieTvView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.awt.*;
import java.text.DecimalFormat;

public class CardMedia extends FlexLayout {

    private Media media;

    public CardMedia(Media media) {
        setClassName("card-media");
        H3 h3 = new H3(media.getTitolo());
        int fineSin = media.getSinossi().length() - (media.getSinossi().length()/100*45);
        Paragraph p = new Paragraph(media.getSinossi().substring(0, fineSin) + "...");
        Paragraph annoUscita = new Paragraph("Anno Uscita: " + media.getAnnoUscita());
        Paragraph mediaVoti = new Paragraph(media.getMediaVoti() == 0 ? "Nessuna valutazione" : "Voto: " + new DecimalFormat("#.##").format(media.getMediaVoti()) + "/5");
        Button b = new Button("Dettagli", e -> naviga(media));
        b.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        VerticalLayout v = new VerticalLayout();
        v.add(h3, p, annoUscita, mediaVoti, b);
        Image l = new Image(media.getLinkLocandina(), "locandina" + media.getTitolo());
        Div d = new Div();
        d.setWidth("30%");
        d.add(l);


        setOrder(1, d);
        setOrder(2, v);
        add(d, v);
    }

    private void naviga(Media media) {
        if(media instanceof Film) {
            getUI().ifPresent(ui -> ui.navigate(InfoFilmView.class, media.getId()));
        } else {
            getUI().ifPresent(ui -> ui.navigate(InfoSerieTvView.class, media.getId()));
        }
    }
}

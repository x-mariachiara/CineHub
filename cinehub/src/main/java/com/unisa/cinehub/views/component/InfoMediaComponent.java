package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Media;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class InfoMediaComponent extends VerticalLayout {
    public InfoMediaComponent(Media media) {
        Image locandina = new Image(media.getLinkLocandina(), "locandina " + media.getTitolo());
        locandina.setClassName("locandina");
        HorizontalLayout h = new HorizontalLayout(locandina, info(media));
        h.setWidth("80%");
        VerticalLayout v = new VerticalLayout(h, trailer(media.getLinkTrailer()));
        setAlignItems(Alignment.CENTER);
        add(h, v);
    }

    private VerticalLayout info(Media media){
        VerticalLayout v = new VerticalLayout();
        H1 titolo = new H1(media.getTitolo());
        titolo.setClassName("titolo-media");
        titolo.setWidthFull();
        UnorderedList list = new UnorderedList();
        list.setClassName("lista-info");
        ListItem ap = new ListItem("Anno di produzione: " + media.getAnnoUscita());
        ListItem g = new ListItem("Genere: " + media.getGeneri());
        ListItem r = new ListItem("Regista: ");
        ListItem c = new ListItem("Cast: ");
        ListItem t = new ListItem("Trama:"+ "\n" + media.getSinossi());
        list.add(ap, g, r, c, t);

        v.add(titolo, list);
        return v;
    }

    private VerticalLayout trailer(String link){
        VerticalLayout v = new VerticalLayout();
        IFrame trailer = new IFrame(link);
        trailer.setWidth("60%");
        trailer.setHeight("450px");
        v.setAlignItems(Alignment.CENTER);
        v.setJustifyContentMode(JustifyContentMode.CENTER);
        H2 h2 = new H2("TRAILER");
        v.add(h2, trailer);
        trailer.setClassName("trailer-media");
        return v;
    }

}

package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Puntata;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class InfoPuntataComponent extends VerticalLayout {

    public InfoPuntataComponent(Puntata puntata){
       //Image locandina = new Image(media.getLinkLocandina(), "locandina " + media.getTitolo());
        //locandina.setClassName("locandina");
        HorizontalLayout h = new HorizontalLayout(info(puntata));
        h.setWidth("80%");
        setAlignItems(Alignment.CENTER);
        add(h);
    }

    private VerticalLayout info(Puntata puntata){
        VerticalLayout v = new VerticalLayout();
        H1 titolo = new H1(puntata.getTitolo());
        titolo.setClassName("titolo-media");
        titolo.setWidthFull();
        UnorderedList list = new UnorderedList();
        list.setClassName("lista-info");
        ListItem s = new ListItem(new Paragraph("Stagione "), new Text(puntata.getStagione().getNumeroStagione() + " "));
        ListItem np = new ListItem(new Paragraph("Numero Puntata: "), new Text(puntata.getNumeroPuntata() + " "));
        ListItem t = new ListItem(new Paragraph("Trama:"), new Text(puntata.getSinossi()));
        VerticalLayout ver = new VerticalLayout(titolo, s, np, t);
        ver.setWidth("50%");
        ver.setPadding(false);
        HorizontalLayout hor = new HorizontalLayout(ver, votoMedio((puntata.getMediaVoti())));
        hor.setAlignItems(Alignment.CENTER);
        list.setWidth("100%");
        list.add(hor);
        v.add(list);

        return v;
    }

    private VerticalLayout votoMedio(Double votoMedio) {
        Div d =new Div();
        Image i = new Image("images/popcornpieno.png", "votomedio");
        d.setClassName("contorno-popcorn");
        //d.getStyle().set("--p", votoMedio * 20+ "%");
        i.getStyle().set("--p", ((int) (100 - (votoMedio * 20))) + "%");
        d.add(i);
        H3 voto = new H3("Voto medio: " + votoMedio + "/5");
        VerticalLayout verticalLayout = new VerticalLayout(d, voto);
        verticalLayout.setAlignItems(Alignment.CENTER);
        return verticalLayout;
    }
}

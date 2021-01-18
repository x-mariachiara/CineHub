package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.awt.*;
import java.util.Collection;
import java.util.Set;

import static com.unisa.cinehub.data.entity.Ruolo.Tipo.REGISTA;

public class InfoMediaComponent extends VerticalLayout {
    public InfoMediaComponent(Media media) {
        Image locandina = new Image(media.getLinkLocandina(), "locandina " + media.getTitolo());
        locandina.setClassName("locandina");
        HorizontalLayout h = new HorizontalLayout(locandina, info(media));
        h.addClassName("horizontal-responsive");
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
        ListItem ap = new ListItem(new Paragraph("Anno di produzione: "), new Text(media.getAnnoUscita() + " "));
        ListItem g = new ListItem(new Paragraph("Genere: "), new Text(retrieveGeneri(media.getGeneri())));
        ListItem c = new ListItem(new Paragraph("Cast: "), new Text(retrieveCast(media.getRuoli())));
        ListItem t = new ListItem(new Paragraph("Trama:"), new Text(media.getSinossi()));
        if(media instanceof Film) {
            ListItem r = new ListItem(new Paragraph("Regista: "), new Text(retrieveRegista(media.getRuoli())));
            VerticalLayout ver = new VerticalLayout(ap, g, r, c, t);
            ver.setWidth("50%");
            ver.setPadding(false);
            HorizontalLayout hor = new HorizontalLayout(ver, votoMedio(((Film) media).getMediaVoti()));
            hor.setAlignItems(Alignment.CENTER);
            hor.setJustifyContentMode(JustifyContentMode.BETWEEN);
            list.add(hor);
        } else {
            VerticalLayout ver = new VerticalLayout(ap, g, c);
            ver.setWidth("50%");
            ver.setPadding(false);
            HorizontalLayout hor = new HorizontalLayout(ver, votoMedio(((SerieTv) media).getMediaVoti()));
            hor.setAlignItems(Alignment.CENTER);
            list.add(hor, t);
        }
        v.add(titolo, list);




        return v;
    }

    private Div votoMedio(Double votoMedio) {
        Div d =new Div();
        Image i = new Image("images/popcornpieno.png", "votomedio");
        d.setClassName("contorno-popcorn");
        //d.getStyle().set("--p", votoMedio * 20+ "%");
        i.getStyle().set("--p", ((int) (100 - (votoMedio * 20))) + "%");
        d.add(i);
        return d;
    }

    private String retrieveCast(Collection<Ruolo> ruoli) {
        String cast = "";
        for (Ruolo r : ruoli)  {
            if(r.getTipo() == REGISTA) continue;
            cast += r.getCast().getNome() + " " + r.getCast().getCognome() + ", ";
        }
        return !cast.isBlank() ?  cast.substring(0, cast.length() - 2) : "";
    }

    private String retrieveRegista(Collection<Ruolo> ruoli) {
        String regista = "";
        for (Ruolo r : ruoli)  {
            if(r.getTipo() == REGISTA) {
                regista += r.getCast().getNome() + " " + r.getCast().getCognome() + ", ";
            }
        }
        return !regista.isBlank() ?  regista.substring(0, regista.length() - 2) : "";
    }

    private String retrieveGeneri(Set<Genere> generi) {
        String toReturn = "";
        for(Genere g : generi) {
            toReturn += g.getNomeGenere().toString().toLowerCase() + ", ";
        }
        return toReturn.substring(0, toReturn.length() - 2);
    }

    private VerticalLayout trailer(String link){
        VerticalLayout v = new VerticalLayout();
        IFrame trailer = new IFrame(link);
        trailer.setSizeFull();
        v.setAlignItems(Alignment.CENTER);
        v.setJustifyContentMode(JustifyContentMode.CENTER);
        H2 h2 = new H2("TRAILER");
        v.add(h2, trailer);
        v.setWidth("100%");
        v.addClassName("div-trailer");
        trailer.setClassName("trailer-media");
        return v;
    }

}

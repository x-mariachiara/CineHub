package com.unisa.cinehub.views.risultati;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.views.component.CardContainerComponent;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Route(value = "risultati", layout = MainView.class)
@PageTitle("Risultati")
public class RisultatiRicercaView extends VerticalLayout {

    private List<Media> risultati = new ArrayList<>();
    private String titolo = "";
    private Set<Genere> generi = new HashSet<>();

    @Autowired
    private CatalogoControl catalogoControl;

    public RisultatiRicercaView() {
        addAttachListener(e -> prepare());
    }

    private void prepare() {
        VaadinSession session = getUI().get().getSession();
        titolo = (String) session.getAttribute("titolo");
        generi = (Set<Genere>) session.getAttribute("generi");

        if(!titolo.equals("") && !generi.isEmpty()) {
            add(new H2("Risultati ricerca \""+ titolo +"\""));
            String nomiGeneri = "";
            for(Genere g : generi) {
                nomiGeneri += g.getNomeGenere() + " ";
            }
            add(new H3("Generi: " + nomiGeneri.toLowerCase()));
            List<Media> risultatiTitolo = new ArrayList<>();
            risultatiTitolo.addAll(catalogoControl.searchFilmByTitle(titolo));
            risultatiTitolo.addAll(catalogoControl.searchSerieTvByTitle(titolo));

            List<Media> risultatiGeneri = new ArrayList<>();
            risultatiGeneri.addAll(catalogoControl.searchFilmByGenere(generi));
            try {
                risultatiGeneri.addAll(catalogoControl.searchSerieTVByGenere(generi));
            } catch (InvalidBeanException e) {
                Notification.show("Si è verificato un errore");
            }
            risultati.addAll(catalogoControl.findAllFilm());
            risultati.addAll(catalogoControl.findAllSerieTv());

            risultati.removeIf(m -> !risultatiGeneri.contains(m) || !risultatiTitolo.contains(m));
        } else if(!titolo.equals("") && generi.isEmpty()) {
            add(new H2("Risultati ricerca \""+ titolo +"\""));
            risultati.addAll(catalogoControl.searchFilmByTitle(titolo));
            risultati.addAll(catalogoControl.searchSerieTvByTitle(titolo));
        } else if(titolo.equals("") && !generi.isEmpty()) {
            String nomiGeneri = "";
            for(Genere g : generi) {
                nomiGeneri += g.getNomeGenere() + " ";
            }
            add(new H2("Ricerca per generi: " + nomiGeneri.toLowerCase()));
            risultati.addAll(catalogoControl.searchFilmByGenere(generi));
            try {
                risultati.addAll(catalogoControl.searchSerieTVByGenere(generi));
            } catch (InvalidBeanException e) {
                Notification.show("Si è verificato un errore");
            }
        } else {
            Notification.show("Si è verificato un errore");
        }

        add(new CardContainerComponent(risultati));
    }
}

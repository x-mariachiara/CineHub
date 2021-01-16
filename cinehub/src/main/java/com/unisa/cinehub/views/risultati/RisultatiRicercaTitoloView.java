package com.unisa.cinehub.views.risultati;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.views.component.CardContainerComponent;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Route(value = "risultati", layout = MainView.class)
@PageTitle("Risultati")
public class RisultatiRicercaTitoloView extends VerticalLayout implements HasUrlParameter<String> {

    private List<Media> risultati = new ArrayList<>();
    private String titolo = "NADA";

    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;

    public RisultatiRicercaTitoloView() {
        addAttachListener(e -> prepare());
    }

    private void prepare() {
        add(new H2("Risultati ricerca \""+ titolo +"\""));
        risultati.addAll(gestioneCatalogoControl.searchFilmByTitle(titolo));
        risultati.addAll(gestioneCatalogoControl.searchSerieTvByTitle(titolo));

        add(new CardContainerComponent(risultati));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String titolo) {
        Location location = beforeEvent.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters.getParameters();
        if(titolo == null) {
            this.titolo = parametersMap.get("titolo").get(0);
        }
        else this.titolo = titolo;
    }
}

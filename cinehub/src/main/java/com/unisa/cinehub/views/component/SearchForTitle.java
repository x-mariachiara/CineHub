package com.unisa.cinehub.views.component;

import com.unisa.cinehub.views.risultati.RisultatiRicercaTitoloView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouterLink;

import java.util.*;

public class SearchForTitle extends HorizontalLayout {

    private TextField titolo = new TextField("Titolo");
    private RouterLink gotToRisultati = new RouterLink("cerca", RisultatiRicercaTitoloView.class, "");
    private Map<String, List<String>> map = new Hashtable<>();


    public SearchForTitle() {
        titolo.setClearButtonVisible(true);
        titolo.setRequired(true);
        titolo.addValueChangeListener(e -> {
            List<String> param = new ArrayList<>();
            param.add(titolo.getValue());
            map.put("titolo", param);
            QueryParameters parameters = new QueryParameters(map);
            gotToRisultati.setQueryParameters(parameters);
        });

        gotToRisultati.getStyle().set("background-color", "smokewhite");
        gotToRisultati.getStyle().set("border-radius", "20px");
        setAlignItems(Alignment.CENTER);
        H4 fakeButton = new H4(gotToRisultati);
        fakeButton.addClassName("fake-button");
        add(titolo, fakeButton);
    }
}

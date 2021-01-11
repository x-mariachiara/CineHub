package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.Recensibile;
import com.unisa.cinehub.data.entity.Recensione;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class RecensioniSectionComponent extends VerticalLayout {

    public RecensioniSectionComponent(Recensibile recensibile, CatalogoControl catalogoControl) {
        RecensioneFormComponent dialog = new RecensioneFormComponent(recensibile, catalogoControl);
        Button button = new Button("Scrivi recensione", buttonClickEvent -> dialog.open());
        button.setClassName("addRec");
        Div recensioni = new Div();
        for(Recensione recensione : recensibile.getListaRecensioni()) {
            recensioni.add(new RecensioneComponent(recensione, catalogoControl));
        }
        add(button, recensioni);
    }


}

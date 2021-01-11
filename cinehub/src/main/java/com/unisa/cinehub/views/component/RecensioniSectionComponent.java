package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.Recensibile;
import com.unisa.cinehub.data.entity.Recensione;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RecensioniSectionComponent extends VerticalLayout {


    private GestioneCatalogoControl gestioneCatalogoControl;
    private Div recensioni;
    private Recensibile recensibile;
    private CatalogoControl catalogoControl;

    public RecensioniSectionComponent(Recensibile recensibile, CatalogoControl catalogoControl, GestioneCatalogoControl gestioneCatalogoControl) {
        recensioni = new Div();
        this.recensibile = recensibile;
        this.catalogoControl = catalogoControl;
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        addAttachListener(e -> prepare());
    }


    protected void prepare() {
        RecensioneFormComponent dialog = new RecensioneFormComponent(recensibile, catalogoControl);
        dialog.addListener(RecensioneFormComponent.SaveEvent.class, this::update);
        Button button = new Button("Scrivi recensione", buttonClickEvent -> {
            dialog.open();
        });
        button.setClassName("addRec");
        add(button);
        populateRecensioni();
    }

    public void update(RecensioneFormComponent.SaveEvent event) {
        populateRecensioni();
    }


    public void populateRecensioni() {
        recensioni.removeAll();
        if(recensibile instanceof Film){
            Film film = gestioneCatalogoControl.findFilmById(((Film) recensibile).getId());
            recensibile = (Recensibile) film;
        } else {
            Puntata puntata = gestioneCatalogoControl.findPuntataById(new Puntata.PuntataID(((Puntata) recensibile).getNumeroPuntata(), ((Puntata) recensibile).getStagioneId()));
            recensibile = (Recensibile) puntata;
        }
        for(Recensione recensione : recensibile.getListaRecensioni()) {
            recensioni.add(new RecensioneComponent(recensione, catalogoControl));
        }
        add(recensioni);
    }



}

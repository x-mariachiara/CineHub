package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.control.ModerazioneControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.Recensibile;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.security.SecurityUtils;
import com.unisa.cinehub.views.component.form.RecensioneFormComponent;
import com.unisa.cinehub.views.login.LoginView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class RecensioniSectionComponent extends VerticalLayout {


    private GestioneCatalogoControl gestioneCatalogoControl;
    private ModerazioneControl moderazioneControl;
    private Div recensioni;
    private Recensibile recensibile;
    private CatalogoControl catalogoControl;

    public RecensioniSectionComponent(Recensibile recensibile, CatalogoControl catalogoControl, GestioneCatalogoControl gestioneCatalogoControl, ModerazioneControl moderazioneControl) {
        recensioni = new Div();
        this.recensibile = recensibile;
        this.catalogoControl = catalogoControl;
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        this.moderazioneControl = moderazioneControl;
        addAttachListener(e -> prepare());
    }


    protected void prepare() {
        RecensioneFormComponent dialog = new RecensioneFormComponent(recensibile, catalogoControl);
        dialog.addListener(RecensioneFormComponent.SaveEvent.class, this::update);
        Button button = new Button("Scrivi recensione", buttonClickEvent -> {
            if(SecurityUtils.isUserLoggedIn()) {
                dialog.open();
            } else {
                getUI().ifPresent(ui -> ui.navigate(LoginView.class));
            }
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
        List<Recensione> listaRecensioni = gestioneCatalogoControl.findRecensioniByMiPiace(recensibile);
        for(Recensione recensione : listaRecensioni) {
            if(!recensione.getRecensore().getBannato()) {
                recensioni.add(new RecensioneComponent(recensione, catalogoControl, moderazioneControl));
            }
        }
        add(recensioni);
    }



}

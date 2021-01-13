package com.unisa.cinehub.views.user.moderatorerecensioni;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Recensione;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route("moderazionerecensioni/modera")
public class ModeraRecensioniView extends VerticalLayout {

    @Autowired
    GestioneCatalogoControl gestioneCatalogoControl;

    private Grid<Recensione> grid = new Grid<>(Recensione.class);
    private TextField filterText = new TextField();
    private ReviewDialog reviewDialog = new ReviewDialog(null);

    public ModeraRecensioniView(GestioneCatalogoControl gestioneCatalogoControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        prepareGrid();
        reviewDialog.addListener(ReviewDialog.DeleteEvent.class, this::deleteRecensione);
        add(filterText, grid);
        updateList();
    }

    private void prepareGrid() {
        grid.removeColumnByKey("createdAt");
        grid.removeColumnByKey("contenuto");
        grid.removeColumnByKey("recensore");
        grid.removeColumnByKey("listaMiPiace");
        grid.removeColumnByKey("listaSegnalazioni");
        grid.removeColumnByKey("listaRisposte");
        grid.removeColumnByKey("punteggio");
        grid.removeColumnByKey("film");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("puntata");
        grid.removeColumnByKey("padre");
        grid.addColumn(recensione -> {
           return recensione.getRecensore().getUsername();
        }).setHeader("Autore");
        grid.addColumn(recensione -> {
            return recensione.getId() + "";
        }).setHeader("Id Recensione");
        grid.addColumn(recensione -> {
            LocalDateTime date = recensione.getCreatedAt().toLocalDateTime();
            return  date.getDayOfMonth() + "/" + date.getMonth() + "/" + date.getYear();
        }).setHeader("Data Creazione");
        grid.addColumn(recensione -> {
            return recensione.getListaSegnalazioni().size();
        }).setHeader("Numero Segnalazioni");
        grid.addColumn(recensione -> {
            return recensione.getPadre() != null ? "Risposta" : "Recensione";
        }).setHeader("Tipologia");

        grid.asSingleSelect().addValueChangeListener(event -> reviewRecensione(event.getValue()));
    }

    private void reviewRecensione(Recensione recensione) {
        reviewDialog.setRecensione(recensione);
        reviewDialog.open();
    }

    private void deleteRecensione(ReviewDialog.DeleteEvent event) {
        System.out.println("LA BAMBAAA " + event.getRecensione());
        gestioneCatalogoControl.deleteRecensione(event.getRecensione());
        updateList();
    }

    private void updateList() {
        List<Recensione> recensioni = gestioneCatalogoControl.requestAllRecensioni();
        grid.setItems( recensioni);
    }


}

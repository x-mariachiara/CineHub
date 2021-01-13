package com.unisa.cinehub.views.user.moderatorerecensioni;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.control.ModerazioneControl;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.views.login.LoginView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;

@Route("moderazione/recensioni")
public class ModeraRecensioniView extends VerticalLayout {

    @Autowired
    GestioneCatalogoControl gestioneCatalogoControl;

    @Autowired
    ModerazioneControl moderazioneControl;

    private Grid<Recensione> grid = new Grid<>(Recensione.class);
    private TextField filterText = new TextField();
    private ReviewDialog reviewDialog = new ReviewDialog(null);

    public ModeraRecensioniView(GestioneCatalogoControl gestioneCatalogoControl, ModerazioneControl moderazioneControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        this.moderazioneControl = moderazioneControl;
        prepareGrid();
        reviewDialog.addListener(ReviewDialog.DeleteEvent.class, this::deleteRecensione);
        add(filterText, grid);
        updateList();
    }

    private void prepareGrid() {
        grid.removeAllColumns();
        grid.addColumn(recensione -> {
           return recensione.getRecensore().getUsername();
        }).setHeader("Autore");
        grid.addColumn(recensione -> {
            return recensione.getId() + "";
        }).setHeader("Id Recensione");
        grid.addColumn(recensione -> {
            return recensione.getCreatedAt().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        }).setHeader("Data Creazione");
        Grid.Column<Recensione> numeroSegnalazioni = grid.addColumn(recensione -> {
            return recensione.getListaSegnalazioni().size();
        }).setHeader("Numero Segnalazioni").setSortProperty("numeroSegnalazioni");
        grid.addColumn(recensione -> {
            return recensione.getPadre() != null ? "Risposta" : "Recensione";
        }).setHeader("Tipologia");
        GridSortOrder<Recensione> order = new GridSortOrder<>(numeroSegnalazioni, SortDirection.DESCENDING);
        grid.sort(Arrays.asList(order));
        grid.asSingleSelect().addValueChangeListener(event -> reviewRecensione(event.getValue()));
    }

    private void reviewRecensione(Recensione recensione) {
        reviewDialog.setRecensione(recensione);
        reviewDialog.open();
    }

    private void deleteRecensione(ReviewDialog.DeleteEvent event) {
        try {
            moderazioneControl.deleteRecensione(event.getRecensione());
        } catch (NotAuthorizedException e){
            getUI().ifPresent(ui -> ui.navigate(LoginView.class));
        }
        updateList();
    }

    private void updateList() {
        List<Recensione> recensioni = gestioneCatalogoControl.requestAllRecensioni();
        grid.setItems( recensioni);
    }


}

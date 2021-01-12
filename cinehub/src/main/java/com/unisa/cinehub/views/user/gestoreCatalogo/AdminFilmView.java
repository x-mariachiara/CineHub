package com.unisa.cinehub.views.user.gestoreCatalogo;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.entity.Ruolo;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;



@Route("gestionecatalogo/film")
public class AdminFilmView  extends VerticalLayout {
    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;

    private Grid<Film> grid = new Grid<>(Film.class);
    private TextField filterText = new TextField();
    private FilmForm form;

    public AdminFilmView(GestioneCatalogoControl gestioneCatalogoControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        setSizeFull();

        configureGrid();
        configureFilter();

        form = new FilmForm();

        Div contenuto = new Div(grid, form);
        contenuto.setSizeFull();

        add(filterText, contenuto);
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(gestioneCatalogoControl.findAllFilm());
    }

    private void configureFilter() {
        filterText.setPlaceholder("Cerca per nome...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> {
            updateList();
        });
    }

    private void configureGrid() {
        setSizeFull();
        grid.removeColumnByKey("listaRecensioni");
        grid.removeColumnByKey("sinossi");
        grid.removeColumnByKey("generi");
        grid.removeColumnByKey("ruoli");
        grid.removeColumnByKey("mediaVoti");
        grid.setColumns("id", "titolo", "annoUscita", "linkTrailer", "linkLocandina");
        grid.addColumn(film -> {
            String generi = "";
            for(Genere g : film.getGeneri()) {
                generi += g.getNomeGenere().toString().toLowerCase()+", ";
            }
            return generi.substring(0, generi.length() - 2);
        }).setHeader("Generi");
        grid.addColumn(film -> {
            String ruoli = "";
            for(Ruolo r : film.getRuoli()) {
                ruoli += r.getTipo().toString() + ": " + r.getCast().getNome() + " " + r.getCast().getCognome() + "\n";
            }
            return ruoli;
        }).setHeader("Ruoli");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editFilm(event.getValue()));
    }

    private void editFilm(Film value) {
        if(value == null) {
            closeEditor();
        } else {
            form.setFilm(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setFilm(null);
        form.setVisible(false);
        removeClassName("editing");
    }


}

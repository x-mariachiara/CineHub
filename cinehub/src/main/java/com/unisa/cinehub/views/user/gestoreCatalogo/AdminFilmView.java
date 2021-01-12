package com.unisa.cinehub.views.user.gestoreCatalogo;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Ruolo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;


@Route("gestionecatalogo/film")
public class AdminFilmView  extends VerticalLayout {
    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;

    private Grid<Film> grid = new Grid<>(Film.class);
    private TextField filterText = new TextField();
    private MediaForm form;
    private Boolean generiAggiunti = false;
    private Button addFilmButton = new Button("Aggiungi Film");
    private Collection<Ruolo> ruoliAggiunti;
    private Boolean newFilm = false;
    private Film filmSelezionato;

    public AdminFilmView(GestioneCatalogoControl gestioneCatalogoControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        setSizeFull();

        configureGrid();
        configureFilter();
        List<Cast> tuttoIlCast = gestioneCatalogoControl.findAllCast();


        form = new MediaForm(tuttoIlCast);
        form.addListener(MediaForm.SaveEvent.class, this::saveFilm);
        form.addListener(MediaForm.DeleteEvent.class, this::deleteFilm);
        form.addListener(MediaForm.AddGenereEvent.class, e -> generiAggiunti = true);
        form.addListener(MediaForm.AddRuoloEvent.class, e -> ruoliAggiunti = e.getRuoli());
        form.addListener(MediaForm.CloseEvent.class, e -> closeEditor());
        addFilmButton.addClickListener(click -> addFilm());
        Div contenuto = new Div(grid, form);
        contenuto.setSizeFull();
        HorizontalLayout hor = new HorizontalLayout(filterText, addFilmButton);
        add(hor, contenuto);
        updateList();
        closeEditor();
    }

    private void addFilm() {
        grid.asSingleSelect().clear();
        newFilm =true;
        editFilm(new Film());
    }

    private  void saveFilm(MediaForm.MediaFormEvent event) {
        Film daModificare = new Film(
                event.getMedia().getTitolo(),
                event.getMedia().getAnnoUscita(),
                event.getMedia().getSinossi(),
                event.getMedia().getLinkTrailer(),
                event.getMedia().getLinkLocandina());
        daModificare.setGeneri(event.getMedia().getGeneri());
        daModificare.setRuoli(event.getMedia().getRuoli());
        daModificare.setId(event.getMedia().getId());

        if(newFilm) {
            System.out.println("NUOVO FILM");
            daModificare.getGeneri().clear();
            daModificare.getRuoli().clear();
            daModificare = gestioneCatalogoControl.addFilm(daModificare);
            daModificare.setGeneri(event.getMedia().getGeneri());
            newFilm =false;
        }
        if(generiAggiunti) {
            System.out.println("Aggiunta generi a: " + daModificare.getId() + " " + daModificare.getTitolo() + "\n\t\tI Generi sono: " + daModificare.getGeneri());
            gestioneCatalogoControl.addGeneriFilm(daModificare.getGeneri(), daModificare.getId());
            generiAggiunti = false;
        }
        if(ruoliAggiunti != null) {
            System.out.println("GIANFRANCO " + ruoliAggiunti + "\nRuoli film Selezionati: " + filmSelezionato.getRuoli());

            for(Ruolo ruolo : ruoliAggiunti) {
                if(!filmSelezionato.getRuoli().contains(ruolo)) {
                    System.out.println("Ruolo non già presente: " + ruolo.getTipo() + " " + ruolo.getCast().getCognome()+ " " + ruolo.getCast().getNome());
                    ruolo.setMedia(daModificare);
                    gestioneCatalogoControl.addRuolo(ruolo, ruolo.getCastId(), daModificare.getId());
                }
            }
            event.getMedia().setRuoli(ruoliAggiunti);
        }
        gestioneCatalogoControl.addFilm(daModificare);
        updateList();
        closeEditor();
    }

    private void deleteFilm(MediaForm.DeleteEvent event) {
        gestioneCatalogoControl.removeFilm(event.getMedia().getId());
        updateList();
        closeEditor();
    }




    private void updateList() {
        grid.setItems(filterText.isEmpty() ? gestioneCatalogoControl.findAllFilm() : gestioneCatalogoControl.searchFilmByTitle(filterText.getValue()));
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

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editFilm(event.getValue()));
    }

    private void editFilm(Film value) {
        if(value == null) {
            closeEditor();
        } else {
            filmSelezionato =value;
            form.setMedia(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setMedia(null);
        form.setVisible(false);
        removeClassName("editing");
    }



}

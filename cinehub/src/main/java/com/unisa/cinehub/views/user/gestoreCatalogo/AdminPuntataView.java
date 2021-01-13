package com.unisa.cinehub.views.user.gestoreCatalogo;

import com.helger.commons.state.ICloseable;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;



@Route("gestionecatalogo/puntata")
public class AdminPuntataView extends VerticalLayout {

    @Autowired
    GestioneCatalogoControl gestioneCatalogoControl;

    private Grid<Puntata> grid = new Grid<>(Puntata.class);
    private TextField filterText = new TextField();
    private PuntataForm form;
    private Button addPuntataButton = new Button("Aggiungi Puntata");
    private Boolean newPuntata = false;
    private Puntata puntataSelezionata;

    public AdminPuntataView(GestioneCatalogoControl gestioneCatalogoControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureFilter();
        addPuntataButton.addClickListener(e -> addPuntata());
        form = new PuntataForm(gestioneCatalogoControl.findAllSerieTv());
        form.addListener(PuntataForm.SaveEvent.class, this::savePuntata);
        form.addListener(PuntataForm.DeleteEvent.class, this::deletePuntata);
        form.addListener(PuntataForm.CloseEvent.class, e -> closeEditor());
        Div contenuto = new Div(grid, form);
        contenuto.addClassName("content");
        contenuto.setSizeFull();
        HorizontalLayout hor = new HorizontalLayout(filterText, addPuntataButton);
        hor.setClassName("toolbar");
        add(hor, contenuto);
        updateList();
        closeEditor();
    }

    private void addPuntata() {
        grid.asSingleSelect().clear();
        newPuntata = true;
        editPuntata(new Puntata());
    }

    private void deletePuntata(PuntataForm.DeleteEvent event) {
        gestioneCatalogoControl.removePuntata(new Puntata.PuntataID(event.getPuntata().getNumeroPuntata(), event.getPuntata().getStagioneId()));
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(filterText.isEmpty() ? gestioneCatalogoControl.findAllPuntate() : gestioneCatalogoControl.findPuntataByTitle(filterText.getValue()));
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
        addClassName("configure-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("sinossi");
        grid.removeColumnByKey("mediaVoti");
        grid.removeColumnByKey("stagione");

        grid.setColumns("titolo", "numeroPuntata");
        grid.addColumn(puntata -> {
            Stagione stagione = puntata.getStagione();
            return stagione != null ? "stagione: " + stagione.getNumeroStagione() : "-";
        }).setHeader("Stagione");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editPuntata(event.getValue()));
    }

    private void editPuntata(Puntata value) {
        if(value == null) {
            closeEditor();
        } else {
            puntataSelezionata = value;
            form.setPuntata(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setPuntata(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void savePuntata(PuntataForm.SaveEvent event) {
        System.out.println("puntata: " + event.getPuntata() + "\nserie tv id: " + event.getSerieTvId() + "\nnumero stagione: " + event.getNumeroStagione());
        if (newPuntata) {
            gestioneCatalogoControl.addPuntata(event.getPuntata(), event.getSerieTvId(), event.getNumeroStagione());
            newPuntata = false;
        } else {
            gestioneCatalogoControl.updatePuntata(event.getPuntata());
        }
        updateList();
        closeEditor();
    }

}

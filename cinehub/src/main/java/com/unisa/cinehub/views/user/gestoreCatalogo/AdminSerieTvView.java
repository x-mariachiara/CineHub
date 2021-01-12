package com.unisa.cinehub.views.user.gestoreCatalogo;


import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.data.entity.SerieTv;
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

@Route("gestionecatalogo/serietv")
public class AdminSerieTvView extends VerticalLayout {
    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;

    private Grid<SerieTv> grid = new Grid<>(SerieTv.class);
    private TextField filterText = new TextField();
    private MediaForm form;
    private Boolean generiAggiunti = false;
    private Button addSerieTvButton = new Button("Aggiungi SerieTv");
    private Collection<Ruolo> ruoliAggiunti;
    private Boolean newSerieTv = false;
    private SerieTv serieTvSelezionato;

    public AdminSerieTvView(GestioneCatalogoControl gestioneCatalogoControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        setSizeFull();

        configureGrid();
        configureFilter();
        List<Cast> tuttoIlCast = gestioneCatalogoControl.findAllCast();


        form = new MediaForm(tuttoIlCast);
        form.addListener(MediaForm.SaveEvent.class, this::saveSerieTv);
        form.addListener(MediaForm.DeleteEvent.class, this::deleteSerieTv);
        form.addListener(MediaForm.AddGenereEvent.class, e -> generiAggiunti = true);
        form.addListener(MediaForm.AddRuoloEvent.class, e -> ruoliAggiunti = e.getRuoli());
        form.addListener(MediaForm.CloseEvent.class, e -> closeEditor());
        addSerieTvButton.addClickListener(click -> addSerieTv());
        Div contenuto = new Div(grid, form);
        contenuto.setSizeFull();
        HorizontalLayout hor = new HorizontalLayout(filterText, addSerieTvButton);
        add(hor, contenuto);
        updateList();
        closeEditor();
    }

    private void addSerieTv() {
        grid.asSingleSelect().clear();
        newSerieTv =true;
        editSerieTv(new SerieTv());
    }

    private  void saveSerieTv(MediaForm.SaveEvent event) {
        SerieTv daModificare = new SerieTv(
                event.getMedia().getTitolo(),
                event.getMedia().getAnnoUscita(),
                event.getMedia().getSinossi(),
                event.getMedia().getLinkTrailer(),
                event.getMedia().getLinkLocandina());
        daModificare.setGeneri(event.getMedia().getGeneri());
        daModificare.setRuoli(event.getMedia().getRuoli());
        daModificare.setId(event.getMedia().getId());

        if(newSerieTv) {
            daModificare.getGeneri().clear();
            daModificare.getRuoli().clear();
            daModificare = gestioneCatalogoControl.addSerieTV(daModificare);
            daModificare.setGeneri(event.getMedia().getGeneri());
            newSerieTv =false;
        }
        if(generiAggiunti) {
            gestioneCatalogoControl.addGeneriSerieTv(daModificare.getGeneri(), daModificare.getId());
            generiAggiunti = false;
        }
        if(ruoliAggiunti != null) {


            for(Ruolo ruolo : ruoliAggiunti) {
                if(!serieTvSelezionato.getRuoli().contains(ruolo)) {

                    ruolo.setMedia(daModificare);
                    gestioneCatalogoControl.addRuolo(ruolo, ruolo.getCastId(), daModificare.getId());
                }
            }
            event.getMedia().setRuoli(ruoliAggiunti);
        }
        gestioneCatalogoControl.addSerieTV(daModificare);
        updateList();
        closeEditor();
    }

    private void deleteSerieTv(MediaForm.DeleteEvent event) {
        gestioneCatalogoControl.removeSerieTV(event.getMedia().getId());
        updateList();
        closeEditor();
    }




    private void updateList() {
        grid.setItems(filterText.isEmpty() ? gestioneCatalogoControl.findAllSerieTv() : gestioneCatalogoControl.searchSerieTvByTitle(filterText.getValue()));
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
        grid.removeColumnByKey("sinossi");
        grid.removeColumnByKey("generi");
        grid.removeColumnByKey("ruoli");
        grid.removeColumnByKey("mediaVoti");

        grid.setColumns("id", "titolo", "annoUscita", "linkTrailer", "linkLocandina");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editSerieTv(event.getValue()));
    }

    private void editSerieTv(SerieTv value) {
        if(value == null) {
            closeEditor();
        } else {
            serieTvSelezionato =value;
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



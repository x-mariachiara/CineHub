package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.Recensibile;
import com.unisa.cinehub.data.entity.Recensione;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.beans.factory.annotation.Autowired;

public class RecensioneFormComponent extends Dialog {

    @Autowired
    CatalogoControl catalogoControl;

    public RecensioneFormComponent(Recensibile r, CatalogoControl catalogoControl) {
        this.catalogoControl = catalogoControl;
        TextArea contenutoRecensione = new TextArea("Cosa ne pensi?");
        ComboBox<Integer> punteggi = new ComboBox<>();
        punteggi.setItems(1, 2, 3, 4, 5);
        punteggi.setLabel("Vota");
        Button inviaRecensione = new Button("Invia Recensione", e -> recensisci(contenutoRecensione.getValue(), punteggi.getValue(), r));
        FormLayout form = new FormLayout();
        form.add(punteggi, contenutoRecensione, inviaRecensione);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));

        add(form);
    }

    private void recensisci(String contenuto, Integer punteggio, Recensibile r) {
        Recensione recensione = new Recensione();
        recensione.setContenuto(contenuto);
        recensione.setPunteggio(punteggio);
        try {
            Film recensito = (Film) r;


            recensione.setFilm(recensito);
        } catch (ClassCastException c) {
            Puntata recensito = (Puntata) r;
            recensione.setPuntata(recensito);
        } finally {
            System.out.println(recensione);
        }
        catalogoControl.addRecensione(recensione);
        close();
    }
}

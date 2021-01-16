package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.views.risultati.RisultatiRicercaTitoloView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.gatanaso.MultiselectComboBox;

public class RicercaComponent extends HorizontalLayout {
    TextField ricercaPerTitolo = new TextField();
    MultiselectComboBox<Genere> comboBox = new MultiselectComboBox<>();
    Button cerca = new Button("cerca");

    public RicercaComponent() {
        ricercaPerTitolo.setPlaceholder("Titolo...");
        comboBox.setPlaceholder("Cerca per genere");
        comboBox.setItems(Genere.getTuttiGeneri());
        comboBox.setItemLabelGenerator(g -> {
            return (g.getNomeGenere() + "").toLowerCase();
        });
        cerca.addClickListener(e -> {
            if(ricercaPerTitolo.isEmpty() && comboBox.isEmpty()) {
                Notification.show("Devi inserire un titolo e/o almeno un genere");
            } else {
                getUI().get().getSession().setAttribute("titolo", ricercaPerTitolo.getValue());
                getUI().get().getSession().setAttribute("generi", comboBox.getSelectedItems());
                getUI().get().navigate(RisultatiRicercaTitoloView.class);
            }
        });
        add(ricercaPerTitolo, comboBox, cerca);
    }

    public void toVertical() {
        VerticalLayout v = new VerticalLayout();
        v.add(ricercaPerTitolo, comboBox, cerca);
        removeAll();
        add(v);
    }
}

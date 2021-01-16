package com.unisa.cinehub.views.component;

import com.unisa.cinehub.data.entity.Genere;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.gatanaso.MultiselectComboBox;

public class RicercaComponent extends HorizontalLayout {
    TextField ricercaPerTitolo = new TextField();
    MultiselectComboBox<Genere> comboBox = new MultiselectComboBox<>();
    Button cerca = new Button("Cerca");

    public RicercaComponent() {
        ricercaPerTitolo.setPlaceholder("Titolo...");
        comboBox.setPlaceholder("Cerca per genere");
        //TODO deve prendere tutti i genere dal db
        comboBox.setItems(new Genere(Genere.NomeGenere.AZIONE));
        comboBox.setItemLabelGenerator(g -> {
            return g.getNomeGenere() + "";
        });
        Button cerca = new Button("Cerca");
        add(ricercaPerTitolo, comboBox, cerca);
    }
}

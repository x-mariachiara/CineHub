package com.unisa.cinehub.system.pageObjects;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.textfield.testbench.IntegerFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextAreaElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-form-layout")
@Attribute(name = "id", value = "puntata-form")
public class PuntataFormElement extends TestBenchElement {

    public TextFieldElement getTitoloFieldElement() {
        return $(TextFieldElement.class).id("titolo");
    }

    public TextAreaElement getSinossiElement() {
        return $(TextAreaElement.class).id("sinossi");
    }

    public IntegerFieldElement getNumeroPuntataFieldElement() {
        return $(IntegerFieldElement.class).id("numero-puntata");
    }

    public IntegerFieldElement getNumeroStagioneFieldElement() {
        return $(IntegerFieldElement.class).id("numero-stagione");
    }

    public ButtonElement getSaveButtonElement() {
        return $(ButtonElement.class).id("salva");
    }

    public ButtonElement getCancellaButtonElement() {
        return $(ButtonElement.class).id("cancella");
    }

    public ComboBoxElement getSerieTvComboBoxElement() {
        return $(ComboBoxElement.class).id("combobox-serietv");
    }

    public void compilaForm(String titolo, String sinossi, String numeroPuntata, String numeroStagione, String serieTV) {
        getTitoloFieldElement().setValue(titolo);
        getSinossiElement().setValue(sinossi);
        getNumeroPuntataFieldElement().setValue(numeroPuntata);
        getNumeroStagioneFieldElement().setValue(numeroStagione);
        getSerieTvComboBoxElement().selectByText(serieTV);
    }

}

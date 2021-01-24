package com.unisa.cinehub.system.PageObjects;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-form-layout")
@Attribute(name = "id", value = "cast-form")
public class CastFormElement extends TestBenchElement {

    public TextFieldElement getNomeFieldElement() {
        return $(TextFieldElement.class).id("nome");
    }

    public TextFieldElement getCognomeFieldElement() {
        return $(TextFieldElement.class).id("cognome");
    }

    public ButtonElement getSalvaButtonElement() {
        return $(ButtonElement.class).id("salva");
    }

    public ButtonElement getEliminaButtonElement() {
        return $(ButtonElement.class).id("elimina");
    }

    public void compilaForm(String nome, String cognome) {
        getNomeFieldElement().setValue(nome);
        getCognomeFieldElement().setValue(cognome);
    }


}

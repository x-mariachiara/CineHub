package com.unisa.cinehub.system.PageObjects;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-horizontal-layout")
@Attribute(name="id", value = "ricerca-component")
public class RicercaObjectElement extends TestBenchElement {

    public TextFieldElement getTextFieldElement() {
        return $(TextFieldElement.class).id("titolo");
    }

    public MultiSelectComboBox getMultiComboBoxElement() {
        return $(MultiSelectComboBox.class).id("comboBox");
    }

    public ButtonElement getCercaButton() {
        return $(ButtonElement.class).first();
    }





}




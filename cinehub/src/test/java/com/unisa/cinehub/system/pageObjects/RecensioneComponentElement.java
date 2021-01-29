package com.unisa.cinehub.system.pageObjects;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.html.testbench.ParagraphElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-vertical-layout")
@Attribute(name = "id", value = "recensione-component")
public class RecensioneComponentElement extends TestBenchElement {

    public ButtonElement getRispondiButton() {
        return $(DivElement.class).first().$(ButtonElement.class).id("rispondi-button");
    }

    public ButtonElement getMiPiaceButton() {
        return $(ButtonElement.class).id("mipiace-button");
    }

    public ButtonElement getNonMiPiaceButton() {
        return $(ButtonElement.class).id("nonmipiace-button");
    }

    public ParagraphElement getNumMipiaceElement() {
        return $(ParagraphElement.class).id("mipiace-number");
    }

    public ParagraphElement getNumNonMiPiaceElement() {
        return $(ParagraphElement.class).id("nonmipiace-number");
    }

}

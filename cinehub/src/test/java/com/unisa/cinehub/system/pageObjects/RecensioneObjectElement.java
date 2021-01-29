package com.unisa.cinehub.system.pageObjects;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.flow.component.textfield.testbench.TextAreaElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("flow-component-renderer")
@Attribute(name = "appid", value = "ROOT")
public class RecensioneObjectElement extends TestBenchElement {

    protected TextAreaElement getContenutoField(){
        return $(DivElement.class).first().$("vaadin-form-layout").first().$(TextAreaElement.class).id("contenuto");
    }

    protected ComboBoxElement getVotoField(){
        return $(DivElement.class).first().$("vaadin-form-layout").first().$(ComboBoxElement.class).id("punteggio");
    }

    protected ButtonElement getBottone() {
        return $(DivElement.class).first().$("vaadin-form-layout").first().$(ButtonElement.class).id("invia-recensione");
    }

    public void scriviRecensione(String punti, String contenuto){
        getVotoField().selectByText(punti);
        getContenutoField().setValue(contenuto);
        getBottone().click();
    }
}

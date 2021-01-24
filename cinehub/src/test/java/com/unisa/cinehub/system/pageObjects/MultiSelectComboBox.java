package com.unisa.cinehub.system.pageObjects;

import com.vaadin.flow.component.html.testbench.DivElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.elementsbase.Element;

import java.util.List;

@Element("multiselect-combo-box")
public class MultiSelectComboBox extends TestBenchElement {


    // Questo metodo non funziona perché il tag vaadin-combo-box-overlay è figlio di body
    // mentre la visibilità del metodo $() in questa classe è rilegata nell'elemento:
    // multi-select-combo-box
    // Ancora non so come ressettare la visibilità. Per ovviare al problema nel metodo di test:
    // TestBenchElement comboBoxItemContainer = $("vaadin-combo-box-overlay").waitForFirst();
    public List<TestBenchElement> getComboBoxItems() {
        TestBenchElement comboBoxItemContainer =  $("vaadin-combo-box-overlay").waitForFirst();
        return  comboBoxItemContainer.$("div").get(1).$("div").id("content").$("div").id("scroller").$("iron-list").first().$("vaadin-combo-box-item").all();
    }

    public void openComboBox() {
        this.$(DivElement.class).first().$("vaadin-combo-box-light").first().callFunction("setAttribute", "opened", "true");
    }

}
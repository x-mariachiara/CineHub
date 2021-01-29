package com.unisa.cinehub.system.pageObjects;

import com.vaadin.flow.component.html.testbench.H3Element;
import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("div")
@Attribute(name = "class", value = "card-media")
public class CardMediaElement  extends TestBenchElement {

    public VerticalLayoutElement getInfo() {
        return $(VerticalLayoutElement.class).id("info");
    }

    public H3Element getTitleElement() {
        return $(H3Element.class).first();
    }

}

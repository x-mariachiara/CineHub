package com.unisa.cinehub.system.PageObjects;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.textfield.testbench.IntegerFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextAreaElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

@Element("vaadin-form-layout")
@Attribute(name = "id", value = "media-form")
public class MediaFormElement extends TestBenchElement {

    public TextFieldElement getTitoloFieldElement() {
        return $(TextFieldElement.class).id("titolo");
    }

    public TextFieldElement getLinkLocandinaFieldElement() {
        return $(TextFieldElement.class).id("link-locandina");
    }

    public TextFieldElement getLinkTrailerFieldElement() {
        return $(TextFieldElement.class).id("link-trailer");
    }

    public IntegerFieldElement getAnnoUscitaFieldElement() {
        return $(IntegerFieldElement.class).id("anno-uscita");
    }

    public TextAreaElement getTextAreaElement() {
        return $(TextAreaElement.class).id("sinossi");
    }

    public ButtonElement getSaveButton() {
        return $(ButtonElement.class).id("salva");
    }

    public ButtonElement getCancellaButton() {
        return $(ButtonElement.class).id("elimina");
    }

    public ButtonElement getResetButton() {
        return $(ButtonElement.class).id("reset");
    }

    public MultiSelectComboBox getGeneriComboBox() {
        return $(MultiSelectComboBox.class).id("generi");
    }

    public MultiSelectComboBox getAttoriComboBox() {
        return $(MultiSelectComboBox.class).id("attori");
    }

    public MultiSelectComboBox getRegistiComboBox() {
        return $(MultiSelectComboBox.class).id("registi");
    }

    public MultiSelectComboBox getVoiceActorsComboBox() {
        return $(MultiSelectComboBox.class).id("voiceactors");
    }

    public void compilaForm(String titolo, String linkLocandina, String linkTrailer, String annoUscita, String sinossi) {
        getTitoloFieldElement().setValue(titolo);
        getLinkLocandinaFieldElement().setValue(linkLocandina);
        getLinkTrailerFieldElement().setValue(linkTrailer);
        getAnnoUscitaFieldElement().setValue(annoUscita);
        getTextAreaElement().setValue(sinossi);
    }

}

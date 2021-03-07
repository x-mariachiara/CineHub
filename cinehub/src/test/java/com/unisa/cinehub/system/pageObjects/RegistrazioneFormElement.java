package com.unisa.cinehub.system.pageObjects;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.checkbox.testbench.CheckboxElement;
import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.datepicker.testbench.DatePickerElement;
import com.vaadin.flow.component.textfield.testbench.EmailFieldElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.annotations.Attribute;
import com.vaadin.testbench.elementsbase.Element;

import java.time.LocalDate;


@Element("vaadin-form-layout")
@Attribute(name="class", value = "form-register")
public class RegistrazioneFormElement extends TestBenchElement {

    public TextFieldElement getNomeField() {
        return  $(TextFieldElement.class).id("nome");
    }

    public TextFieldElement getCognomeField() {
        return  $(TextFieldElement.class).id("cognome");
    }

    public DatePickerElement getDataDiNascitaField() {
        return  $(DatePickerElement.class).id("data-nascita");
    }

    public EmailFieldElement getEmailField() {
        return  $(EmailFieldElement.class).id("email");
    }

    public PasswordFieldElement getPasswordFieldElement() {
        return $(PasswordFieldElement.class).id("password");
    }

    public PasswordFieldElement getConfermaPasswordFieldElement() {
        return $(PasswordFieldElement.class).id("conferma-password");
    }

    public CheckboxElement getConfermaElement() {
        return $(CheckboxElement.class).id("policy");
    }

    public ButtonElement getBottoneElement() {
        return $(ButtonElement.class).id("registrati");
    }

    public TextFieldElement getUsernameElement() {
        return $(TextFieldElement.class).id("username");
    }

    public ComboBoxElement getSessoElement() {
        return $(ComboBoxElement.class).id("sesso");
    }

    public ComboBoxElement getHobbyElement() {
        return $(ComboBoxElement.class).id("hobby");
    }

    public void compilaForm(String nome, String cognome, String email, LocalDate dataNascita, String username, String password, String confermaPassword, Boolean check, String sesso, String hobby){
        getNomeField().setValue(nome);
        getCognomeField().setValue(cognome);
        getEmailField().setValue(email);
        getUsernameElement().setValue(username);
        getPasswordFieldElement().setValue(password);
        getConfermaPasswordFieldElement().setValue(confermaPassword);
        getConfermaElement().setChecked(check);
        getSessoElement().selectByText(sesso);
        getHobbyElement().selectByText(hobby);
        getDataDiNascitaField().setDate(dataNascita);
    }


}

package com.unisa.cinehub.views.login;

import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;

public class RegisterForm extends FormLayout  implements HasEnabled {
    private TextField nome =new TextField("Nome");
    private TextField cognome = new TextField("Cognome");
    private DatePicker dataNascita = new DatePicker("Data di Nascita");
    private EmailField email = new EmailField("Email");
    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Inserisci Password");
    Button button = new Button("Registrati");

    private Binder<Utente> binder = new BeanValidationBinder<>(Utente.class);


    private Utente utente;

    public RegisterForm() {
        setClassName("form-register");
        binder.bindInstanceFields(this);


        nome.setId("nome");
        nome.setRequired(true);

        cognome.setId("cognome");
        cognome.setRequired(true);

        dataNascita.setId("data-nascita");
        dataNascita.setRequired(true);

        email.setRequiredIndicatorVisible(true);
        email.setId("email");

        username.setRequired(true);
        username.setId("username");

        password.setId("password");
        password.setRequired(true);

        PasswordField confermaPassword = new PasswordField("Conferma Password");
        confermaPassword.setRequired(true);


        Checkbox policy = new Checkbox("accetto le policy");
        Paragraph showPolicy = new Paragraph("leggi policy");
        policy.setId("policy");

        showPolicy.setClassName("show-policy");
        showPolicy.addClickListener(e -> {
            Dialog dialogPolicy = new Dialog();
            dialogPolicy.setWidth("30%");
            dialogPolicy.setHeight("50%");
            HorizontalLayout titoloDialog = new HorizontalLayout(new H3("Policy di CineHub"));
            titoloDialog.setWidthFull();
            titoloDialog.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            VerticalLayout contenutoDialog = new VerticalLayout();
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            contenutoDialog.add(new Paragraph("{ INSERIRE POLICY }"));
            dialogPolicy.add(titoloDialog, contenutoDialog);
            dialogPolicy.open();
        });
        button.addClickListener(buttonClickEvent -> register());
        button.setId("registrati");
        button.setEnabled(false);


        policy.setRequiredIndicatorVisible(true);
//        policy.addClickListener(e -> {
//            if(!e.getSource().getValue()) {
//                button.setEnabled(false);
//            }
//        });
        showPolicy.getStyle().set("margin-top", "0");
        showPolicy.getStyle().set("margin-bottom", "0");
        HorizontalLayout policyLayout = new HorizontalLayout(policy, showPolicy);
        policyLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        policyLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        confermaPassword.setErrorMessage("Password e conferma password non coincidono");



        confermaPassword.addValueChangeListener(e -> {
            if (!e.getValue().equals(password.getValue())) {
                confermaPassword.setInvalid(true);
                button.setEnabled(false);
            } else {
                confermaPassword.setInvalid(false);
                if(policy.getValue()) {
                    button.setEnabled(true);
                }
            }
        });

        policy.addClickListener(checkboxClickEvent -> {
            if (checkboxClickEvent.getSource().getValue() && !confermaPassword.isInvalid()) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
        });


        confermaPassword.setValueChangeMode(ValueChangeMode.LAZY);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(nome, cognome, email, username, password, confermaPassword, dataNascita, policyLayout, button);
    }

    public void setUtente(Recensore recensore) {
        this.utente = recensore;
        binder.readBean(recensore);
    }

    private void register() {
        System.out.println(utente);
        try {
            binder.writeBean(utente);
            fireEvent(new SaveEvent(this, utente));
        } catch (ValidationException e) {
            button.setEnabled(false);
        }

    }


    public static abstract class RegisterFormEvent extends ComponentEvent<RegisterForm> {

        private Utente utente;

        public RegisterFormEvent(RegisterForm source, Utente utente) {
            super(source, false);
            this.utente = utente;
        }

        public Utente getUtente() {
            return utente;
        }
    }

    public static class SaveEvent extends RegisterFormEvent {
        public SaveEvent(RegisterForm source, Utente recensore) {
            super(source, recensore);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}

package com.unisa.cinehub.views.login;

import com.unisa.cinehub.control.UtenteControl;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;


@Route("register")
public class RegisterView extends VerticalLayout {

    @Autowired
    private UtenteControl utenteControl;

    public RegisterView(UtenteControl utenteControl) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(new H2("Registrati adesso"), createForm());
    }


    private VerticalLayout createForm() {
        TextField nome =new TextField("Nome");
        TextField cognome = new TextField("Cognome");
        DatePicker dataDiNascita = new DatePicker("Data di Nascita");
        EmailField email = new EmailField("Email");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Inserisci Password");
        PasswordField confermaPassword = new PasswordField("Conferma Password");
        Button button = new Button("Registrati", event -> register(
                nome.getValue(),
                cognome.getValue(),
                dataDiNascita.getValue(),
                email.getValue(),
                username.getValue(),
                password.getValue(),
                confermaPassword.getValue()
        ));


        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.add(nome,  cognome);
        HorizontalLayout secondRow = new HorizontalLayout();
        secondRow.add(email,  username);
        HorizontalLayout thirdRow = new HorizontalLayout();
        thirdRow.add(password, confermaPassword);
        HorizontalLayout fourthRow = new HorizontalLayout();
        fourthRow.add(dataDiNascita, button);

        VerticalLayout form = new VerticalLayout();
        form.add(firstRow, secondRow, thirdRow, fourthRow);
        form.setJustifyContentMode(JustifyContentMode.CENTER);
        return form;
    }


    private void register(String nome, String cognome, LocalDate dataDiNascita, String email, String username, String password, String confermaPassword) {
        System.out.println(nome + " " + cognome + " " + dataDiNascita + " " + email + " " +username + " " +password + " " + confermaPassword);
        if (nome.isBlank()) {
            Notification.show("Inserisci il nome");
        } else if (cognome.isBlank()) {
            Notification.show("Inserisci il cognome");
        } else if (dataDiNascita == null) {
            Notification.show("Inserisci la data di nascita");
        } else if (email.isBlank()) {
            Notification.show("Inserisci email");
        } else if (username.isBlank()) {
            Notification.show("Inserisci username");
        } else if (password.isBlank()) {
            Notification.show("Inserisci password");
        } else if (confermaPassword.isBlank() || !password.equals(confermaPassword)) {
            Notification.show("Password e conferma password non coincidono");
        } else {
            Utente utente = new Recensore(email, nome, cognome, dataDiNascita, username, password, false, false);
            if (!utenteControl.registrazioneUtente(utente)){
                Notification.show("Devi avere almeno tredici anni");
            }
        }
    }


}

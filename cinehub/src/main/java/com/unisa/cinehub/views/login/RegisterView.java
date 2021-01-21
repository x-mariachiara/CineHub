package com.unisa.cinehub.views.login;

import com.unisa.cinehub.control.UtenteControl;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BannedException;
import com.unisa.cinehub.model.exception.UserUnderAgeException;
import com.unisa.cinehub.views.homepage.HomepageView;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinServletRequest;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Parameter;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;


@Route(value = "register")
@PageTitle("Signup | CineHub")
public class RegisterView extends VerticalLayout {


    @Autowired
    private UtenteControl utenteControl;

    public RegisterView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(new H2("Registrati adesso"), createForm(), new H3(new RouterLink("Torna alla homepage", HomepageView.class)));
    }


    private HorizontalLayout createForm() {
        TextField nome =new TextField("Nome");
        nome.setRequired(true);
        TextField cognome = new TextField("Cognome");
        cognome.setRequired(true);
        DatePicker dataDiNascita = new DatePicker("Data di Nascita");
        dataDiNascita.setRequired(true);
        EmailField email = new EmailField("Email");
        email.setRequiredIndicatorVisible(true);
        TextField username = new TextField("Username");
        username.setRequired(true);
        PasswordField password = new PasswordField("Inserisci Password");
        password.setRequired(true);
        PasswordField confermaPassword = new PasswordField("Conferma Password");
        confermaPassword.setRequired(true);
        Checkbox policy = new Checkbox("policy");
        Paragraph showPolicy = new Paragraph("leggi policy");
        showPolicy.setClassName("show-policy");
        showPolicy.addClickListener(e -> {
            Dialog dialogPolicy = new Dialog();
            dialogPolicy.setWidth("30%");
            dialogPolicy.setHeight("50%");
            HorizontalLayout titoloDialog = new HorizontalLayout(new H3("Policy di CineHub"));
            titoloDialog.setWidthFull();
            titoloDialog.setJustifyContentMode(JustifyContentMode.CENTER);
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
        Button button = new Button("Registrati", event -> register(
                nome.getValue(),
                cognome.getValue(),
                dataDiNascita.getValue(),
                email.getValue(),
                username.getValue(),
                password.getValue(),
                confermaPassword.getValue(),
                policy.getValue()
        ));


        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.add(nome,  cognome);
        HorizontalLayout secondRow = new HorizontalLayout();
        secondRow.add(email,  username);
        HorizontalLayout thirdRow = new HorizontalLayout();
        thirdRow.add(password, confermaPassword);
        HorizontalLayout fourthRow = new HorizontalLayout();
        fourthRow.add(dataDiNascita, policy, showPolicy);
        fourthRow.setAlignItems(Alignment.BASELINE);
        HorizontalLayout fifthRow = new HorizontalLayout();
        fifthRow.setWidthFull();
        fifthRow.setJustifyContentMode(JustifyContentMode.CENTER);
        fifthRow.add(button);

        VerticalLayout form = new VerticalLayout();
        form.add(firstRow, secondRow, thirdRow, fourthRow, fifthRow);
        form.setJustifyContentMode(JustifyContentMode.CENTER);
        HorizontalLayout composite = new HorizontalLayout();
        composite.add(form);
        return composite;
    }


    private void register(String nome, String cognome, LocalDate dataDiNascita, String email, String username, String password, String confermaPassword, boolean policy) {
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
        } else if (!policy){
            Notification.show("Devi accettare le policy");
        } else {
            Utente utente = new Recensore(email, nome, cognome, dataDiNascita, username, password, false, false);
            try {
                utenteControl.registrazioneUtente(utente, (HttpServletRequest) VaadinServletRequest.getCurrent());
            }catch (UserUnderAgeException e) {
                Notification.show("Devi avere più di 13 anni");
            } catch (AlreadyExsistsException c) {
                Notification.show("Esiste già un account con questa email");
            } catch (BannedException b) {
                Notification.show("L'account con email: " + email + " è stato bannato.");
            }
        }
    }


}

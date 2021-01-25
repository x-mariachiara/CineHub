package com.unisa.cinehub.views.login;

import com.unisa.cinehub.control.UtenteControl;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.model.exception.AlreadyExsistsException;
import com.unisa.cinehub.model.exception.BannedException;
import com.unisa.cinehub.model.exception.UserUnderAgeException;
import com.unisa.cinehub.views.homepage.HomepageView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


@Route(value = "register")
@PageTitle("Signup | CineHub")
@CssImport("./styles/views/components/shared-styles.css")
public class RegisterView extends VerticalLayout {


    @Autowired
    private UtenteControl utenteControl;
    private RegisterForm form = new RegisterForm();



    public RegisterView() {
        setId("registrazione-view");
        setSizeFull();
        initForm();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        VerticalLayout container = new VerticalLayout(new H2("Registrati adesso"), form, new H3(new RouterLink("Torna alla homepage", HomepageView.class)));
//        container.setAlignItems(Alignment.CENTER);
//        container.setJustifyContentMode(JustifyContentMode.CENTER);
        container.getStyle().set("width", "unset");
        container.addClassName("form-container");
        add(container);
    }


    private void initForm() {
        form.addListener(RegisterForm.SaveEvent.class, this::register);
        form.setUtente(new Recensore());

    }

    private void register(RegisterForm.SaveEvent event) {
        try {
            System.out.println(event.getUtente());
            utenteControl.registrazioneUtente(event.getUtente(), (HttpServletRequest) VaadinServletRequest.getCurrent());
            getUI().ifPresent(ui -> ui.navigate(MiddleStepView.class, event.getUtente().getEmail()));
        }catch (UserUnderAgeException e) {
            Notification.show("Devi avere più di 13 anni");
        } catch (AlreadyExsistsException c) {
            Notification.show("Esiste già un account con questa email");
        } catch (BannedException b) {
            Notification.show("L'account con email: " + event.getUtente().getEmail() + " è stato bannato.");
        }

    }


//    private void register(String nome, String cognome, LocalDate dataDiNascita, String email, String username, String password, String confermaPassword, boolean policy) {
//        System.out.println(nome + " " + cognome + " " + dataDiNascita + " " + email + " " +username + " " +password + " " + confermaPassword);
//        if (nome.isBlank()) {
//            Notification.show("Inserisci il nome");
//        } else if (cognome.isBlank()) {
//            Notification.show("Inserisci il cognome");
//        } else if (dataDiNascita == null) {
//            Notification.show("Inserisci la data di nascita");
//        } else if (email.isBlank()) {
//            Notification.show("Inserisci email");
//        } else if (username.isBlank()) {
//            Notification.show("Inserisci username");
//        } else if (password.isBlank()) {
//            Notification.show("Inserisci password");

//        } else if (!policy){
//            Notification.show("Devi accettare le policy");
//        } else {
//            Utente utente = new Recensore(email, nome, cognome, dataDiNascita, username, password, false, false);
//
//        }
//    }


}

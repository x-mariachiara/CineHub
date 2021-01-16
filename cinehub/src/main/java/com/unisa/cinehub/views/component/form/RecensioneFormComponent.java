package com.unisa.cinehub.views.component.form;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.Recensibile;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.exception.NotLoggedException;
import com.unisa.cinehub.views.login.LoginView;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;


public class RecensioneFormComponent extends Dialog {

    @Autowired
    CatalogoControl catalogoControl;

    public RecensioneFormComponent(Recensibile r, CatalogoControl catalogoControl) {
        this.catalogoControl = catalogoControl;
        TextArea contenutoRecensione = new TextArea("Cosa ne pensi?");
        contenutoRecensione.setRequired(true);
        ComboBox<Integer> punteggi = new ComboBox<>();
        punteggi.setRequired(true);
        punteggi.setItems(1, 2, 3, 4, 5);
        punteggi.setLabel("Vota");
        Button inviaRecensione = new Button("Invia Recensione", e -> recensisci(contenutoRecensione.getValue(), punteggi.getValue(), r, e.getSource()));
        FormLayout form = new FormLayout();
        form.add(punteggi, contenutoRecensione, inviaRecensione);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));

        add(form);
    }

    private void recensisci(String contenuto, Integer punteggio, Recensibile r, Button source) {
        Recensione recensione = new Recensione();
        recensione.setContenuto(contenuto);
        recensione.setPunteggio(punteggio);
        try {
            Film recensito = (Film) r;


            recensione.setFilm(recensito);
        } catch (ClassCastException c) {
            Puntata recensito = (Puntata) r;
            recensione.setPuntata(recensito);
        } finally {
            System.out.println(recensione);
        }
        try {
            catalogoControl.addRecensione(recensione);
        } catch (NotLoggedException e){
            getUI().ifPresent(ui -> ui.navigate(LoginView.class));
        } catch (NotAuthorizedException e) {
            Notification.show("Non sei autorizzato a scrivere una recensione");
        } catch (InvalidBeanException | BeanNotExsistException e) {
            Notification.show("Si è verificato un errore");
        }
        close();
    }

    @Override
    public void close() {
        fireEvent(new SaveEvent(this));
        super.close();
    }


    public static abstract class RecensioneFormEvent extends ComponentEvent<RecensioneFormComponent> {

        public RecensioneFormEvent(RecensioneFormComponent source) {
            super(source, false);
        }


    }

    public static class SaveEvent extends RecensioneFormEvent {
        SaveEvent(RecensioneFormComponent source) {
            super(source);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}

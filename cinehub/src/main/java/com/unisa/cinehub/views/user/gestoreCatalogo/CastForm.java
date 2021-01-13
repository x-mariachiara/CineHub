package com.unisa.cinehub.views.user.gestoreCatalogo;

import com.unisa.cinehub.data.entity.Cast;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


public class CastForm extends FormLayout {

    private Cast cast;
    private TextField nome = new TextField("Nome");
    private TextField cognome = new TextField("Cognome");
    private Button save = new Button("Salva");
    private Button elimina = new Button("Cancella");
    private Button reset = new Button("reset");
    private Binder<Cast> binder = new BeanValidationBinder<>(Cast.class);

    public CastForm() {
        addClassName("configure-form");
        binder.bindInstanceFields(this);
        HorizontalLayout h = new HorizontalLayout();
        save.addClickListener(e -> validateAndSave());
        elimina.addClickListener(e -> fireEvent(new DeleteEvent(this, cast)));
        reset.addClickListener(e -> fireEvent(new CloseEvent(this)));
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        h.add(save, elimina, reset);
        add(nome, cognome, h);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(cast);
            fireEvent(new SaveEvent(this, cast));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setCast(Cast cast) {
        this.cast = cast;
        binder.readBean(cast);
    }

    public static abstract class CastFormEvent extends ComponentEvent<CastForm> {

        private Cast cast;

        public CastFormEvent(CastForm source, Cast cast) {
            super(source, false);
            this.cast = cast;
        }

        public Cast getCast() {
            return cast;
        }
    }

    public static class SaveEvent extends CastFormEvent {
        public SaveEvent(CastForm source, Cast cast) {
            super(source, cast);
        }
    }

    public static class DeleteEvent extends CastFormEvent {
        public DeleteEvent(CastForm source, Cast cast) {
            super(source, cast);
        }
    }

    public static class CloseEvent extends CastFormEvent {
        public CloseEvent(CastForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener){
        return getEventBus().addListener(eventType, listener);
    }
}

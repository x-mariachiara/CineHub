package com.unisa.cinehub.views.component.form;

import com.unisa.cinehub.data.entity.Recensione;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class RispostaFormDialog extends Dialog {

    private FormLayout form = new FormLayout();
    private TextArea contenuto = new TextArea();
    private Button send = new Button("Rispondi");
    Binder<Recensione> binder = new BeanValidationBinder<>(Recensione.class);
    private Recensione recensione = new Recensione();

    public RispostaFormDialog() {
        initForm();
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        binder.bindInstanceFields(this);
        add(form);
    }

    private void initForm() {
        form.add(contenuto, send);
        form.addClassName("risposta-form");
        send.setIcon(new Icon(VaadinIcon.COMMENT_O));
        send.addClickListener(e -> validateAndSend());
    }

    private void validateAndSend() {
        try {
            binder.writeBean(recensione);
            fireEvent(new SaveEvent(this, recensione));
            close();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public abstract static class RispostaFormEvent extends ComponentEvent<RispostaFormDialog> {
        private Recensione recensione;

        public RispostaFormEvent(RispostaFormDialog source, Recensione recensione) {
            super(source, false);
            this.recensione = recensione;
        }

        public Recensione getRecensione() {
            return recensione;
        }
    }

    public static class SaveEvent extends RispostaFormEvent {
        public SaveEvent(RispostaFormDialog source, Recensione recensione) {
            super(source, recensione);
        }
    }

}

package com.unisa.cinehub.views.user.gestoreCatalogo;

import com.unisa.cinehub.data.AbstractEntity;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.List;

public class PuntataForm extends FormLayout {
    private TextField titolo = new TextField("Titolo");
    private IntegerField numeroPuntata = new IntegerField("Numero Puntata");
    private IntegerField numeroStagione = new IntegerField("Numero Stagione");
    private TextArea sinossi = new TextArea("Sinossi");
    private ComboBox<SerieTv> serieTv = new ComboBox<>("Serie TV");
    private List<SerieTv> serieTvList;
    private Puntata puntata;
    private Binder<Puntata> binder = new BeanValidationBinder<>(Puntata.class);

    private Button saveButton = new Button("Salva");
    private Button deleteButton = new Button("Elimina");
    private Button closeButton = new Button("Termina Modifica");

    public PuntataForm(List<SerieTv> serieTvList) {
        addClassName("configure-form");
        this.serieTvList = serieTvList;
        binder.bindInstanceFields(this);
        VerticalLayout h = new VerticalLayout();
        saveButton.addClickListener(event -> validateAndSave());
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, puntata, serieTv.getValue().getId(), numeroStagione.getValue())));
        closeButton.addClickListener(event -> fireEvent(new CloseEvent(this)));
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.setIcon(new Icon(VaadinIcon.DATABASE));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
        closeButton.setIcon(new Icon(VaadinIcon.BAN));
        h.add(saveButton, deleteButton, closeButton);
        add(titolo, numeroPuntata, numeroStagione, sinossi, serieTv, h);
    }

    public void setPuntata(Puntata puntata) {
        this.puntata = puntata;
        binder.readBean(puntata);
        if(puntata != null && puntata.getStagioneId() != null) {
            numeroStagione.setValue(puntata.getStagione().getNumeroStagione());
            numeroStagione.setEnabled(false);
            preparePuntate(true);
        } else {
            numeroStagione.setEnabled(true);
            preparePuntate(false);
        }
    }

    private void validateAndSave() {
        try {
            binder.writeBean(puntata);
            fireEvent(new PuntataForm.SaveEvent(this, puntata, serieTv.getValue().getId(), numeroStagione.getValue()));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }



    public void preparePuntate(Boolean isEdit) {
        if(isEdit) {
            serieTvList.forEach(st -> {
                if(st.getId().equals(puntata.getStagioneId().getSerieTvId())){
                    serieTv.setValue(st);
                    return;
                }
            });
            serieTv.setEnabled(false);
        } else {
            serieTv.setItems(serieTvList);
            serieTv.setItemLabelGenerator(SerieTv::getTitolo);
            serieTv.setEnabled(true);
        }
    }

    public static abstract class PuntataFormEvent extends ComponentEvent<PuntataForm> {
        private Puntata puntata;
        private Long serieTvId;
        private Integer numeroStagione;

        public PuntataFormEvent(PuntataForm source, Puntata puntata, Long serieTvId, Integer numeroStagione) {
            super(source, false);
            this.puntata = puntata;
            this.serieTvId = serieTvId;
            this.numeroStagione = numeroStagione;
        }

        public Puntata getPuntata() {
            return puntata;
        }

        public Long getSerieTvId() {
            return serieTvId;
        }

        public Integer getNumeroStagione() {
            return numeroStagione;
        }
    }

    public static class SaveEvent extends PuntataFormEvent {
        public SaveEvent(PuntataForm source, Puntata puntata, Long serieTvId, Integer numeroStagione) {
            super(source, puntata, serieTvId, numeroStagione);
        }
    }

    public static class DeleteEvent extends PuntataFormEvent {
        public DeleteEvent(PuntataForm source, Puntata puntata, Long serieTvId, Integer numeroStagione) {
            super(source, puntata, serieTvId, numeroStagione);
        }
    }

    public static class CloseEvent extends PuntataFormEvent {
        public CloseEvent(PuntataForm source) {
            super(source, null, null, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}

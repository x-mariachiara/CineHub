package com.unisa.cinehub.views.user.gestoreCatalogo;

import ch.qos.logback.core.joran.util.StringToObjectConverter;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Genere;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.vaadin.gatanaso.MultiselectComboBox;

import java.util.ArrayList;
import java.util.Set;


public class FilmForm extends FormLayout {

    private TextField titolo = new TextField("Titolo");
    private IntegerField annoUscita = new IntegerField("Anno uscita");
    private TextField linkTrailer = new TextField("Link trailer");
    private TextField linkLocandina = new TextField("Link locandina");
    private TextArea sinossi = new TextArea("Sinossi");
    private MultiselectComboBox<Genere> generi = new MultiselectComboBox<>();
    private Button save = new Button("Salva");
    private Button elimina = new Button("Cancella");
    private Button reset = new Button("reset");
    private Binder<Film> binder = new BeanValidationBinder<>(Film.class);
    private Film film;

    public FilmForm() {

        generi.setItems(Genere.getTuttiGeneri());

        binder.bindInstanceFields(this);
        HorizontalLayout h = new HorizontalLayout();
        save.addClickListener(e -> validateAndSave());
        elimina.addClickListener(e -> fireEvent(new DeleteEvent(this, film)));
        reset.addClickListener(e -> fireEvent(new CloseEvent(this)));
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        h.add(save, elimina, reset);
        add(titolo, annoUscita, linkTrailer, linkLocandina, sinossi, generi, h);
    }

    public void setFilm(Film film) {
        this.film = film;
        binder.readBean(film);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(film);
            fireEvent(new SaveEvent(this, film));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    public static abstract class FilmFormEvent extends ComponentEvent<FilmForm> {

        private Film film;

        public FilmFormEvent(FilmForm source, Film film) {
            super(source, false);
            this.film = film;
        }

        public Film getFilm() {
            return film;
        }
    }

    public static class SaveEvent extends FilmFormEvent{
        public SaveEvent(FilmForm source, Film film) {
            super(source, film);
        }
    }

    public static class DeleteEvent extends  FilmFormEvent {
        public DeleteEvent(FilmForm source, Film film) {
            super(source, film);
        }
    }

    public static class CloseEvent extends  FilmFormEvent {
        public CloseEvent(FilmForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}

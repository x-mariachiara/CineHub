package com.unisa.cinehub.views.user.gestoreCatalogo;


import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.data.entity.Genere;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.entity.Ruolo;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
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
import org.vaadin.gatanaso.MultiselectComboBox;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MediaForm extends FormLayout {

    private TextField titolo = new TextField("Titolo");
    private IntegerField annoUscita = new IntegerField("Anno uscita");
    private TextField linkTrailer = new TextField("Link trailer");
    private TextField linkLocandina = new TextField("Link locandina");
    private TextArea sinossi = new TextArea("Sinossi");
    private MultiselectComboBox<Genere> generi = new MultiselectComboBox<>();
    private Button save = new Button("Salva");
    private Button elimina = new Button("Cancella");
    private Button reset = new Button("Termina modifche");
    private Binder<Media> binder = new BeanValidationBinder<>(Media.class);
    private Media media;
    private List<Cast> tuttoIlCast;
    private HashSet<Ruolo> ruoliSelezionati = new HashSet<>();
    private HashSet<Ruolo> attoriSelezionati = new HashSet<>();
    private HashSet<Ruolo> registiSelezionati = new HashSet<>();
    private HashSet<Ruolo> voiceactorsSelezionati = new HashSet<>();
    private MultiselectComboBox<Cast> attori = new MultiselectComboBox<>();
    private MultiselectComboBox<Cast> registi = new MultiselectComboBox<>();
    private MultiselectComboBox<Cast> voiceactors = new MultiselectComboBox<>();

    public MediaForm(List<Cast> tuttoIlCast) {
        setId("media-form");
        addClassName("configure-form");
        titolo.setId("titolo");
        annoUscita.setId("anno-uscita");
        linkLocandina.setId("link-locandina");
        linkLocandina.setPattern("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
        linkTrailer.setId("link-trailer");
        linkTrailer.setPattern("https:\\/\\/www.youtube.com\\/embed\\/(.*)");
        linkTrailer.setErrorMessage("Il link del trailer deve essere un embed di youtube");
        linkLocandina.setErrorMessage("Deve essere un link valido");
        sinossi.setId("sinossi");
        save.setId("salva");
        elimina.setId("elimina");
        reset.setId("reset");
        this.tuttoIlCast = tuttoIlCast;
        generi.setId("generi");
        generi.setItems(Genere.getTuttiGeneri());
        generi.setItemLabelGenerator(genere -> {
            return genere.getNomeGenere().toString();
        });
        attori.setId("attori");
        registi.setId("registi");
        voiceactors.setId("voiceactors");
        generi.setAllowCustomValues(false);
        generi.addSelectionListener(e -> fireEvent(new AddGenereEvent(this, media)));
        binder.bindInstanceFields(this);
        HorizontalLayout h = new HorizontalLayout();
        save.addClickListener(e -> validateAndSave());
        elimina.addClickListener(e -> fireEvent(new DeleteEvent(this, media)));
        reset.addClickListener(e -> fireEvent(new CloseEvent(this)));
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setIcon(new Icon(VaadinIcon.DATABASE));
        elimina.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        elimina.setIcon(new Icon(VaadinIcon.TRASH));
        reset.setIcon(new Icon(VaadinIcon.BAN));
        titolo.setRequired(true);
        linkLocandina.setRequired(true);
        linkTrailer.setRequired(true);
        sinossi.setRequired(true);
        annoUscita.setMin(1895);
        annoUscita.setMax(LocalDate.now().getYear());
        generi.setLabel("Generi");
        h.add(save, elimina, reset);
        add(titolo, annoUscita, linkTrailer, linkLocandina, sinossi, generi, configureRuoli(), h);
    }

    public void setMedia(Media media) {
        this.media = media;
        binder.readBean(media);
        if(media != null) {
            prepareCastMultiSelect();
        }

    }

    private void validateAndSave() {
        try {
            binder.writeBean(media);
            ruoliSelezionati.clear();
            ruoliSelezionati.addAll(attoriSelezionati.stream().collect(Collectors.toList()));
            ruoliSelezionati.addAll(registiSelezionati.stream().collect(Collectors.toList()));
            ruoliSelezionati.addAll(voiceactorsSelezionati.stream().collect(Collectors.toList()));
            System.out.println("Tutti i ruoli selezionati: " + ruoliSelezionati);
            fireEvent(new SaveEvent(this, media));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private VerticalLayout configureRuoli() {

        attori.setAllowCustomValues(false);
        registi.setAllowCustomValues(false);
        voiceactors.setAllowCustomValues(false);

        VerticalLayout container = new VerticalLayout();
        HorizontalLayout rigaAttori = new HorizontalLayout();

        attori.setItemLabelGenerator(cast -> {
            return cast.getNome() + " " + cast.getCognome();
        });
        attori.addSelectionListener(e -> {
            attoriSelezionati = getRuoloHashSet(e, Ruolo.Tipo.ATTORE);
            System.out.println("attori Selezionati: " + attoriSelezionati);
            fireEvent(new AddRuoloEvent(this, media, ruoliSelezionati));
        });
        attori.setItems(tuttoIlCast);
        rigaAttori.add(new Paragraph("Attori: "), attori);

        HorizontalLayout rigaRegisti = new HorizontalLayout();

        registi.setItemLabelGenerator(cast -> {
            return cast.getNome() + " " + cast.getCognome();
        });
        rigaRegisti.add(new Paragraph("Registi: "), registi);
        registi.setItems(tuttoIlCast);
        registi.addSelectionListener(e -> {
            registiSelezionati = getRuoloHashSet(e, Ruolo.Tipo.REGISTA);
            System.out.println("registi Selezionati: " + registiSelezionati);
            fireEvent(new AddRuoloEvent(this, media, ruoliSelezionati));
        });

        HorizontalLayout rigaVoiceActors = new HorizontalLayout();

        voiceactors.setItemLabelGenerator(cast -> {
            return cast.getNome() + " " + cast.getCognome();
        });
        voiceactors.addSelectionListener(e -> {
            voiceactorsSelezionati = getRuoloHashSet(e, Ruolo.Tipo.VOICEACTOR);
            System.out.println("voiceactors Selezionati: " + voiceactorsSelezionati);
            fireEvent(new AddRuoloEvent(this, media, ruoliSelezionati));
        });
        rigaVoiceActors.add(new Paragraph("Voice Actor: "), voiceactors);
        voiceactors.setItems(tuttoIlCast);

        container.add(rigaAttori, rigaRegisti, rigaVoiceActors);
        return container;
    }

    private HashSet<Ruolo> getRuoloHashSet(com.vaadin.flow.data.selection.MultiSelectionEvent<MultiselectComboBox<Cast>, Cast> e, Ruolo.Tipo tipo) {
        HashSet<Ruolo> ruoli = new HashSet<>();
        for(Cast c : e.getAllSelectedItems()) {
            Ruolo ruolo = new Ruolo();
            ruolo.setTipo(tipo);
            ruolo.setCast(c);
            ruolo.setMedia(media);
            ruoli.add(ruolo);
        }
        return ruoli;
    }

    private void prepareCastMultiSelect() {
        attori.deselectAll();
        registi.deselectAll();
        voiceactors.deselectAll();
        ruoliSelezionati.clear();
        for(Ruolo ruolo : media.getRuoli()){
            if(ruolo.getTipo().equals(Ruolo.Tipo.ATTORE)) {
                attori.select(ruolo.getCast());
            }else if(ruolo.getTipo().equals(Ruolo.Tipo.REGISTA)) {
                registi.select(ruolo.getCast());
            } else {
                voiceactors.select(ruolo.getCast());
            }
        }
    }


    public static abstract class MediaFormEvent extends ComponentEvent<MediaForm> {

        private Media media;

        public MediaFormEvent(MediaForm source, Media media) {
            super(source, false);
            this.media = media;
        }

        public Media getMedia() {
            return media;
        }
    }

    public static class SaveEvent extends MediaFormEvent{
        public SaveEvent(MediaForm source, Media film) {
            super(source, film);
        }
    }

    public static class DeleteEvent extends  MediaFormEvent {
        public DeleteEvent(MediaForm source, Media film) {
            super(source, film);
        }
    }

    public static class CloseEvent extends  MediaFormEvent {
        public CloseEvent(MediaForm source) {
            super(source, null);
        }
    }

    public static class AddGenereEvent extends MediaFormEvent {

        public AddGenereEvent(MediaForm source, Media film) {
            super(source, film);
        }
    }

    public static class AddRuoloEvent extends MediaFormEvent {
        private Collection<Ruolo> ruoli;
        public AddRuoloEvent(MediaForm source, Media film, Collection<Ruolo> ruoli) {
            super(source, film);
            this.ruoli = ruoli;
        }

        public Collection<Ruolo> getRuoli() {
            return ruoli;
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}

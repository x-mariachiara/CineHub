package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.ModerazioneControl;
import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.model.exception.NotLoggedException;
import com.unisa.cinehub.views.component.form.RispostaFormDialog;
import com.unisa.cinehub.views.login.LoginView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

public class RecensioneComponent extends VerticalLayout {

    private CatalogoControl catalogoControl;
    private ModerazioneControl moderazioneControl;
    private RispostaFormDialog rispostaFormDialog = new RispostaFormDialog();;
    private Div divRecensionePadre = new Div();
    private Div divRiposte = new Div();

    private Button miPiaceButton = new Button();
    private Button nonMiPiaceButton = new Button();
    private Button rispondi = new Button("rispondi");
    private Button segnalaRec = new Button("segnala");
    private Paragraph numMiPiace = new Paragraph();
    private Paragraph numNonMiPiace = new Paragraph();
    private Recensione recensione;

    public RecensioneComponent(Recensione recensione, CatalogoControl catalogoControl, ModerazioneControl moderazioneControl) {
        setId("recensione-component");
        this.catalogoControl = catalogoControl;
        this.recensione = recensione;
        this.moderazioneControl = moderazioneControl;
        rispostaFormDialog.addListener(RispostaFormDialog.SaveEvent.class, this::retrieveRisposte);
        rispondi.setId("rispondi-button");
        miPiaceButton.addClickListener(e -> {
            try {
                catalogoControl.addMiPiace(true, recensione);
                miPiaceRetriever();
            } catch (NotLoggedException c){
                getUI().ifPresent(ui -> ui.navigate(LoginView.class));
            } catch (NotAuthorizedException c) {
                Notification.show("Non sei autorizzato a mettere mi piace");
            }
        });

        nonMiPiaceButton.addClickListener(e -> {
            try {
                catalogoControl.addMiPiace(false, recensione);
                miPiaceRetriever();
            } catch (NotLoggedException c) {
                getUI().ifPresent(ui -> ui.navigate(LoginView.class));
            } catch (NotAuthorizedException c){
                Notification.show("Non sei autorizzato a mettere non mi piace");
            }
        });

        miPiaceButton.setId("mipiace-button");
        nonMiPiaceButton.setId("nonmipiace-button");
        numMiPiace.setId("mipiace-number");
        numNonMiPiace.setId("nonmipiace-number");

        rispondi.setIcon(new Icon(REPLY));
        rispondi.addClickListener(buttonClickEvent -> {
            rispostaFormDialog.open();
        });

        segnalaRec.setIcon(new Icon(CLOSE_CIRCLE_O));
        try {
            segnalaRec.setEnabled(moderazioneControl.isSegnalated(recensione));
        } catch (NotAuthorizedException e) {
            segnalaRec.setEnabled(false);
        }
        segnalaRec.addClickListener(buttonClickEvent -> {
            try {
                segnalaRec.setEnabled(false);
                moderazioneControl.addSegnalazione(recensione);
            } catch (NotAuthorizedException e) {
                Notification.show("Non puoi segnalarti da solo ;)");
            } catch (InvalidBeanException e) {
                Notification.show("Si è verificato un errore nel costruttore");
            }
        });

        divRecensionePadre.setClassName("recensione");
        setWidthFull();
        addAttachListener(e -> prepare());



    }


    private Div popcornHandler(Integer voto) {
        Div d = new Div();
        d.setClassName("punteggio-film-div");
        for(int i = 0; i < voto; i++) {
            d.add(new Image("images/popcorn.png", "popcorn punteggio" + voto));
        }

        return d;
    }


    private void retrieveRisposte(RispostaFormDialog.SaveEvent event) {
        try {
            catalogoControl.rispondiARecensione(event.getRecensione(), recensione.getId());
            recensione = catalogoControl.requestRecensioneById(recensione.getId());
            prepareRisposteDiv();
        } catch (NotLoggedException e){
            this.getUI().ifPresent(ui -> ui.navigate(LoginView.class));
        } catch (NotAuthorizedException e) {
            Notification.show("Non sei autorizzato a rispondere ad una recensione");
        } catch (InvalidBeanException | BeanNotExsistException e) {
            Notification.show("Si è verificato un errore nella retrieveRisposte");
            e.printStackTrace();
        }
    }



    private void prepare() {
        prepareRecensioneDiv();
        prepareRisposteDiv();
        miPiaceRetriever();
    }

    private void prepareRisposteDiv() {
        if(!recensione.getListaRisposte().isEmpty()) {
            remove(divRiposte);
            divRiposte.removeAll();
            VerticalLayout v = new VerticalLayout();
            for(Recensione risposta : recensione.getListaRisposte()) {
                    Image freccia = new Image("images/freccia.png", "freccia");
                    freccia.setClassName("freccia");
                    Button segnalaRis = new Button("segnala");
                    segnalaRis.setIcon(new Icon(CLOSE_CIRCLE_O));
                    try {
                        segnalaRis.setEnabled(moderazioneControl.isSegnalated(risposta));
                    } catch (NotAuthorizedException e) {
                        segnalaRis.setEnabled(false);
                    }
                    segnalaRis.addClickListener(buttonClickEvent -> {
                        try {
                            moderazioneControl.addSegnalazione(risposta);
                        } catch (NotAuthorizedException e) {
                            Notification.show("Non puoi segnalarti da solo ;)");
                        } catch (InvalidBeanException e) {
                            Notification.show("Non sei autorizzato");
                        }
                        segnalaRis.setEnabled(false);
                    });
                    Div ripostaDiv = new Div();
                    ripostaDiv.setClassName("risposta-rec");
                    Paragraph username = new Paragraph(risposta.getRecensore().getUsername());
                    username.getStyle().set("font-weight", "bold");
                    Paragraph inRispostaA = new Paragraph(risposta.getPadre().getRecensore().getUsername());
                    inRispostaA.setClassName("inrispostaa");
                    Paragraph contenuto = new Paragraph(risposta.getContenuto());
                    contenuto.setClassName("contenuto");
                    HorizontalLayout h = new HorizontalLayout(new Icon(USER), username, segnalaRis);
                    h.setAlignItems(Alignment.CENTER);
                    VerticalLayout ver = new VerticalLayout(h, contenuto);
                    ripostaDiv.add(h, ver);
                    HorizontalLayout hor = new HorizontalLayout(freccia, ripostaDiv);
                    v.add(hor);

            }
            divRiposte.add(v);
            add(divRiposte);
        }
    }


    private void miPiaceRetriever() {

        MiPiace miPiace = null;
        try {
            miPiace = catalogoControl.findMiPiaceById(recensione);
            if (miPiace.isTipo()) {
                miPiaceButton.setIcon(new Icon(THUMBS_UP));
                nonMiPiaceButton.setIcon(new Icon(THUMBS_DOWN_O));
            } else {
                miPiaceButton.setIcon(new Icon(THUMBS_UP_O));
                nonMiPiaceButton.setIcon(new Icon(THUMBS_DOWN));
            }
        } catch (NotAuthorizedException e) {
            miPiaceButton.setIcon(new Icon(THUMBS_UP_O));
            nonMiPiaceButton.setIcon(new Icon(THUMBS_DOWN_O));
            miPiaceButton.setEnabled(false);
            nonMiPiaceButton.setEnabled(false);
        } catch (InvalidBeanException e) {
            Notification.show("Si è verificato un errore miPiaceRetriever");
        } catch (BeanNotExsistException e) {
            miPiaceButton.setIcon(new Icon(THUMBS_UP_O));
            nonMiPiaceButton.setIcon(new Icon(THUMBS_DOWN_O));
        } catch (NotLoggedException e) {
            miPiaceButton.setIcon(new Icon(THUMBS_UP_O));
            nonMiPiaceButton.setIcon(new Icon(THUMBS_DOWN_O));
        }
        numMiPiace.setText(catalogoControl.getNumeroMiPiaceOfRecensione(recensione) + "");
        numNonMiPiace.setText(catalogoControl.getNumeroNonMiPiaceOfRecensione(recensione) + "");
    }

    private void prepareRecensioneDiv() {
        divRecensionePadre.removeAll();
        System.out.println("PREPARE RECENSIONE DIV");
        Paragraph username = new Paragraph(recensione.getRecensore().getUsername());
        username.getStyle().set("font-weight", "bold");
        Paragraph contenuto = new Paragraph(recensione.getContenuto());
        contenuto.setClassName("contenuto");
        Integer valutazione = recensione.getPunteggio();
        HorizontalLayout h = new HorizontalLayout(new Icon(USER), username, popcornHandler(valutazione), miPiaceButton, numMiPiace, nonMiPiaceButton, numNonMiPiace, segnalaRec);
        h.setAlignItems(Alignment.CENTER);
        VerticalLayout v = new VerticalLayout(h, contenuto, rispondi);
        divRecensionePadre.add(v);
        add(divRecensionePadre);
    }

}

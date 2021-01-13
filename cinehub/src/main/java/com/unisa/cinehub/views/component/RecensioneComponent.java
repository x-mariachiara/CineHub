package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.ModerazioneControl;
import com.unisa.cinehub.data.entity.*;
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
import org.aspectj.weaver.ast.Not;

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
        this.catalogoControl = catalogoControl;
        this.recensione = recensione;
        this.moderazioneControl = moderazioneControl;
        rispostaFormDialog.addListener(RispostaFormDialog.SaveEvent.class, this::retrieveRisposte);

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

        rispondi.setIcon(new Icon(REPLY));
        rispondi.addClickListener(buttonClickEvent -> {
            rispostaFormDialog.open();
        });

        segnalaRec.setIcon(new Icon(CLOSE_CIRCLE_O));
        segnalaRec.setEnabled(moderazioneControl.isSegnalated(recensione));
        segnalaRec.addClickListener(buttonClickEvent -> {
            moderazioneControl.addSegnalazione(recensione);
            segnalaRec.setEnabled(false);
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
                if(!risposta.getRecensore().getBannato()) {
                    Image freccia = new Image("images/freccia.png", "freccia");
                    freccia.setClassName("freccia");
                    Button segnalaRis = new Button("segnala");
                    segnalaRis.setIcon(new Icon(CLOSE_CIRCLE_O));
                    segnalaRis.setEnabled(moderazioneControl.isSegnalated(risposta));
                    segnalaRis.addClickListener(buttonClickEvent -> {
                        moderazioneControl.addSegnalazione(risposta);
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
            }
            divRiposte.add(v);
            add(divRiposte);
        }
    }


    private void miPiaceRetriever() {
        MiPiace miPiace = catalogoControl.findMyPiaceById(recensione);
        if (miPiace != null){
            if (miPiace.isTipo()) {
                miPiaceButton.setIcon(new Icon(THUMBS_UP));
                nonMiPiaceButton.setIcon(new Icon(THUMBS_DOWN_O));
            } else {
                miPiaceButton.setIcon(new Icon(THUMBS_UP_O));
                nonMiPiaceButton.setIcon(new Icon(THUMBS_DOWN));
            }
        } else {
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

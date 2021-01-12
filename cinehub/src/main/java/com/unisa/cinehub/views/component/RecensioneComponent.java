package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.unisa.cinehub.views.component.form.RispostaFormDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

public class RecensioneComponent extends VerticalLayout {

    private CatalogoControl catalogoControl;
    private RispostaFormDialog rispostaFormDialog = new RispostaFormDialog();;
    private Div divRecensionePadre = new Div();
    private Div divRiposte = new Div();

    private Button miPiaceButton = new Button();
    private Button nonMiPiaceButton = new Button();
    private Button rispondi = new Button("rispondi");
    private Paragraph numMiPiace = new Paragraph();
    private Paragraph numNonMiPiace = new Paragraph();
    private Recensione recensione;

    public RecensioneComponent(Recensione recensione, CatalogoControl catalogoControl) {
        this.catalogoControl = catalogoControl;
        this.recensione = recensione;
        rispostaFormDialog.addListener(RispostaFormDialog.SaveEvent.class, this::retrieveRisposte);
        miPiaceButton.addClickListener(e -> {
            catalogoControl.addMiPiace(true, recensione);
            miPiaceRetriever();
        });

        nonMiPiaceButton.addClickListener(e -> {
            catalogoControl.addMiPiace(false, recensione);
            miPiaceRetriever();
        });

        rispondi.addClickListener(buttonClickEvent -> {
            rispostaFormDialog.open();
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
        catalogoControl.rispondiARecensione(event.getRecensione(), recensione.getId());
        recensione = catalogoControl.requestRecensioneById(recensione.getId());
        prepareRisposteDiv();
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
                Div ripostaDiv = new Div();
                ripostaDiv.setClassName("");
                ripostaDiv.add(new Paragraph(risposta.getContenuto()));
                v.add(ripostaDiv);
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
        HorizontalLayout h = new HorizontalLayout(new Icon(USER), username, popcornHandler(valutazione), miPiaceButton, numMiPiace, nonMiPiaceButton, numNonMiPiace);
        h.setAlignItems(Alignment.CENTER);
        VerticalLayout v = new VerticalLayout(h, contenuto, rispondi);
        divRecensionePadre.add(v);
        add(divRecensionePadre);
    }

}

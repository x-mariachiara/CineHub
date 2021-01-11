package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.MiPiace;
import com.unisa.cinehub.data.entity.Recensione;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

public class RecensioneComponent  extends Div {

    @Autowired
    private CatalogoControl catalogoControl;

    private Button miPiace;
    private Button nonMiPiace;
    private Paragraph numMiPiace;
    private Paragraph numNonMiPiace;

    public RecensioneComponent(Recensione recensione, CatalogoControl catalogoControl) {
        this.catalogoControl = catalogoControl;
        miPiace = new Button();
        nonMiPiace = new Button();
        numMiPiace = new Paragraph();
        numNonMiPiace = new Paragraph();
        bindMiPiaceEvent(recensione);

        addAttachListener(e -> prepare(recensione, miPiace, nonMiPiace, numMiPiace, numNonMiPiace));


        setClassName("recensione");
        Paragraph username = new Paragraph(recensione.getRecensore().getUsername());
        username.getStyle().set("font-weight", "bold");
        Paragraph contenuto = new Paragraph(recensione.getContenuto());
        contenuto.setClassName("contenuto");
        Integer valutazione = recensione.getPunteggio();
        HorizontalLayout h = new HorizontalLayout(new Icon(USER), username, popcornHandler(valutazione), miPiace, numMiPiace, nonMiPiace, numNonMiPiace);
        h.setAlignItems(FlexComponent.Alignment.CENTER);
        VerticalLayout v = new VerticalLayout(h, contenuto, rispondi());
        add(v);
    }

    private Div popcornHandler(Integer voto) {
        Div d = new Div();
        d.setClassName("punteggio-film-div");
        for(int i = 0; i < voto; i++) {
            d.add(new Image("images/popcorn.png", "popcorn punteggio" + voto));
        }

        return d;
    }

    private Button rispondi(){
        Button b = new Button("Rispondi", buttonClickEvent -> {

        });
        b.setIcon(new Icon(COMMENT_O));
        return b;
    }

    /**
     * Associa la logica a i bottoni del mipiace e nonmipiace
     * @param recensione recensione a cui mettere il mi piace
     */
    private void bindMiPiaceEvent(Recensione recensione){
        miPiace.addClickListener(buttonClickEvent -> {
                catalogoControl.addMiPiace(true, recensione);
                prepare(recensione, miPiace, nonMiPiace, numMiPiace, numNonMiPiace);
            });
        nonMiPiace.addClickListener(buttonClickEvent -> {
                catalogoControl.addMiPiace(false, recensione);
                prepare(recensione, miPiace, nonMiPiace, numMiPiace, numNonMiPiace);
            });
    }


    /**
     * fa in modo che al caricamento del componente recensione se l'utente loggato ha messo mi piace o non mi piace ad una recensione
     * questa apparirà già con l'icona adatta.
     * @param recensione recensione da controllare
     * @param buttonMiPiace bottoneMiPiace da aggiornare
     * @param buttonNonMiPiace bottone nonMiPiace da aggionrare
     */
    private void prepare(Recensione recensione, Button buttonMiPiace, Button buttonNonMiPiace, Paragraph numMiPiace, Paragraph numNonMiPiace) {
        MiPiace miPiace = catalogoControl.findMyPiaceById(recensione);
        if (miPiace != null){
            if (miPiace.isTipo()) {
                buttonMiPiace.setIcon(new Icon(THUMBS_UP));
                buttonNonMiPiace.setIcon(new Icon(THUMBS_DOWN_O));
            } else {
                buttonMiPiace.setIcon(new Icon(THUMBS_UP_O));
                buttonNonMiPiace.setIcon(new Icon(THUMBS_DOWN));
            }
        } else {
            buttonMiPiace.setIcon(new Icon(THUMBS_UP_O));
            buttonNonMiPiace.setIcon(new Icon(THUMBS_DOWN_O));
        }
        numMiPiace.setText(catalogoControl.getNumeroMiPiaceOfRecensione(recensione) + "");
        numNonMiPiace.setText(catalogoControl.getNumeroNonMiPiaceOfRecensione(recensione) + "");
    }

}

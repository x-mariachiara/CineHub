package com.unisa.cinehub.views.user.moderatorerecensioni;

import com.unisa.cinehub.data.entity.Recensione;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import org.springframework.security.core.parameters.P;

public class ReviewDialog extends Dialog {

    private  Recensione recensione;
    private Paragraph contenutoRecensione = new Paragraph();
    private Button eliminaRecensione = new Button("Elimina Recensione");

    public ReviewDialog(Recensione recensione) {
        VerticalLayout ver = new VerticalLayout(contenutoRecensione, eliminaRecensione);
        eliminaRecensione.setIcon(new Icon(VaadinIcon.TRASH));
        ver.setAlignItems(FlexComponent.Alignment.CENTER);
        add(ver);
    }

    public void setRecensione(Recensione recensione) {
        if(recensione != null) {
            this.recensione = recensione;
            contenutoRecensione.setText(recensione.getContenuto());
            eliminaRecensione.addClickListener(event -> {
                System.out.println("LA MADONNA CANCELLATA");
                this.close();
                fireEvent(new DeleteEvent(this, recensione));
            });
        }
    }

    public static class DeleteEvent extends ComponentEvent<ReviewDialog> {
        private final Recensione recensione;

        public DeleteEvent(ReviewDialog source, Recensione recensione) {
            super(source, false);
            this.recensione = recensione;
        }

        public Recensione getRecensione() {
            return recensione;
        }
    }

    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}

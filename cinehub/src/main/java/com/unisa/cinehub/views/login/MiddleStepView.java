package com.unisa.cinehub.views.login;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("confirmLinkSend")
public class MiddleStepView  extends VerticalLayout {

    public MiddleStepView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(new H2("Link di conferma inviato all'indirizzo "));
    }
}

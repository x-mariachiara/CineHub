package com.unisa.cinehub.views.login;

import com.unisa.cinehub.views.homepage.HomepageView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route("confirmLinkSend")
public class MiddleStepView  extends VerticalLayout implements HasUrlParameter<String> {


    public MiddleStepView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter){
        Button tornaAllaHome = new Button("Torna alla Home", buttonClickEvent -> {
            getUI().ifPresent(ui -> ui.navigate(HomepageView.class));
        });
        add(new H2("Link di conferma inviato all'indirizzo " + parameter), tornaAllaHome);
    }

}

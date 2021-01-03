package com.unisa.cinehub.views.login;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.servlet.View;

@Route("successRegister")
public class SuccessRegister extends VerticalLayout {

    public SuccessRegister() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(new H2("Ti sei registrato con successo"));
    }

}

package com.unisa.cinehub.views.login;

import com.unisa.cinehub.views.homepage.HomepageView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.web.servlet.View;

@Route("successRegister")
public class SuccessRegister extends VerticalLayout {

    public SuccessRegister() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(new H2("Ti sei registrato con successo"), new RouterLink("Torna alla home", HomepageView.class));
    }

}

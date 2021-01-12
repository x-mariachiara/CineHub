package com.unisa.cinehub.views.user;

import com.unisa.cinehub.control.UtenteControl;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("profilo")
@PageTitle("Profilo")
public class ProfiloView extends VerticalLayout {

    @Autowired
    private UtenteControl utenteControl;
    private Recensore recensore;

    public ProfiloView() {
        setId("profilo-view");
        addAttachListener(e -> prepare());
    }

    private void prepare() {
        recensore = utenteControl.getRecensoreLoggato();
        H1 benvenuto = new H1("Benvenuto/a " + recensore.getNome() + " " + recensore.getCognome());
        add(benvenuto);
    }
}

package com.unisa.cinehub.security;

import com.unisa.cinehub.views.homepage.HomepageView;
import com.unisa.cinehub.views.login.LoginView;
import com.unisa.cinehub.views.login.MiddleStepView;
import com.unisa.cinehub.views.login.RegisterView;
import com.unisa.cinehub.views.login.SuccessRegister;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {


            final UI ui = uiEvent.getUI();
            ((UI) ui).addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    private void authenticateNavigation(BeforeEnterEvent event) {
        //Aggiungere classi dove non controllare l'accesso
        if (!(LoginView.class.equals(event.getNavigationTarget()) ||
                RegisterView.class.equals(event.getNavigationTarget()) ||
                MiddleStepView.class.equals(event.getNavigationTarget()) ||
                SuccessRegister.class.equals(event.getNavigationTarget()) ||
                HomepageView.class.equals(event.getNavigationTarget())) &&
                !SecurityUtils.isUserLoggedIn()) {

            event.rerouteTo(LoginView.class);
        }
    }
}
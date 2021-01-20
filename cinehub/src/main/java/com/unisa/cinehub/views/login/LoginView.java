package com.unisa.cinehub.views.login;

import com.unisa.cinehub.views.component.LoginComponent;
import com.unisa.cinehub.views.homepage.HomepageView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route("login")
@PageTitle("Login | CineHub")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginComponent();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        add(new H1("CineHub Login"), login, new H2(new RouterLink("Registrati", RegisterView.class)), new H3(new RouterLink("Torna alla homepage", HomepageView.class)));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}

package com.unisa.cinehub.views.component;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;

public class LoginComponent extends LoginForm {

    private LoginI18n loginI18n = LoginI18n.createDefault();

    public LoginComponent() {
        super();
        loginI18n.getForm().setUsername("Email");
        loginI18n.getForm().setForgotPassword("");
        this.setI18n(loginI18n);
    }


}

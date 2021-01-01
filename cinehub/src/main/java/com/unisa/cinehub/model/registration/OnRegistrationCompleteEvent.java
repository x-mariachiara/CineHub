package com.unisa.cinehub.model.registration;

import com.unisa.cinehub.data.entity.Utente;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private Locale locale;
    private Utente utente;

    public OnRegistrationCompleteEvent(Utente utenteRegistrato, Locale locale, String appUrl) {
        super(utenteRegistrato);

        this.utente = utenteRegistrato;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

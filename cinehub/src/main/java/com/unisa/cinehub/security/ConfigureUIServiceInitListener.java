package com.unisa.cinehub.security;

import com.unisa.cinehub.data.entity.Moderatore;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.ResponsabileCatalogo;
import com.unisa.cinehub.data.entity.Utente;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.views.film.FilmView;
import com.unisa.cinehub.views.film.InfoFilmView;
import com.unisa.cinehub.views.homepage.HomepageView;
import com.unisa.cinehub.views.login.LoginView;
import com.unisa.cinehub.views.login.MiddleStepView;
import com.unisa.cinehub.views.login.RegisterView;
import com.unisa.cinehub.views.login.SuccessRegister;
import com.unisa.cinehub.views.main.MainView;
import com.unisa.cinehub.views.puntata.PuntataView;
import com.unisa.cinehub.views.risultati.RisultatiRicercaView;
import com.unisa.cinehub.views.serietv.InfoSerieTvView;
import com.unisa.cinehub.views.serietv.SerieTvView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminCastView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminFilmView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminPuntataView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminSerieTvView;
import com.unisa.cinehub.views.user.moderatoreaccount.ModeratoreAccountView;
import com.unisa.cinehub.views.user.moderatorerecensioni.ModeraRecensioniView;
import com.unisa.cinehub.views.user.recensore.ProfiloView;
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

        if(SecurityUtils.isUserLoggedIn()) {
            Utente utente = null;
            try {
                utente = SecurityUtils.getLoggedIn();
            } catch (InvalidBeanException | BeanNotExsistException e) {
                event.rerouteTo(HomepageView.class);
            }
            if(ModeratoreAccountView.class.equals(event.getNavigationTarget())) {
                if(!(utente instanceof Moderatore
                && ((Moderatore) utente).getTipo().equals(Moderatore.Tipo.MODACCOUNT))){
                    event.rerouteTo(HomepageView.class);
                }
            } else if(ModeraRecensioniView.class.equals(event.getNavigationTarget())) {
                if(!(utente instanceof Moderatore
                        && ((Moderatore) utente).getTipo().equals(Moderatore.Tipo.MODCOMMENTI))){
                    event.rerouteTo(HomepageView.class);
                }
            } else if(AdminSerieTvView.class.equals(event.getNavigationTarget())
                    || AdminFilmView.class.equals(event.getNavigationTarget())
                    || AdminPuntataView.class.equals(event.getNavigationTarget())
                    || AdminCastView.class.equals(event.getNavigationTarget())) {
                if (!(utente instanceof ResponsabileCatalogo)) {
                    event.rerouteTo(HomepageView.class);
                }
            } else if(ProfiloView.class.equals(event.getNavigationTarget())) {
                if (!(utente instanceof Recensore)) {
                    event.rerouteTo(HomepageView.class);
                }
            }
        } else {
            if (!(LoginView.class.equals(event.getNavigationTarget()) ||
                    RegisterView.class.equals(event.getNavigationTarget()) ||
                    MiddleStepView.class.equals(event.getNavigationTarget()) ||
                    SuccessRegister.class.equals(event.getNavigationTarget()) ||
                    SerieTvView.class.equals(event.getNavigationTarget()) ||
                    FilmView.class.equals(event.getNavigationTarget()) ||
                    InfoSerieTvView.class.equals(event.getNavigationTarget()) ||
                    InfoFilmView.class.equals(event.getNavigationTarget()) ||
                    MainView.class.equals(event.getNavigationTarget()) ||
                    PuntataView.class.equals(event.getNavigationTarget()) ||
                    RisultatiRicercaView.class.equals(event.getNavigationTarget()) ||
                    HomepageView.class.equals(event.getNavigationTarget()))) {
                event.rerouteTo(LoginView.class);
            }
        }
    }
}
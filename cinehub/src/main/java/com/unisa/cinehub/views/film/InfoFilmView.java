package com.unisa.cinehub.views.film;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.control.ModerazioneControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.views.component.InfoMediaComponent;
import com.unisa.cinehub.views.component.RecensioniSectionComponent;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "infofilm", layout = MainView.class)
@PageTitle("info film")
public class InfoFilmView extends Div implements HasUrlParameter<Long> {
    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;
    @Autowired
    private CatalogoControl catalogoControl;
    @Autowired
    private ModerazioneControl moderazioneControl;

    public InfoFilmView() {
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, Long mediaId){
        try {
            Media media = catalogoControl.findFilmById(mediaId);
            Film film = (Film) media;
            RecensioniSectionComponent recensioniSectionComponent = new RecensioniSectionComponent(film, catalogoControl, gestioneCatalogoControl, moderazioneControl);
            add(new InfoMediaComponent(film), recensioniSectionComponent);
        } catch (InvalidBeanException e) {
            Notification.show("Si è verificato un errore");
        } catch (BeanNotExsistException e) {
            Notification.show("Il film non esiste");
        }


    }


}

package com.unisa.cinehub.views.film;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.views.component.InfoMediaComponent;
import com.unisa.cinehub.views.component.RecensioniSectionComponent;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
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

    public InfoFilmView(GestioneCatalogoControl gestioneCatalogoControl, CatalogoControl catalogoControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        this.catalogoControl = catalogoControl;
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, Long mediaId){
        Media media = gestioneCatalogoControl.findFilmById(mediaId);
        Film film = (Film) media;
        add(new InfoMediaComponent(film), new RecensioniSectionComponent(film, catalogoControl));

    }


}

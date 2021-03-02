package com.unisa.cinehub.views.homepage;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.control.IAControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.views.component.CardScrollContainer;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "home", layout = MainView.class)
@PageTitle("Homepage")
@RouteAlias(value = "", layout = MainView.class)
public class HomepageView extends Div {

    @Autowired
    private CatalogoControl catalogoControl;

    @Autowired
    private IAControl iaControl;

    public HomepageView() {
        setId("homepage-view");
        addAttachListener(e -> prepare());
    }

    private void prepare(){
        List<Media> mostRecentMedia = catalogoControl.findMostRecentMedia(5);
        List<Media> mostVotedMedia = catalogoControl.findMostVoted();
        Film consigliato = iaControl.consigliaFilm();
        CardScrollContainer contenuti_più_recenti = new CardScrollContainer(mostRecentMedia, "Contenuti più recenti");
        CardScrollContainer contenuti_più_votati = new CardScrollContainer(mostVotedMedia, "Contenuti più votati");
        contenuti_più_recenti.addClassName("contenuti-home");
        contenuti_più_votati.addClassName("contenuti-home");



        add(contenuti_più_recenti, contenuti_più_votati);
    }
}

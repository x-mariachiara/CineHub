package com.unisa.cinehub.views.homepage;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.views.component.CardScrollContainer;
import com.unisa.cinehub.views.component.SearchForTitle;
import com.unisa.cinehub.views.risultati.RisultatiRicercaTitoloView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "home", layout = MainView.class)
@PageTitle("Homepage")
@RouteAlias(value = "", layout = MainView.class)
public class HomepageView extends Div {

    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;

    public HomepageView() {
        setId("homepage-view");
        addAttachListener(e -> prepare());
    }

    private void prepare(){
        add(new SearchForTitle());
        List<Film> film = gestioneCatalogoControl.findAllFilm();
        List<SerieTv> serieTv = gestioneCatalogoControl.findAllSerieTv();
        List<Media> media = new ArrayList<>();
        media.addAll(film);
        media.addAll(serieTv);

        List<Media> mostRecentMedia = gestioneCatalogoControl.findMostRecentMedia(5);
        List<Media> mostVotedMedia = gestioneCatalogoControl.findMostVoted();

        CardScrollContainer contenuti_più_recenti = new CardScrollContainer(mostRecentMedia, "Contenuti più recenti");
        CardScrollContainer contenuti_più_votati = new CardScrollContainer(mostVotedMedia, "Contenuti più votati");


        add(contenuti_più_recenti, contenuti_più_votati);
    }
}

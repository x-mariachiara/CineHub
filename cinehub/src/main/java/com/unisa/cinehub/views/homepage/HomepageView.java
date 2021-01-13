package com.unisa.cinehub.views.homepage;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.views.component.CardContainerComponent;
import com.unisa.cinehub.views.component.CardMedia;
import com.unisa.cinehub.views.component.CardScrollContainer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.charts.model.Pane;
import com.vaadin.flow.component.charts.model.PaneList;
import com.vaadin.flow.component.charts.model.Scrollbar;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
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

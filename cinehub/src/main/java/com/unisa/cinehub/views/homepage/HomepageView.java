package com.unisa.cinehub.views.homepage;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.control.IAControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.views.component.CardConsigliato;
import com.unisa.cinehub.views.component.CardScrollContainer;
import com.unisa.cinehub.views.component.LocandinaComponent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random random = new Random();

        VerticalLayout v = new VerticalLayout();
        HorizontalLayout h = new HorizontalLayout();
        CardConsigliato cardConsigliato;
        CardScrollContainer contenuti_più_recenti = new CardScrollContainer(mostRecentMedia, "Contenuti più recenti");
        CardScrollContainer contenuti_più_votati = new CardScrollContainer(mostVotedMedia.subList(0, 5), "Contenuti più votati");
        contenuti_più_recenti.addClassName("contenuti-home");
        contenuti_più_votati.addClassName("contenuti-home");
        v.setWidth("70%");
        v.setHeight("100%");

        if(consigliato != null)
            cardConsigliato = new CardConsigliato(consigliato);
        else
            cardConsigliato = new CardConsigliato((Film) mostVotedMedia.get(random.nextInt(mostVotedMedia.size())));
        v.add(contenuti_più_recenti, contenuti_più_votati);

        h.add(v, cardConsigliato);

        add(h);
    }
}

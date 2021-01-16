package com.unisa.cinehub.views.film;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Film;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.views.component.CardContainerComponent;
import com.unisa.cinehub.views.component.CardMedia;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.unisa.cinehub.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "film", layout = MainView.class)
@PageTitle("Film")
public class FilmView extends Div {

    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;

    public FilmView() {
        setId("film-view");
        addAttachListener(e -> prepare());
    }

    private void prepare(){
        List<Media> film = new ArrayList<>(gestioneCatalogoControl.findAllFilm());
        CardContainerComponent kinder = new CardContainerComponent(film);
        add(kinder);
    }
}


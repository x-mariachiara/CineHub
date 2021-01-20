package com.unisa.cinehub.views.film;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.views.component.CardContainerComponent;
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
    private CatalogoControl catalogoControl;

    public FilmView() {
        setId("film-view");
        addAttachListener(e -> prepare());
    }

    private void prepare(){
        List<Media> film = new ArrayList<>(catalogoControl.findAllFilm());
        CardContainerComponent kinder = new CardContainerComponent(film);
        add(kinder);
    }
}


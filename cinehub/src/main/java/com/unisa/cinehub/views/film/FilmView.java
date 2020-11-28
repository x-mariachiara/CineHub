package com.unisa.cinehub.views.film;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.unisa.cinehub.views.main.MainView;

@Route(value = "film", layout = MainView.class)
@PageTitle("Film")
public class FilmView extends Div {

    public FilmView() {
        setId("film-view");
        add(new Text("Content placeholder"));
    }

}

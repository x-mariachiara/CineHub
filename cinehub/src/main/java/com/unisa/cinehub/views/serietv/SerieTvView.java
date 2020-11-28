package com.unisa.cinehub.views.serietv;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.unisa.cinehub.views.main.MainView;

@Route(value = "serietv", layout = MainView.class)
@PageTitle("Serie Tv")
public class SerieTvView extends Div {

    public SerieTvView() {
        setId("serie-tv-view");
        add(new Text("Content placeholder"));
    }

}

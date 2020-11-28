package com.unisa.cinehub.views.homepage;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "home", layout = MainView.class)
@PageTitle("Homepage")
@RouteAlias(value = "", layout = MainView.class)
public class HomepageView extends Div {

    public HomepageView() {
        setId("homepage-view");
        add(new Text("Content placeholder"));
    }

}

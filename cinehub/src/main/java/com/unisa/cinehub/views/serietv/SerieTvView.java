package com.unisa.cinehub.views.serietv;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.views.component.CardContainerComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.unisa.cinehub.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "serietv", layout = MainView.class)
@PageTitle("Serie Tv")
public class SerieTvView extends Div {

    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;

    public SerieTvView(GestioneCatalogoControl gestioneCatalogoControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        setId("serie-tv-view");
        List<Media> serieTvs = new ArrayList<>(gestioneCatalogoControl.findAllSerieTv());
        CardContainerComponent kinder = new CardContainerComponent(serieTvs);
        add(kinder);

    }

}

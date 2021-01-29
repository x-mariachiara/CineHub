package com.unisa.cinehub.views.serietv;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.views.component.CardContainerComponent;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "serietv", layout = MainView.class)
@PageTitle("Serie Tv")
public class SerieTvView extends Div {

    @Autowired
    private CatalogoControl catalogoControl;

    public SerieTvView() {
        setId("serie-tv-view");
        addAttachListener(e -> prepare());
    }

    private void prepare(){
        List<Media> serieTvs = new ArrayList<>(catalogoControl.findAllSerieTv());
        CardContainerComponent kinder = new CardContainerComponent(serieTvs);
        add(kinder);
    }

}

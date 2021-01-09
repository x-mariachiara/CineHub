package com.unisa.cinehub.views.serietv;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Media;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.views.component.InfoMediaComponent;
import com.unisa.cinehub.views.component.StagioneSection;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "infoserietv", layout = MainView.class)
@PageTitle("Info Serie TV")
public class InfoSerieTvView extends Div implements HasUrlParameter<Long> {

    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;
    @Autowired
    private CatalogoControl catalogoControl;

    public InfoSerieTvView(GestioneCatalogoControl gestioneCatalogoControl, CatalogoControl catalogoControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        this.catalogoControl = catalogoControl;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        SerieTv serieTv = gestioneCatalogoControl.findSerieTvById(id);
        add(new InfoMediaComponent(serieTv), new StagioneSection(gestioneCatalogoControl, serieTv));
    }
}

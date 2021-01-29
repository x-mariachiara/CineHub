package com.unisa.cinehub.views.serietv;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.views.component.InfoMediaComponent;
import com.unisa.cinehub.views.component.StagioneSection;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "infoserietv", layout = MainView.class)
@PageTitle("Info Serie TV")
public class InfoSerieTvView extends Div implements HasUrlParameter<Long> {


    @Autowired
    private CatalogoControl catalogoControl;

    public InfoSerieTvView() {
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        SerieTv serieTv = new SerieTv();
        try {
            serieTv = catalogoControl.findSerieTvById(id);
            add(new InfoMediaComponent(serieTv), new StagioneSection(catalogoControl, serieTv));
        } catch (InvalidBeanException e) {
            Notification.show("Si è verificato un errore");
        } catch (BeanNotExsistException e) {
            Notification.show("La serieTV non esiste");
        }
    }
}

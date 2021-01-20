package com.unisa.cinehub.views.puntata;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.control.ModerazioneControl;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.Stagione;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.views.component.InfoPuntataComponent;
import com.unisa.cinehub.views.component.RecensioniSectionComponent;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "puntata", layout = MainView.class)
@PageTitle("Puntata")
public class PuntataView extends Div implements HasUrlParameter<String> {
    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;
    @Autowired
    private CatalogoControl catalogoControl;
    @Autowired
    private ModerazioneControl moderazioneControl;

    public PuntataView(){
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String idPuntata){
        String[] divise;
        divise = idPuntata.split("-");
        Stagione.StagioneID stagioneID = new Stagione.StagioneID();
        stagioneID.setNumeroStagione(Integer.parseInt(divise[1]));
        stagioneID.setSerieTvId(Long.parseLong(divise[2]));
        Puntata.PuntataID puntataID = new Puntata.PuntataID();
        puntataID.setNumeroPuntata(Integer.parseInt(divise[0]));
        puntataID.setStagioneId(stagioneID);
        Puntata puntata = new Puntata();
        try {
            puntata = catalogoControl.findPuntataById(puntataID);
        } catch (BeanNotExsistException | InvalidBeanException e) {
            Notification.show("Puntata non esiste");
        }
        add(new InfoPuntataComponent(puntata), new RecensioniSectionComponent(puntata, catalogoControl, gestioneCatalogoControl, moderazioneControl));

    }


}

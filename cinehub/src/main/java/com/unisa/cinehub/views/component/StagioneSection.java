package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class StagioneSection extends VerticalLayout {

    CatalogoControl catalogoControl;
    SerieTv serie;
    List<Puntata> puntate;
    ContainerPuntate containerPuntate;

    public StagioneSection(CatalogoControl catalogoControl, SerieTv serie) throws InvalidBeanException, BeanNotExsistException {
        this.catalogoControl = catalogoControl;
        this.serie = serie;
        prepare();
    }

    private void prepare() throws InvalidBeanException, BeanNotExsistException {
        if(serie.getStagioni().isEmpty()) {
            add(new H3("Ancora nessuna puntata"));
        } else {
            ComboBox<Stagione> comboBox = createComboBox();
            puntate = catalogoControl.puntateByStagione(serie.getId(), comboBox.getValue().getNumeroStagione());
            containerPuntate = new ContainerPuntate(puntate);
            containerPuntate.setId("container-puntate");
            //getStyle().set("margin", "0 auto");
            addClassName("container-puntate");
            add(comboBox, containerPuntate);
        }
    }

    private ComboBox<Stagione> createComboBox() {
        ComboBox<Stagione> comboBox = new ComboBox<>();
        comboBox.setItems(serie.getStagioni());
        comboBox.setItemLabelGenerator(Stagione::getNomeStagione);
        comboBox.setValue(serie.getStagioni().iterator().next());
        comboBox.setAllowCustomValue(false);
        comboBox.addValueChangeListener(e -> {
            remove(containerPuntate);
            try {
                puntate = catalogoControl.puntateByStagione(serie.getId(), e.getValue().getNumeroStagione());
            } catch (InvalidBeanException invalidBeanException) {
                Notification.show("Si è verificato un errore");
            } catch (BeanNotExsistException beanNotExsistException) {
                Notification.show("Puntata non esiste");
            }
            containerPuntate = new ContainerPuntate(puntate);
            add(containerPuntate);
        });
        return comboBox;
    }
}


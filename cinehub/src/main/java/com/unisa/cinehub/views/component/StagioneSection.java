package com.unisa.cinehub.views.component;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Puntata;
import com.unisa.cinehub.data.entity.SerieTv;
import com.unisa.cinehub.data.entity.Stagione;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class StagioneSection extends VerticalLayout {

    GestioneCatalogoControl gestioneCatalogoControl;
    SerieTv serie;
    List<Puntata> puntate;
    ContainerPuntate containerPuntate;

    public StagioneSection(GestioneCatalogoControl gestioneCatalogoControl, SerieTv serie) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        this.serie = serie;
        prepare();
    }

    private void prepare() {
        ComboBox<Stagione> comboBox = createComboBox();
        puntate = gestioneCatalogoControl.puntateByStagione(serie.getId(), comboBox.getValue().getNumeroStagione());
        containerPuntate = new ContainerPuntate(puntate);
        containerPuntate.setId("container-puntate");
        setWidth("70%");
        getStyle().set("margin", "0 auto");
        add(comboBox, containerPuntate);
    }

    private ComboBox<Stagione> createComboBox() {
        ComboBox<Stagione> comboBox = new ComboBox<>();
        comboBox.setItems(serie.getStagioni());
        comboBox.setItemLabelGenerator(Stagione::getNomeStagione);
        comboBox.setValue(serie.getStagioni().iterator().next());
        comboBox.setAllowCustomValue(false);
        comboBox.addValueChangeListener(e -> {
            remove(containerPuntate);
            puntate = gestioneCatalogoControl.puntateByStagione(serie.getId(), e.getValue().getNumeroStagione());
            containerPuntate = new ContainerPuntate(puntate);
            add(containerPuntate);
        });
        return comboBox;
    }
}


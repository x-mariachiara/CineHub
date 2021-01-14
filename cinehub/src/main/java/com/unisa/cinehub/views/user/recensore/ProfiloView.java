package com.unisa.cinehub.views.user.recensore;

import com.unisa.cinehub.control.UtenteControl;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "profilo", layout = MainView.class)
@PageTitle("Profilo")
public class ProfiloView extends VerticalLayout {

    @Autowired
    private UtenteControl utenteControl;
    private Recensore recensore;

    public ProfiloView() {
        setId("profilo-view");
        addAttachListener(e -> prepare());
    }

    private void prepare() {
        setClassName("profilo");
        setSizeFull();
        setHeightFull();
        recensore = utenteControl.getRecensoreLoggato();
        Accordion sidebar = new Accordion();

        sidebar.add("Panel 1", new Span("Panel content"))
                .addThemeVariants(DetailsVariant.REVERSE);

        sidebar.add("Panel 2", new Span("Panel content"))
                .addThemeVariants(DetailsVariant.REVERSE);

        sidebar.add("Panel 3", new Span("Panel content"))
                .addThemeVariants(DetailsVariant.REVERSE);
        sidebar.setSizeFull();
        VerticalLayout v = new VerticalLayout( sidebar);
        v.setPadding(false);
        v.setMargin(false);
        v.getStyle().set("background-color", "red");
        VerticalLayout infoUtente = new VerticalLayout();
        H1 benvenuto = new H1("Benvenuto/a " + recensore.getNome());
        UnorderedList info = new UnorderedList();
        info.setClassName("lista-info");
        ListItem n = new ListItem(new Paragraph("Nome: "), new Text(recensore.getNome()));
        ListItem c = new ListItem(new Paragraph("Cognome: "), new Text(recensore.getCognome()));
        ListItem u = new ListItem(new Paragraph("Username: "), new Text(recensore.getUsername()));
        ListItem dn = new ListItem(new Paragraph("Data di nascita: "), new Text(String.valueOf(recensore.getDataNascita())));
        ListItem nr = new ListItem(new Paragraph("Numero recensioni effettuate: "), new Text(String.valueOf(recensore.getListaRecensioni().size())));
        ListItem nmp = new ListItem(new Paragraph("Numero mi piace ricevuti: "), new Text(String.valueOf(recensore.getListaMiPiace().size())));
        ListItem ns = new ListItem(new Paragraph("Numero seganalzioni ottenute: "), new Text(String.valueOf(recensore.getListaSegnalazioni().size())));
        info.add(n,c,u,dn,nr,nmp,ns);
        infoUtente.add(benvenuto, info);
        SplitLayout h = new SplitLayout(v, infoUtente);
        h.setSizeFull();
        h.setSplitterPosition(18);
        h.setPrimaryStyle("maxWidth", "18%");
        setMargin(false);
        setPadding(false);
        add(h);


    }
}

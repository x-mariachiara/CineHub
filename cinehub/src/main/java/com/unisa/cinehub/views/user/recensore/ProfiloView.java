package com.unisa.cinehub.views.user.recensore;

import com.unisa.cinehub.control.UtenteControl;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.security.SecurityUtils;
import com.unisa.cinehub.views.component.RicercaComponent;
import com.unisa.cinehub.views.film.FilmView;
import com.unisa.cinehub.views.homepage.HomepageView;
import com.unisa.cinehub.views.main.MainView;
import com.unisa.cinehub.views.serietv.SerieTvView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "profilo")
@PageTitle("Profilo")
public class ProfiloView extends VerticalLayout {


    private Recensore recensore;

    public ProfiloView() {
        setId("profilo-view");
        addAttachListener(e -> prepare());
    }

    private void prepare() {
        setClassName("profilo");
        setSizeFull();
        setHeightFull();
        try {
            recensore = (Recensore) SecurityUtils.getLoggedIn();
        } catch (InvalidBeanException e) {
            getUI().ifPresent(ui -> ui.navigate(HomepageView.class));
        } catch (ClassCastException e) {
            getUI().ifPresent(ui -> ui.navigate(HomepageView.class));
        }
        Accordion sidebar = new Accordion();

        Image popcorn = new Image("images/popcorn.png", "popcorn");
        popcorn.setWidth("100%");
        AccordionPanel panelpopcorn = new AccordionPanel(popcorn, null);
        panelpopcorn.setEnabled(false);
        panelpopcorn.setOpened(false);
        sidebar.add(panelpopcorn).addThemeVariants(DetailsVariant.REVERSE);
        sidebar.add("Homepage", new Span(new RouterLink("Torna alla homepage", HomepageView.class)))
                .addThemeVariants(DetailsVariant.REVERSE);

        sidebar.add("Catalogo", new VerticalLayout(
                    new Span(new RouterLink("Film", FilmView.class)),
                    new Span(new RouterLink("Serie Tv", SerieTvView.class)))
                ).addThemeVariants(DetailsVariant.REVERSE);
        RicercaComponent ricercaComponent = new RicercaComponent();
        ricercaComponent.toVertical();
        sidebar.add("Ricerca", ricercaComponent).addThemeVariants(DetailsVariant.REVERSE);

        sidebar.setSizeFull();
        VerticalLayout v = new VerticalLayout( sidebar);
        v.setPadding(false);
        v.setMargin(false);
        v.getStyle().set("background-color", "whitesmoke");
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

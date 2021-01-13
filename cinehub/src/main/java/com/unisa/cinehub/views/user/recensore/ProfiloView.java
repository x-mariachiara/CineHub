package com.unisa.cinehub.views.user.recensore;

import com.unisa.cinehub.control.UtenteControl;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        recensore = utenteControl.getRecensoreLoggato();
        VerticalLayout v = new VerticalLayout();
        H1 benvenuto = new H1("Benvenuto/a " + recensore.getNome());
        HorizontalLayout h = new HorizontalLayout();
        h.add(benvenuto);
        UnorderedList list = new UnorderedList();
        list.setClassName("lista-info");
        ListItem n = new ListItem(new Paragraph("Nome: "), new Text(recensore.getNome()));
        ListItem c = new ListItem(new Paragraph("Cognome: "), new Text(recensore.getCognome()));
        ListItem u = new ListItem(new Paragraph("Username: "), new Text(recensore.getUsername()));
        ListItem dn = new ListItem(new Paragraph("Data di nascita: "), new Text(String.valueOf(recensore.getDataNascita())));
        ListItem nr = new ListItem(new Paragraph("Numero recensioni effettuate: "), new Text(String.valueOf(recensore.getListaRecensioni().size())));
        ListItem nmp = new ListItem(new Paragraph("Numero mi piace ricevuti: "), new Text(String.valueOf(recensore.getListaMiPiace().size())));
        ListItem ns = new ListItem(new Paragraph("Numero seganalzioni ottenute: "), new Text(String.valueOf(recensore.getListaSegnalazioni().size())));
        VerticalLayout info = new VerticalLayout();
        info.add(n,c,u,dn,nr,nmp,ns);
        v.add(h, info);
        add(v);

    }
}

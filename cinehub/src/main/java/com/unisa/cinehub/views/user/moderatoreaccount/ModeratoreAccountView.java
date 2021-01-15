package com.unisa.cinehub.views.user.moderatoreaccount;


import com.unisa.cinehub.control.ModerazioneControl;
import com.unisa.cinehub.control.UtenteControl;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.views.login.LoginView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;

@Route("moderazione/account")
public class ModeratoreAccountView extends VerticalLayout {

    @Autowired
    ModerazioneControl moderazioneControl;
    @Autowired
    UtenteControl utenteControl;

    private Grid<Recensore> grid = new Grid<>(Recensore.class);

    public ModeratoreAccountView(ModerazioneControl moderazioneControl, UtenteControl utenteControl) {
        this.moderazioneControl = moderazioneControl;
        this.utenteControl = utenteControl;
        prepareGrid();
        add(grid);
        updateList();
    }

    private void prepareGrid() {
        grid.addClassName("grid-moderazione-account");
        grid.setWidth("60%");
        grid.removeAllColumns();
        grid.addColumn(recensore -> {
            return recensore.getEmail();
        }).setHeader("Email");
        grid.addColumn(recensore -> {
            return recensore.getNome();
        }).setHeader("Nome");
        grid.addColumn(recensore -> {
            return recensore.getCognome();
        }).setHeader("Cognome");
        Grid.Column<Recensore> numeroSegnalazioni = grid.addColumn(recensore -> {
            return recensore.getListaSegnalazioni().size();
        }).setHeader("Numero Segnalazioni");
        Grid.Column<Recensore> ultimaSegnalazione = grid.addColumn(recensore -> {
            int size = recensore.getListaSegnalazioni().size();
            if(size == 0) return "nessuna";
            else return recensore.getListaSegnalazioni().get(size - 1).getCreatedAt().toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        }).setHeader("Ultima Segnalazione");
        grid.addComponentColumn(recensore -> {
            Button bannaButton = new Button("banna");
            bannaButton.addClickListener(e -> {
                try {
                    moderazioneControl.bannaRecensore(recensore.getEmail());
                } catch (NotAuthorizedException etc){
                    getUI().ifPresent(ui -> ui.navigate(LoginView.class));
                } catch (InvalidBeanException invalidBeanException) {
                    Notification.show("Si è verificato un errore");
                }
                updateList();
            });
            return bannaButton;
        }).setHeader("");
        GridSortOrder<Recensore> orderNumSegnalazioni = new GridSortOrder<>(numeroSegnalazioni, SortDirection.DESCENDING);
        GridSortOrder<Recensore> orderData = new GridSortOrder<>(ultimaSegnalazione, SortDirection.DESCENDING);
        grid.sort(Arrays.asList(orderNumSegnalazioni, orderData));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        List<Recensore> recensori = utenteControl.getAllNotBannedRecensori();
        grid.setItems(recensori);
    }


}

package com.unisa.cinehub.views.user.gestoreCatalogo;

import com.unisa.cinehub.control.GestioneCatalogoControl;
import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.exception.NotAuthorizedException;
import com.unisa.cinehub.views.login.LoginView;
import com.unisa.cinehub.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@Route(value = "gestionecatalogo/cast", layout = MainView.class)
@CssImport("./styles/views/components/shared-styles.css")
public class AdminCastView extends VerticalLayout {

    @Autowired
    private GestioneCatalogoControl gestioneCatalogoControl;

    private Grid<Cast> grid = new Grid<>(Cast.class);
    private Button addCastButton = new Button("Aggiungi Cast");
    private CastForm form;
    private Cast castSelezionato;

    public AdminCastView(GestioneCatalogoControl gestioneCatalogoControl) {
        this.gestioneCatalogoControl = gestioneCatalogoControl;
        addClassName("list-view");
        setSizeFull();

        configureGrid();

        form = new CastForm();
        form.addListener(CastForm.SaveEvent.class, this::saveCast);
        form.addListener(CastForm.DeleteEvent.class, this::deleteCast);
        form.addListener(CastForm.CloseEvent.class, e -> closeEditor());
        form.addClassName("form-inserimento");
        addCastButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addCastButton.addClickListener(click -> addCast());
        addCastButton.setId("aggiungi-cast");
        SplitLayout contenuto = new SplitLayout(grid, form);
        contenuto.setSplitterPosition(60);
        contenuto.addClassName("content");
        contenuto.setSizeFull();
        HorizontalLayout hor = new HorizontalLayout(addCastButton);
        hor.addClassName("toolbar");
        add(hor, contenuto);
        updateList();
        closeEditor();

    }

    private void addCast() {
        grid.asSingleSelect().clear();
        editCast(new Cast());
    }

    private void saveCast(CastForm.CastFormEvent event)  {
        Cast daModificare = new Cast(event.getCast().getNome(), event.getCast().getCognome());
        daModificare.setId(event.getCast().getId());
        try {
            gestioneCatalogoControl.addCast(daModificare);
            updateList();
            closeEditor();
        } catch (NotAuthorizedException e) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.class));
        } catch (InvalidBeanException e) {
            Notification.show("Aggiungere Nome e Cognome");
        } catch (BeanNotExsistException e) {
            Notification.show("Si è verificato un errore");
        }
    }

    private void deleteCast(CastForm.CastFormEvent event) {
        try {
            gestioneCatalogoControl.removeCast(event.getCast().getId());
            updateList();
            closeEditor();
        } catch (NotAuthorizedException e) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.class));
        } catch (BeanNotExsistException e) {
            Notification.show("Membro del cast non presente");
            updateList();
        } catch (InvalidBeanException e) {
            Notification.show("Si è verificto un errore");
        }
    }

    private void updateList() {
        grid.setItems(gestioneCatalogoControl.findAllCast());
    }

    private void configureGrid() {
        addClassName("configure-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        Grid.Column<Cast> colonnaNomi = grid.addColumn(cast -> {
            return cast.getNome();
        }).setHeader("Nome");
        Grid.Column<Cast> colonnaCognomi = grid.addColumn(cast -> {
            return cast.getCognome();
        }).setHeader("Cognome");
        GridSortOrder<Cast> orderNome = new GridSortOrder<>(colonnaNomi, SortDirection.DESCENDING);
        GridSortOrder<Cast> orderCognome = new GridSortOrder<>(colonnaCognomi, SortDirection.DESCENDING);
        grid.sort(Arrays.asList(orderNome, orderCognome));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editCast(event.getValue()));

    }

    private void editCast(Cast value) {
        if(value == null) {
            closeEditor();
        } else {
            form.setCast(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setCast(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}

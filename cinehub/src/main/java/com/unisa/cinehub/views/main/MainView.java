package com.unisa.cinehub.views.main;

import java.util.Optional;

import com.unisa.cinehub.data.entity.*;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.security.SecurityUtils;
import com.unisa.cinehub.views.component.RicercaComponent;
import com.unisa.cinehub.views.login.LoginView;
import com.unisa.cinehub.views.login.RegisterView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminCastView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminFilmView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminPuntataView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminSerieTvView;
import com.unisa.cinehub.views.user.moderatoreaccount.ModeratoreAccountView;
import com.unisa.cinehub.views.user.moderatorerecensioni.ModeraRecensioniView;
import com.unisa.cinehub.views.user.recensore.ProfiloView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.unisa.cinehub.views.homepage.HomepageView;
import com.unisa.cinehub.views.film.FilmView;
import com.unisa.cinehub.views.serietv.SerieTvView;
import org.vaadin.gatanaso.MultiselectComboBox;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@CssImport(value = "./styles/views/main/main-view.css", themeFor = "vaadin-app-layout")
@CssImport(value = "./styles/views/components/menu-bar-button.css", themeFor = "vaadin-menu-bar-button")
@CssImport("./styles/views/main/main-view.css")
@CssImport("./styles/views/components/card-media.css")
@CssImport("./styles/views/components/shared-styles.css")
@PWA(name = "CineHub", shortName = "CineHub", enableInstallPrompt = false)
public class MainView extends AppLayout {

   private Tabs menu;

    public MainView() {
        HorizontalLayout header = createHeader();
        addToNavbar(header);
    }

    /*private VerticalLayout createTopBar(HorizontalLayout header, Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.getThemeList().add("dark");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.add(header, menu);
        return layout;
    }*/

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setPadding(false);
        header.setSpacing(false);
        //header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setId("header");
        Image logo = new Image("images/popcorn.png", "CineHub logo");
        logo.setId("logo");
        logo.getStyle().set("margin-left", ".65rem");


        /* ricerca */

        HorizontalLayout ricerca = new RicercaComponent();


        /* tendina */
        Icon iconUser = new Icon(VaadinIcon.USER);
        iconUser.setColor("red");
        iconUser.setId("iconUser");
        MenuBar menuBar = new MenuBar();
        menuBar.setOpenOnHover(true);
        MenuItem userItem = menuBar.addItem(iconUser);
        SubMenu userSubMenu = userItem.getSubMenu();
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                Utente utente = SecurityUtils.getLoggedIn();
                if(utente instanceof Recensore) {
                    MenuItem profilo = userSubMenu.addItem(new RouterLink("Profilo", ProfiloView.class));
                } else if (utente instanceof ResponsabileCatalogo) {
                    MenuItem gestioneCast = userSubMenu.addItem(new RouterLink("Gestione Cast", AdminCastView.class));
                    MenuItem gestioneFilm = userSubMenu.addItem(new RouterLink("Gestione Film", AdminFilmView.class));
                    MenuItem gestioneSerieTv = userSubMenu.addItem(new RouterLink("Gestione Serie TV", AdminSerieTvView.class));
                    MenuItem gestionePuntate = userSubMenu.addItem(new RouterLink("Gestione Puntate", AdminPuntataView.class));
                } else if (utente instanceof Moderatore && ((Moderatore) utente).getTipo().equals(Moderatore.Tipo.MODACCOUNT)) {
                    MenuItem moderazioneAccount = userSubMenu.addItem(new RouterLink("Moderazione Account", ModeratoreAccountView.class));
                } else if (utente instanceof Moderatore && ((Moderatore) utente).getTipo().equals(Moderatore.Tipo.MODCOMMENTI)) {
                    MenuItem moderazioneRecensioni = userSubMenu.addItem(new RouterLink("Moderazione Recensioni", ModeraRecensioniView.class));
                }
                MenuItem logout = userSubMenu.addItem(new Anchor("/logout", "Logout"));
            } catch (InvalidBeanException | BeanNotExsistException e) {
                Notification.show("Si è verificato un errore");
            }
        } else {
            MenuItem login = userSubMenu.addItem(new RouterLink("Login", LoginView.class));
            MenuItem signup = userSubMenu.addItem(new RouterLink("Sign Up", RegisterView.class));
        }

        H3 h3 = new H3("CineHub");
        h3.getStyle().set("margin", "0");
        HorizontalLayout logoNome= new HorizontalLayout(logo, h3);
        logoNome.setAlignItems(FlexComponent.Alignment.CENTER);

        menu = createMenuTabs();

        HorizontalLayout hor = new HorizontalLayout(ricerca, menuBar);
        header.add(logoNome, menu, hor);


        return header;
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs(); //questi sono i bottoni
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        return new Tab[]{createTab("Homepage", HomepageView.class), createTab("Film", FilmView.class),
                createTab("Serie Tv", SerieTvView.class)};
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        H4 h4 = new H4(new RouterLink(text, navigationTarget));
        tab.add(h4);
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }
}

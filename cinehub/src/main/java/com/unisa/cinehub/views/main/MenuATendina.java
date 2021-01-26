package com.unisa.cinehub.views.main;

import com.github.appreciated.app.layout.addons.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.applayout.LeftLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftSubMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftHeaderItem;
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.github.appreciated.app.layout.entity.DefaultBadgeHolder;
import com.unisa.cinehub.data.entity.Moderatore;
import com.unisa.cinehub.data.entity.Recensore;
import com.unisa.cinehub.data.entity.ResponsabileCatalogo;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.security.SecurityUtils;
import com.unisa.cinehub.views.film.FilmView;
import com.unisa.cinehub.views.homepage.HomepageView;
import com.unisa.cinehub.views.login.LoginView;
import com.unisa.cinehub.views.login.RegisterView;
import com.unisa.cinehub.views.serietv.SerieTvView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminCastView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminFilmView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminPuntataView;
import com.unisa.cinehub.views.user.gestoreCatalogo.AdminSerieTvView;
import com.unisa.cinehub.views.user.moderatoreaccount.ModeratoreAccountView;
import com.unisa.cinehub.views.user.moderatorerecensioni.ModeraRecensioniView;
import com.unisa.cinehub.views.user.recensore.ProfiloView;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;

import static com.github.appreciated.app.layout.entity.Section.HEADER;


public class MenuATendina extends AppLayoutRouterLayout<LeftLayouts.LeftResponsiveHybrid> {
    /**
     * Do not initialize here. This will lead to NPEs
     */
    private DefaultNotificationHolder notifications;
    private DefaultBadgeHolder badge;

    public MenuATendina() {
        if(SecurityUtils.isUserLoggedIn()) {
            try {
                if (SecurityUtils.getLoggedIn() instanceof Recensore) {
                    init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
                            .withTitle("CineHub")
                            .withAppBar(AppBarBuilder.get()
                                    .build())
                            .withAppMenu(LeftAppMenuBuilder.get()
                                    .addToSection(HEADER,
                                            new LeftHeaderItem("", "", "images/popcorn.png")
                                    )
                                    .add(new LeftNavigationItem("Homepage", VaadinIcon.HOME.create(), HomepageView.class),
                                            new LeftNavigationItem("Film", VaadinIcon.HOME.create(), FilmView.class),
                                            new LeftNavigationItem("SerieTv", VaadinIcon.HOME.create(), SerieTvView.class),
                                            LeftSubMenuBuilder.get("Profilo", VaadinIcon.USER.create())
                                                    .add(
                                                            new LeftNavigationItem("Profilo", VaadinIcon.USER.create(), ProfiloView.class),
                                                            new Anchor("/logout", "LogOut"))
                                                    .build()
                                    )
                                    .build())
                            .build());
                } else if (SecurityUtils.getLoggedIn() instanceof ResponsabileCatalogo) {
                    init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
                            .withTitle("CineHub")
                            .withAppBar(AppBarBuilder.get()
                                    .build())
                            .withAppMenu(LeftAppMenuBuilder.get()
                                    .addToSection(HEADER,
                                            new LeftHeaderItem("", "", "images/popcorn.png")
                                    )
                                    .add(new LeftNavigationItem("Homepage", VaadinIcon.HOME.create(), HomepageView.class),
                                            new LeftNavigationItem("Film", VaadinIcon.HOME.create(), FilmView.class),
                                            new LeftNavigationItem("SerieTv", VaadinIcon.HOME.create(), SerieTvView.class),
                                            LeftSubMenuBuilder.get("Profilo", VaadinIcon.USER.create())
                                                    .add(
                                                            new LeftNavigationItem("Gestione Film", VaadinIcon.USER_STAR.create(), AdminFilmView.class),
                                                            new LeftNavigationItem("Gestione SerieTV", VaadinIcon.USER_STAR.create(), AdminSerieTvView.class),
                                                            new LeftNavigationItem("Gestione Puntate", VaadinIcon.USER_STAR.create(), AdminPuntataView.class),
                                                            new LeftNavigationItem("Gestione Cast", VaadinIcon.USER_STAR.create(), AdminCastView.class),
                                                            new Anchor("/logout", "LogOut"))
                                                    .build()
                                    )
                                    .build())
                            .build());
                } else if (SecurityUtils.getLoggedIn() instanceof Moderatore && ((Moderatore) SecurityUtils.getLoggedIn()).getTipo().equals(Moderatore.Tipo.MODACCOUNT)) {
                    init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
                            .withTitle("CineHub")
                            .withAppBar(AppBarBuilder.get()
                                    .build())
                            .withAppMenu(LeftAppMenuBuilder.get()
                                    .addToSection(HEADER,
                                            new LeftHeaderItem("", "", "images/popcorn.png")
                                    )
                                    .add(new LeftNavigationItem("Homepage", VaadinIcon.HOME.create(), HomepageView.class),
                                            new LeftNavigationItem("Film", VaadinIcon.HOME.create(), FilmView.class),
                                            new LeftNavigationItem("SerieTv", VaadinIcon.HOME.create(), SerieTvView.class),
                                            LeftSubMenuBuilder.get("Profilo", VaadinIcon.USER.create())
                                                    .add(
                                                            new LeftNavigationItem("Moderazione Account", VaadinIcon.USER_STAR.create(), ModeratoreAccountView.class),
                                                            new Anchor("/logout", "LogOut"))
                                                    .build()
                                    )
                                    .build())
                            .build());
                } else {
                    init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
                            .withTitle("CineHub")
                            .withAppBar(AppBarBuilder.get()
                                    .build())
                            .withAppMenu(LeftAppMenuBuilder.get()
                                    .addToSection(HEADER,
                                            new LeftHeaderItem("", "", "images/popcorn.png")
                                    )
                                    .add(new LeftNavigationItem("Homepage", VaadinIcon.HOME.create(), HomepageView.class),
                                            new LeftNavigationItem("Film", VaadinIcon.HOME.create(), FilmView.class),
                                            new LeftNavigationItem("SerieTv", VaadinIcon.HOME.create(), SerieTvView.class),
                                            LeftSubMenuBuilder.get("Profilo", VaadinIcon.USER.create())
                                                    .add(
                                                            new LeftNavigationItem("Moderazione Recensione", VaadinIcon.USER_STAR.create(), ModeraRecensioniView.class),
                                                            new Anchor("/logout", "LogOut"))
                                                    .build()
                                    )
                                    .build())
                            .build());
                }
            } catch (InvalidBeanException | BeanNotExsistException e) {
                e.printStackTrace();
            }
        } else {
            init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
                    .withTitle("CineHub")
                    .withAppBar(AppBarBuilder.get()
                            .build())
                    .withAppMenu(LeftAppMenuBuilder.get()
                            .addToSection(HEADER,
                                    new LeftHeaderItem("", "", "images/popcorn.png")
                            )
                            .add(new LeftNavigationItem("Homepage", VaadinIcon.HOME.create(), HomepageView.class),
                                    new LeftNavigationItem("Film", VaadinIcon.HOME.create(), FilmView.class),
                                    new LeftNavigationItem("SerieTv", VaadinIcon.HOME.create(), SerieTvView.class),
                                    LeftSubMenuBuilder.get("Profilo", VaadinIcon.USER.create())
                                            .add(
                                                    new LeftNavigationItem("Log In", VaadinIcon.UNLOCK.create(), LoginView.class),
                                                    new LeftNavigationItem("Sign Up", VaadinIcon.RECORDS.create(), RegisterView.class))
                                            .build()
                            )
                            .build())
                    .build());
        }

    }
}


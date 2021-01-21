package com.unisa.cinehub.views.main;

import com.github.appreciated.app.layout.addons.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.addons.notification.component.NotificationButton;
import com.github.appreciated.app.layout.addons.notification.entity.DefaultNotification;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.applayout.LeftLayouts;
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.builder.LeftSubMenuBuilder;
import com.github.appreciated.app.layout.component.menu.left.items.LeftClickableItem;
import com.github.appreciated.app.layout.component.menu.left.items.LeftHeaderItem;
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem;
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout;
import com.github.appreciated.app.layout.entity.DefaultBadgeHolder;
import com.unisa.cinehub.views.film.FilmView;
import com.unisa.cinehub.views.homepage.HomepageView;
import com.unisa.cinehub.views.serietv.SerieTvView;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;

import static com.github.appreciated.app.layout.entity.Section.FOOTER;
import static com.github.appreciated.app.layout.entity.Section.HEADER;


public class MenuATendina extends AppLayoutRouterLayout<LeftLayouts.LeftResponsiveHybrid> {
    /**
     * Do not initialize here. This will lead to NPEs
     */
    private DefaultNotificationHolder notifications;
    private DefaultBadgeHolder badge;

    public MenuATendina() {
        init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
                .withTitle("CibeHub")
                .withAppBar(AppBarBuilder.get()
                        .build())
                .withAppMenu(LeftAppMenuBuilder.get()
                        .addToSection(HEADER,
                                new LeftHeaderItem("", "", "images/popcorn.png")
                        )
                        .add(new LeftNavigationItem("Homepage", VaadinIcon.HOME.create(), HomepageView.class),
                                new LeftNavigationItem("Film", VaadinIcon.HOME.create(), FilmView.class),
                                new LeftNavigationItem("SerieTv", VaadinIcon.HOME.create(), SerieTvView.class),
                                LeftSubMenuBuilder.get("Profilo", VaadinIcon.PLUS.create())
                                        .add(
                                                new LeftNavigationItem("LogIn", VaadinIcon.CONNECT.create(), HomepageView.class),
                                                new LeftNavigationItem("LogOut", VaadinIcon.COG.create(), HomepageView.class))
                                        .build()
                            )
                        .build())
                .build());
    }
}


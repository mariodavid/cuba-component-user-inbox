package de.diedavids.cuba.userinbox.web.screens.main;

import com.haulmont.cuba.gui.components.Timer;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.app.main.MainScreen;
import de.diedavids.cuba.userinbox.web.screens.UserInboxMessageMenuBadge;

import javax.inject.Inject;


@UiController("userInboxSideMenuMainScreen")
@UiDescriptor("user-inbox-side-menu-main-screen.xml")
public class UserInboxSideMenuMainScreen extends MainScreen {

    @Inject
    protected SideMenu sideMenu;
    @Inject
    protected Timer updateCountersTimer;

    @Inject
    protected UserInboxMessageMenuBadge userInboxMessageMenuBadge;

    @Subscribe
    protected void onInit(InitEvent event) {
        userInboxMessageMenuBadge.initMessagesMenuItem(
                sideMenu,
                updateCountersTimer,
                this
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        userInboxMessageMenuBadge.updateMessageCounter(sideMenu);
    }

    @Subscribe("updateCountersTimer")
    protected void onUpdateCountersTimerTimerAction(Timer.TimerActionEvent event) {
        userInboxMessageMenuBadge.updateMessageCounter(sideMenu);
    }

}
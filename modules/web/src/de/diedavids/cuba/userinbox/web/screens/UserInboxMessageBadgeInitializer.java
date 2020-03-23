package de.diedavids.cuba.userinbox.web.screens;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Timer;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.cuba.gui.screen.OpenMode;
import com.haulmont.cuba.web.WebConfig;
import de.diedavids.cuba.userinbox.service.MessageService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(UserInboxMessageBadgeInitializer.NAME)
public class UserInboxMessageBadgeInitializer {

    static final String NAME = "ddcui_UserInboxMessageBadgeInitializer";

    @Inject
    protected Messages messages;
    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected MessageService messageService;
    @Inject
    protected WebConfig webConfig;

    public void initMessagesMenuItem(SideMenu sideMenu, Timer updateCountersTimer, FrameOwner frameOwner) {


        SideMenu.MenuItem messagesMenuItem = sideMenu.createMenuItem("messages");
        messagesMenuItem.setCaption(messages.getMainMessage("menu-config.ddcui$user-inbox"));
        messagesMenuItem.setIcon("font-icon:ENVELOPE");
        messagesMenuItem.setCommand(menuItem ->
                screenBuilders.screen(frameOwner)
                        .withLaunchMode(OpenMode.NEW_TAB)
                        .withScreenId("user-inbox")
                        .show()
        );

        sideMenu.addMenuItem(messagesMenuItem, 0);

        initUpdateCounterTimerDelay(updateCountersTimer);

    }


    private void initUpdateCounterTimerDelay(Timer updateCountersTimer) {
        int period = webConfig.getAppFoldersRefreshPeriodSec() * 1000;
        updateCountersTimer.setDelay(period);
    }

    public void updateMessageCounter(SideMenu sideMenu) {
        sideMenu.getMenuItemNN("messages")
                .setBadgeText(
                        messages.formatMainMessage("menu-config.ddcui$user-inbox.badge", getMessageCounter())
                );
    }

    private int getMessageCounter() {
        return messageService.countUnreadMessagesForCurrentUser();
    }

}

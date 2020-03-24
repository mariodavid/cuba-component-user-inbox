package de.diedavids.cuba.userinbox.web.screens;

import com.haulmont.cuba.gui.components.AbstractMainWindow;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.components.Timer;
import com.haulmont.cuba.gui.components.mainwindow.FtsField;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;

import javax.inject.Inject;
import java.util.Map;


/**
 * @deprecated switch to CUBA 7 based Main Screens
 */
public class SideMainwindowWithMessages extends AbstractMainWindow {

    @Inject
    private FtsField ftsField;

    @Inject
    private Image logoImage;

    @Inject
    private SideMenu sideMenu;

    @Inject
    private Timer updateCountersTimer;

    @Inject
    protected UserInboxMessageMenuBadge userInboxMessageMenuBadge;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        sideMenu.focus();

        initLayoutAnalyzerContextMenu(logoImage);
        initLogoImage(logoImage);
        initFtsField(ftsField);
//
//        initUpdateCounterTimerDelay();
//        initMessagesMenuItem();
        userInboxMessageMenuBadge
                .initMessagesMenuItem(sideMenu, updateCountersTimer, this);

        sideMenu.setSelectOnClick(true);
    }

//
//    private void initUpdateCounterTimerDelay() {
//        int period = webConfig.getAppFoldersRefreshPeriodSec() * 1000;
//        updateCountersTimer.setDelay(period);
//    }
//
//
//    private void initMessagesMenuItem() {
//        SideMenu.MenuItem messagesMenuItem = sideMenu.createMenuItem("messages");
//        messagesMenuItem.setCaption(messages.getMessage(this.getClass(), "messages"));
//        messagesMenuItem.setIcon("font-icon:ENVELOPE");
//        messagesMenuItem.setCommand(menuItem -> openWindow("user-inbox", WindowManager.OpenType.NEW_TAB));
//
//        sideMenu.addMenuItem(messagesMenuItem,0);
//    }

    @Override
    public void ready() {
        userInboxMessageMenuBadge.updateMessageCounter(sideMenu);
    }

    public void updateCounters(Timer source) {
        userInboxMessageMenuBadge.updateMessageCounter(sideMenu);
    }

//    private void updateMessageCounter() {
//        sideMenu.getMenuItemNN("messages")
//                .setBadgeText(getMessageCounter() + " new");
//    }
//
//    private long getMessageCounter() {
//        return messageService.countUnreadMessagesForCurrentUser();
//    }
}
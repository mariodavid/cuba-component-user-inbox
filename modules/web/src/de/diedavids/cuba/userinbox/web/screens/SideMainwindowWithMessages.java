package de.diedavids.cuba.userinbox.web.screens;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractMainWindow;
import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.Timer;
import com.haulmont.cuba.gui.components.mainwindow.FtsField;
import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import com.haulmont.cuba.web.WebConfig;
import de.diedavids.cuba.userinbox.service.MessageService;

import javax.inject.Inject;
import java.util.Map;

public class SideMainwindowWithMessages extends AbstractMainWindow {

    @Inject
    private FtsField ftsField;

    @Inject
    private Embedded logoImage;

    @Inject
    private SideMenu sideMenu;

    @Inject
    private MessageService messageService;


    @Inject
    private Timer updateCountersTimer;

    @Inject
    private WebConfig webConfig;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        sideMenu.requestFocus();

        initLayoutAnalyzerContextMenu(logoImage);
        initLogoImage(logoImage);
        initFtsField(ftsField);

        initUpdateCounterTimerDelay();
        initMessagesMenuItem();

        sideMenu.setSelectOnClick(true);



    }


    private void initUpdateCounterTimerDelay() {
        int period = webConfig.getAppFoldersRefreshPeriodSec() * 1000;
        updateCountersTimer.setDelay(period);
    }


    private void initMessagesMenuItem() {
        SideMenu.MenuItem messagesMenuItem = sideMenu.createMenuItem("messages");
        messagesMenuItem.setCaption(messages.getMessage(this.getClass(), "messages"));
        messagesMenuItem.setIcon("font-icon:ENVELOPE");
        messagesMenuItem.setCommand(menuItem -> openWindow("user-inbox", WindowManager.OpenType.NEW_TAB));

        sideMenu.addMenuItem(messagesMenuItem,0);
    }

    @Override
    public void ready() {
        updateMessageCounter();
    }

    public void updateCounters(Timer source) {
        updateMessageCounter();
    }

    private void updateMessageCounter() {
        sideMenu.getMenuItemNN("messages")
                .setBadgeText(getMessageCounter() + " new");
    }

    private int getMessageCounter() {
        return messageService.countUnreadMessagesForCurrentUser();
    }
}
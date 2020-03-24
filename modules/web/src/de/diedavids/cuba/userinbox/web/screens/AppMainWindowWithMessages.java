package de.diedavids.cuba.userinbox.web.screens;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Timer;
import com.haulmont.cuba.web.WebConfig;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;
import de.diedavids.cuba.userinbox.service.MessageService;

import javax.inject.Inject;
import java.util.Map;

public class AppMainWindowWithMessages extends AppMainWindow {

    @Inject
    private Action openMessagesAction;

    @Inject
    private MessageService messageService;

    @Inject
    private Timer updateCountersTimer;

    @Inject
    private WebConfig webConfig;

    public void openMessages() {
        openWindow("ddcui$user-inbox", WindowManager.OpenType.DIALOG);
    }

    @Override
    public void init(Map<String, Object> params) {
        initUpdateCounterTimerDelay();
    }

    private void initUpdateCounterTimerDelay() {
        int period = webConfig.getAppFoldersRefreshPeriodSec() * 1000;
        updateCountersTimer.setDelay(period);
    }

    @Override
    public void ready() {
        updateMessageCounter();
    }

    public void updateCounters(Timer source) {
        updateMessageCounter();
    }

    private void updateMessageCounter() {
        long messageCounter = getMessageCounter();
        String messageCounterCaption = "";

        if (messageCounter > 0) {
            messageCounterCaption = "(" + messageCounter + ")";
        }

        openMessagesAction.setCaption(messageCounterCaption);
    }

    private long getMessageCounter() {
        return messageService.countUnreadMessagesForCurrentUser();
    }
}
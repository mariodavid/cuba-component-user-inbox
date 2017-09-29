package de.diedavids.cuba.userinbox.web.screens;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;

public class ExtAppMainWindow extends AppMainWindow {


    public void openMessages() {
        openWindow("user-inbox", WindowManager.OpenType.DIALOG);
    }

}
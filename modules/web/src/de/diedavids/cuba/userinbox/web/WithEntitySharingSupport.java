package de.diedavids.cuba.userinbox.web;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ButtonsPanel;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.components.actions.ListAction;
import com.haulmont.cuba.gui.screen.*;
import de.balvi.cuba.declarativecontrollers.web.helper.ButtonsPanelHelper;
import de.diedavids.cuba.userinbox.entity.Message;
import de.diedavids.cuba.userinbox.entity.SendMessageEntity;

import java.util.Collections;

public interface WithEntitySharingSupport {

    String BUTTON_MSG_KEY = "actions.Share";
    String ICON_KEY = "font-icon:SHARE_ALT";

    String SHARE_SUBJECT_KEY = "share.subject";
    String SHARE_TEXT_KEY = "share.text";
    String SHARE_ACTION_ID = "shareAction";
    String SHARE_BTN_ID = "shareBtn";
    String SHARE_NEW_MESSAGE_SCREEN_ID = "ddcui$send-message";

    /**
     * defines the table component that will be used as a basis for the tag functionality
     *
     * @return the table
     */
    Table getListComponent();

    /**
     * the button id of the destination button
     *
     * It will either picked up from existing XML definitions or created with this identifier
     *
     * @return the button identifier
     */
    default String getButtonId() {
        return SHARE_BTN_ID;
    }


    /**
     * defines the button panel that will be used for inserting the tags button
     *
     * @return the destination buttonPanel
     */
    ButtonsPanel getButtonsPanel();


    @Subscribe
    default void initShareableButton(Screen.InitEvent event) {

        Screen screen = event.getSource();
        Button button = createOrGetButtonForShareable(screen);

        initButtonWithShareFunctionality(screen, button);
    }

    default void initButtonWithShareFunctionality(Screen screen, Button button) {

        ScreenBuilders screens = getBeanLocatorForShareable(screen).get(ScreenBuilders.class);
        Messages messages = getBeanLocatorForShareable(screen).get(Messages.class);
        MetadataTools metadataTools = getBeanLocatorForShareable(screen).get(Metadata.class).getTools();
        UserSessionSource userSessionSource = getBeanLocatorForShareable(screen).get(UserSessionSource.class);

        ListAction tagsAction = new ItemTrackingAction(getListComponent(), SHARE_ACTION_ID)
                .withPrimary(true)
                .withIcon(ICON_KEY)
                .withCaption(messages.getMainMessage(BUTTON_MSG_KEY))
                .withHandler(e -> screens.editor(SendMessageEntity.class, screen)
                        .withScreenId(SHARE_NEW_MESSAGE_SCREEN_ID)
                        .withInitializer(message -> {
                            Entity shareable = getListComponent().getSingleSelected();
                            String currentUsername = userSessionSource.getUserSession().getCurrentOrSubstitutedUser().getName();
                            String entityCaption = messages.getTools().getEntityCaption(shareable.getMetaClass());
                            String shareableInstanceName = metadataTools.getInstanceName(shareable);
                            message.setSubject(
                                    messages.formatMainMessage(
                                            SHARE_SUBJECT_KEY,
                                            currentUsername,
                                            entityCaption,
                                            shareableInstanceName
                                    )
                            );
                            message.setText(
                                    messages.formatMainMessage(
                                            SHARE_TEXT_KEY,
                                            entityCaption,
                                            shareableInstanceName,
                                            currentUsername
                                    )
                            );
                            message.setShareable(shareable);
                        })
                        .withLaunchMode(OpenMode.DIALOG)
                        .show()
                );
        getListComponent().addAction(tagsAction);
        button.setAction(tagsAction);

    }

    default Button createOrGetButtonForShareable(Screen screen) {
        BeanLocator beanLocator = getBeanLocatorForShareable(screen);
        ButtonsPanelHelper buttonsPanelHelper = beanLocator.get(ButtonsPanelHelper.NAME);

        return buttonsPanelHelper.createButton(getButtonId(), getButtonsPanel(), Collections.emptyList());
    }

    default BeanLocator getBeanLocatorForShareable(Screen screen) {
        return Extensions.getBeanLocator(screen);
    }


}
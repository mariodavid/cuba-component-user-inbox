package de.diedavids.cuba.userinbox.web.message;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.builders.AfterScreenCloseEvent;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import de.diedavids.cuba.entitysoftreference.web.SoftReferenceInstanceNameTableColumnGenerator;
import de.diedavids.cuba.userinbox.entity.Message;
import de.diedavids.cuba.userinbox.entity.SendMessageEntity;

import javax.inject.Inject;
import javax.inject.Named;

@UiController("ddcui$user-inbox")
@UiDescriptor("user-inbox.xml")
@LookupComponent("messagesTable")
@LoadDataBeforeShow
public class UserInbox extends StandardLookup<Message> {

    private static final String SHAREABLE_ATTRIBUTE_KEY = "shareable";

    @Inject
    protected CollectionContainer<Message> messagesDc;
    @Inject
    protected Table<Message> messagesTable;
    @Inject
    protected DataContext dataContext;
    @Inject
    protected CollectionLoader<Message> messagesDl;
    @Inject
    protected Notifications notifications;
    @Inject
    protected MessageBundle messageBundle;
    @Inject
    protected UserSession userSession;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected Metadata metadata;

    @Named("messagesTable.toggleRead")
    protected Action messagesTableToggleRead;

    @Inject
    protected Messages messages;
    @Named("messagesTable.create")
    protected CreateAction<Message> messagesTableCreate;

    @Subscribe
    protected void onInit(InitEvent event) {

        messagesDc.addItemChangeListener(new ToggleReadActionListener(
                messages, messagesTableToggleRead
        ));

        initShareableTableColumn();

        messagesDl.setParameter("receiver", userSession.getCurrentOrSubstitutedUser());
    }

    @Install(to = "messagesTable.edit", subject = "afterCloseHandler")
    protected void messagesTableEditAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        getScreenData().loadAll();
    }

    @Subscribe("messagesTable.create")
    protected void onMessagesTableCreate(Action.ActionPerformedEvent event) {
        screenBuilders
                .editor(SendMessageEntity.class, this)
                .withScreenClass(SendMessage.class)
                .newEntity()
                .withLaunchMode(OpenMode.DIALOG)
                .withAfterCloseListener(this::afterMessageSendScreenClosed)
                .show();
    }

    void afterMessageSendScreenClosed(AfterScreenCloseEvent<SendMessage> screenClosedEvent) {
        if (screenClosedEvent.closedWith(StandardOutcome.COMMIT)) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messageBundle.getMessage("messageSend"))
                    .show();
        }
    }

    private void initShareableTableColumn() {

        messagesTable.addGeneratedColumn(SHAREABLE_ATTRIBUTE_KEY,
                new SoftReferenceInstanceNameTableColumnGenerator(
                        SHAREABLE_ATTRIBUTE_KEY,
                        uiComponents,
                        metadata.getTools(),
                        screenBuilders,
                        this
                )
        );
    }

    @Subscribe("messagesTable.toggleRead")
    protected void onMessagesTableToggleRead(Action.ActionPerformedEvent event) {
        messagesTable
                .getSingleSelected()
                .toggleRead();

        dataContext.commit();
    }

}
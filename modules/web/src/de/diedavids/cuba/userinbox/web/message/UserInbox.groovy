package de.diedavids.cuba.userinbox.web.message

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.LoadContext
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.gui.WindowManager
import com.haulmont.cuba.gui.components.AbstractLookup
import com.haulmont.cuba.gui.components.Action
import com.haulmont.cuba.gui.components.Frame
import com.haulmont.cuba.gui.components.Table
import com.haulmont.cuba.gui.components.actions.CreateAction
import com.haulmont.cuba.gui.components.actions.EditAction
import com.haulmont.cuba.gui.data.CollectionDatasource
import de.diedavids.cuba.userinbox.entity.Message

import javax.inject.Inject
import javax.inject.Named

class UserInbox extends AbstractLookup {

    @Named('messagesTable.create')
    CreateAction createAction


    @Named('messagesTable.edit')
    EditAction editAction

    @Named('messagesTable.toggleRead')
    Action toggleReadAction

    @Inject
    Table<Message> messagesTable

    @Inject
    CollectionDatasource<Message, UUID> messagesDs

    @Inject
    DataManager dataManager

    @Inject
    Metadata metadata

    @Override
    void init(Map<String, Object> params) {
        super.init(params)
        createAction.windowId = 'send-message'
        createAction.afterCommitHandler = new CreateAction.AfterCommitHandler() {
            @Override
            void handle(Entity entity) {
                messagesDs.refresh()
                showMessageSendNotification()
            }
        }


        messagesDs.addItemChangeListener(new ToggleReadActionListener(
                messages: messages,
                toggleReadAction: toggleReadAction
        ))
    }

    private showMessageSendNotification() {
        showNotification(messages.formatMessage(getClass(), 'messageSend'), Frame.NotificationType.TRAY)
    }

    @SuppressWarnings('UnusedMethodParameter')
    void openEntity(Message item, String columnId) {
        openEditor(findRecordInstance(item), WindowManager.OpenType.NEW_TAB)
    }


    protected Entity findRecordInstance(Message item) {

        Class entityClass = getClassFromMessageRecord(item)

        LoadContext loadContext = LoadContext.create(entityClass)
                .setId(getRecordId(item))

        dataManager.load(loadContext)
    }

    protected UUID getRecordId(Message item) {
        UUID.fromString(item.entityReferenceId)
    }

    protected Class getClassFromMessageRecord(Message item) {
        metadata.getClass(item.entityReferenceClass)?.javaClass
    }

    void toggleRead() {


        def message = messagesTable.singleSelected
        message.read = !message.read

        messagesDs.commit()

    }
}